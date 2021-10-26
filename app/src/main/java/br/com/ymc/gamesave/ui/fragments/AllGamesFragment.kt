package br.com.ymc.gamesave.ui.activities.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.ymc.gamesave.R
import br.com.ymc.gamesave.adapter.AllGamesAdapter
import br.com.ymc.gamesave.databinding.FragmentAllGamesBinding
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.ui.activities.GameDetailActivity
import br.com.ymc.gamesave.util.Const
import br.com.ymc.gamesave.viewModels.AllGamesViewModel
import br.com.ymc.gamesave.viewModels.GamesListState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowWith
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

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
    }

    fun setObservers()
    { //        //List of games observer
        //        viewModel.arrGames.observe(viewLifecycleOwner, { arrGames ->
        //            binding.rcvGames.apply {
        //                binding.swipeRefreshLayout.isRefreshing = false
        //
        //                val adapterAux = AllGamesAdapter(arrGames)
        //                layoutManager = GridLayoutManager(context, 2)
        //                adapter = adapterAux
        //
        //                adapterAux.itemClick = { gameId ->
        //                    activity?.let {
        //                        val intent = Intent(context, GameDetailActivity::class.java).apply {
        //                            putExtra(Const.EXTRA_GAME_ID, gameId)
        //                        }
        //
        //                        startActivity(intent)
        //                    }
        //                }
        //            }
        //        })

        viewModel.arrGames.onEach { state ->
            when (state)
            {
                is GamesListState.Success ->
                {
                    binding.rcvGames.apply {
                        binding.swipeRefreshLayout.isRefreshing = false
                        snackbar?.dismiss()

                        val adapterAux = AllGamesAdapter(state.arrGames)
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

                is GamesListState.IsLoading ->
                {
                    binding.root.let {
                        snackbar = Snackbar.make(binding.root, "Loading...", Snackbar.LENGTH_INDEFINITE)
                        snackbar!!.show()
                    }
                }

                is GamesListState.Error ->
                {
                    binding.root.let {
                        snackbar?.dismiss()
                        Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG).show()
                    }
                }
                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    override fun onStart()
    {
        super.onStart()
        viewModel.callGamesApi()
    }

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