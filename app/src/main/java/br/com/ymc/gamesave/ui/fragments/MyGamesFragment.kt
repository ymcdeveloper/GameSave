package br.com.ymc.gamesave.ui.activities.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.ymc.gamesave.adapter.AllGamesAdapter
import br.com.ymc.gamesave.databinding.FragmentMyGamesBinding
import br.com.ymc.gamesave.ui.activities.GameDetailActivity
import br.com.ymc.gamesave.util.Const
import br.com.ymc.gamesave.viewModels.MyGamesViewModel

class MyGamesFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener
{
    private var _binding: FragmentMyGamesBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MyGamesViewModel by activityViewModels()

    var listenerAddGames: ((clicked : Boolean) -> Unit)? = null

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

    private fun setupObservers()
    {
        viewModel.arrGames.observe(viewLifecycleOwner, { arrGames ->
            binding.rcvMyGames.apply {

                binding.swipeRefreshLayout.isRefreshing = false
                if(arrGames.isNotEmpty())
                {
                    binding.rcvMyGames.visibility = View.VISIBLE
                    binding.lnlWarning.visibility = View.GONE

                    val adapterAux = AllGamesAdapter(arrGames)
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
        })
    }

    override fun onRefresh()
    {
        viewModel.loadGames()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        if(requestCode == Const.REQUEST_DETAIL_ACTIVITY && resultCode == Activity.RESULT_OK)
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