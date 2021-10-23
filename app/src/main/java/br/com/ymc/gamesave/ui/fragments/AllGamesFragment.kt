package br.com.ymc.gamesave.ui.activities.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.ymc.gamesave.adapter.AllGamesAdapter
import br.com.ymc.gamesave.databinding.FragmentAllGamesBinding
import br.com.ymc.gamesave.ui.activities.GameDetailActivity
import br.com.ymc.gamesave.ui.activities.MainActivity
import br.com.ymc.gamesave.util.Const
import br.com.ymc.gamesave.viewModels.AllGamesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllGamesFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener
{
    private var _binding: FragmentAllGamesBinding? = null
    private val binding get() = _binding!!

    private val viewModel : AllGamesViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentAllGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        viewModel.callGamesApi()

        binding.swipeRefreshLayout.setOnRefreshListener(this)
    }

    fun setObservers()
    {
        //List of games observer
        viewModel.arrGames.observe(viewLifecycleOwner, { arrGames ->
            binding.rcvGames.apply {
                binding.swipeRefreshLayout.isRefreshing = false

                val adapterAux = AllGamesAdapter(arrGames)
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
        })
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