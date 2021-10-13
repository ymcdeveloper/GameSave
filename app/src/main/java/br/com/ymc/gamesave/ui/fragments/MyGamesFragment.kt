package br.com.ymc.gamesave.ui.activities.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.ymc.gamesave.databinding.FragmentMyGamesBinding

class MyGamesFragment : Fragment()
{
    private var _binding: FragmentMyGamesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentMyGamesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(context, "My games", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}