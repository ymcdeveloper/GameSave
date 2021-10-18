package br.com.ymc.gamesave.ui.activities.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import br.com.ymc.gamesave.adapter.AllGamesAdapter
import br.com.ymc.gamesave.databinding.FragmentMyGamesBinding
import br.com.ymc.gamesave.ui.activities.GameDetailActivity
import br.com.ymc.gamesave.util.Const
import br.com.ymc.gamesave.viewModels.AllGamesViewModel
import br.com.ymc.gamesave.viewModels.MyGamesViewModel

class MyGamesFragment : Fragment()
{
    private var _binding: FragmentMyGamesBinding? = null

    private val binding get() = _binding!!

    private val viewModel : MyGamesViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        _binding = FragmentMyGamesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        viewModel.arrGames.observe(viewLifecycleOwner, { arrGames ->
            binding.rcvMyGames.apply {
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

        viewModel.loadGames()
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}