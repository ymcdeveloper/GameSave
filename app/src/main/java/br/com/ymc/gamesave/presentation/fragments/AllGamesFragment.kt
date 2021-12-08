package br.com.ymc.gamesave.presentation.activities.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.ymc.gamesave.core.util.Const
import br.com.ymc.gamesave.data.remote.dto.Game
import br.com.ymc.gamesave.databinding.FragmentAllGamesBinding
import br.com.ymc.gamesave.presentation.activities.GameDetailActivity
import br.com.ymc.gamesave.presentation.adapter.AllGamesAdapter
import br.com.ymc.gamesave.viewModels.AllGamesViewModel
import br.com.ymc.gamesave.viewModels.UIState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllGamesFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener
{
    private var _binding: FragmentAllGamesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AllGamesViewModel by activityViewModels()

    private var snackbar: Snackbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentAllGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setObservers()

        binding.swipeRefreshLayout.setOnRefreshListener(this)
        viewModel.callGamesApi()
    }

    private fun setupGameList(games: List<Game>?)
    {
        binding.rcvGames.apply {
            snackbar?.dismiss()

            val adapterAux = AllGamesAdapter(games)
            layoutManager = GridLayoutManager(context, 2)
            adapter = adapterAux

            adapterAux.itemClick = { gameId ->
                activity?.let {
                    val intent = Intent(context, GameDetailActivity::class.java).apply {
                        putExtra(Const.EXTRA_GAME_ID, gameId)
                    }

                    startActivity(intent)
                }
            }
        }
    }

    private fun setObservers()
    {
        viewModel.gamesList.observe(viewLifecycleOwner) { result ->
            setupGameList(result)
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state)
            {
                is UIState.Error ->
                {
                    binding.root.let {
                        snackbar?.dismiss()
                        binding.swipeRefreshLayout.isRefreshing = false
                        Snackbar.make(it, state.message, Snackbar.LENGTH_LONG).show()
                    }
                }

                is UIState.Loading ->
                {
                    binding.swipeRefreshLayout.isRefreshing = true
                    binding.root.let {
                        snackbar = Snackbar.make(it, "Loading...", Snackbar.LENGTH_INDEFINITE)
                        snackbar!!.show()
                    }
                }

                is UIState.Success ->
                {
                    snackbar?.dismiss()
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }

        //        viewModel.isLoading.observe(viewLifecycleOwner) {
        //                    if(it)
        //                    {
        //                        binding.swipeRefreshLayout.isRefreshing = true
        //                        binding.root.let {
        //                            snackbar = Snackbar.make(it, "Loading...", Snackbar.LENGTH_INDEFINITE)
        //                            snackbar!!.show()
        //                        }
        //                    }
        //                    else
        //                    {
        //                        binding.swipeRefreshLayout.isRefreshing = false
        //                        snackbar?.dismiss()
        //                    }
        //        }
        //
        //        viewModel.error.observe(viewLifecycleOwner) { error ->
        //            if(error.first)
        //            {
        //                binding.root.let {
        //                    snackbar?.dismiss()
        //                    Snackbar.make(it, error.second, Snackbar.LENGTH_LONG).show()
        //                }
        //            }
        //        }
    }

    //    private fun setObservers()
    //    {
    //        viewModel.arrGames.observe(viewLifecycleOwner) { result ->
    //            when(result)
    //            {
    //                is Resource.Success -> {
    //                    binding.swipeRefreshLayout.isRefreshing = false
    //                    setupGameList(result.data)
    //                }
    //
    //                is Resource.Error -> {
    //                    binding.swipeRefreshLayout.isRefreshing = false
    //                    binding.root.let {
    //                        snackbar?.dismiss()
    //                        Snackbar.make(it, result.message ?: "Unknown Error", Snackbar.LENGTH_LONG).show()
    //                    }
    //                }
    //
    //                is Resource.Loading -> {
    //                    binding.swipeRefreshLayout.isRefreshing = true
    //                    binding.root.let {
    //                        snackbar = Snackbar.make(it, "Loading...", Snackbar.LENGTH_INDEFINITE)
    //                        snackbar!!.show()
    //                    }
    //                }
    //            }
    //        }
    //    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

    override fun onRefresh()
    {
        viewModel.callGamesApi()
    }
}