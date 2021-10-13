package br.com.ymc.gamesave.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import br.com.ymc.gamesave.R
import br.com.ymc.gamesave.databinding.ActivityMainBinding
import br.com.ymc.gamesave.ui.activities.fragments.AllGamesFragment
import br.com.ymc.gamesave.ui.activities.fragments.InfoFragment
import br.com.ymc.gamesave.ui.activities.fragments.MyGamesFragment

class MainActivity : AppCompatActivity()
{
    private lateinit var binding : ActivityMainBinding

    private val allGamesFragment = AllGamesFragment()
    private val myGamesFragment = MyGamesFragment()
    private val infoFragment = InfoFragment()

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

        binding.bottomNavigation.getOrCreateBadge(R.id.menu_my_games).number = 1000
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