package br.com.ymc.gamesave.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.ymc.gamesave.R
import br.com.ymc.gamesave.databinding.ActivityMainBinding
import br.com.ymc.gamesave.ui.activities.fragments.AllGamesFragment
import br.com.ymc.gamesave.ui.activities.fragments.InfoFragment
import br.com.ymc.gamesave.ui.activities.fragments.MyGamesFragment
import br.com.ymc.gamesave.viewModels.AllGamesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{
    private lateinit var binding : ActivityMainBinding

    private val allGamesFragment = AllGamesFragment()
    private val myGamesFragment = MyGamesFragment()
    private val infoFragment = InfoFragment()

    private val viewModel : AllGamesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(allGamesFragment)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId)
            {
                R.id.menu_all_games -> replaceFragment(allGamesFragment)

                R.id.menu_my_games -> replaceFragment(myGamesFragment)

                R.id.menu_info -> replaceFragment(infoFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment)
    {
        if(fragment != null)
        {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}