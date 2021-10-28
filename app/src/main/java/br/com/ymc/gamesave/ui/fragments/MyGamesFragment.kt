package br.com.ymc.gamesave.ui.activities.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.ymc.gamesave.adapter.AllGamesAdapter
import br.com.ymc.gamesave.databinding.FragmentMyGamesBinding
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.ui.activities.GameDetailActivity
import br.com.ymc.gamesave.util.Const
import br.com.ymc.gamesave.util.Resource
import br.com.ymc.gamesave.viewModels.MyGamesViewModel
import br.com.ymc.gamesave.viewModels.UIState
import com.google.android.material.snackbar.Snackbar

class MyGamesFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener
{
    private var _binding: FragmentMyGamesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyGamesViewModel by activityViewModels()

    var listenerAddGames: ((clicked: Boolean) -> Unit)? = null

    private var snackbar: Snackbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        _binding = FragmentMyGamesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        viewModel.loadGames()

        binding.fabAdd.setOnClickListener {
            listenerAddGames?.invoke(true)
        }

        binding.swipeRefreshLayout.setOnRefreshListener(this)
    }

    private fun setupList(games: List<Game>)
    {
        binding.rcvMyGames.apply {

            binding.swipeRefreshLayout.isRefreshing = false
            if (games.isNotEmpty())
            {
                binding.rcvMyGames.visibility = View.VISIBLE
                binding.lnlWarning.visibility = View.GONE

                val adapterAux = AllGamesAdapter(games)
                layoutManager = GridLayoutManager(context, 2)
                adapter = adapterAux

                adapterAux.itemClick = { gameId ->
                    activity?.let {
                        val intent = Intent(context, GameDetailActivity::class.java).apply {
                            putExtra(Const.EXTRA_GAME_ID, gameId)
                        }

                        startActivityForResult(intent, Const.REQUEST_DETAIL_ACTIVITY)
                    }
                }
            }
            else
            {
                binding.rcvMyGames.visibility = View.GONE
                binding.lnlWarning.visibility = View.VISIBLE
            }
        }
    }

    private fun setupObservers()
    {
        viewModel.arrGames.observe(viewLifecycleOwner) { result ->
            setupList(result)
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state)
            {
                is UIState.Success ->
                {
                    snackbar?.dismiss()
                }

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
                    binding.root.let {
                        snackbar = Snackbar.make(it, "Loading...", Snackbar.LENGTH_INDEFINITE)
                        snackbar!!.show()
                    }
                }
            }
        }
    }

    override fun onRefresh()
    {
        viewModel.loadGames()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        if (requestCode == Const.REQUEST_DETAIL_ACTIVITY && resultCode == Activity.RESULT_OK)
        {
            viewModel.loadGames()
        }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}