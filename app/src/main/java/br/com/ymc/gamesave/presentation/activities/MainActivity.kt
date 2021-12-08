package br.com.ymc.gamesave.presentation.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import br.com.ymc.gamesave.R
import br.com.ymc.gamesave.presentation.adapter.MainViewPagerAdapter
import br.com.ymc.gamesave.databinding.ActivityMainBinding
import br.com.ymc.gamesave.presentation.activities.fragments.AllGamesFragment
import br.com.ymc.gamesave.presentation.activities.fragments.InfoFragment
import br.com.ymc.gamesave.presentation.activities.fragments.MyGamesFragment
import br.com.ymc.gamesave.viewModels.AllGamesViewModel
import br.com.ymc.gamesave.viewModels.MyGamesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, SearchView.OnCloseListener, View.OnClickListener
{
    private lateinit var binding : ActivityMainBinding

    private val allGamesFragment = AllGamesFragment()
    private val myGamesFragment = MyGamesFragment()
    private val infoFragment = InfoFragment()

    private val allGamesViewModel : AllGamesViewModel by viewModels()
    private val myGamesViewModel : MyGamesViewModel by viewModels()

    val fragments: ArrayList<Fragment> = arrayListOf(
        allGamesFragment,
        myGamesFragment,
        infoFragment
    )

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgSearch.setOnClickListener(this)
        binding.searchView.setOnCloseListener(this)
        binding.searchView.setOnQueryTextListener(this)

        setupBottomNavigation()

        myGamesFragment.listenerAddGames = {
            binding.viewPager.currentItem = 0
            binding.bottomNavigation.menu.getItem(0).isChecked = true
        }

        setupViewPager()
    }

    private fun setupViewPager()
    {
        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int)
            {
                onNavigationItemSelected(binding.bottomNavigation.menu.getItem(position))
                binding.bottomNavigation.menu.getItem(position).isChecked = true
            }
        })

        val viewPagerAdapter = MainViewPagerAdapter(fragments, this)

        binding.viewPager.apply {
            adapter = viewPagerAdapter
        }
    }

    private fun setupBottomNavigation()
    {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            onNavigationItemSelected(item)
            true
        }
    }

    private fun onNavigationItemSelected(menuItem : MenuItem)
    {
        binding.searchView.visibility = View.GONE
        binding.lnlSearch.visibility = View.VISIBLE
        binding.searchView.setQuery("", false)

        when(menuItem.itemId)
        {
            R.id.menu_all_games ->
            {
                binding.txtHeader.text = getString(R.string.games_list)
                binding.imgSearch.visibility = View.VISIBLE
                binding.viewPager.currentItem = 0
            }

            R.id.menu_my_games ->
            {
                binding.txtHeader.text = getString(R.string.my_games)
                binding.imgSearch.visibility = View.VISIBLE
                binding.viewPager.currentItem = 1
                myGamesViewModel.shouldReload()
            }

            R.id.menu_info ->
            {
                binding.txtHeader.text = getString(R.string.info)
                binding.imgSearch.visibility = View.GONE
                binding.viewPager.currentItem = 2
            }
        }
    }

    override fun onClick(view: View?)
    {
        when(view)
        {
            binding.imgSearch ->{
                binding.searchView.visibility = View.VISIBLE
                binding.lnlSearch.visibility = View.GONE
                binding.searchView.isIconified = false
                binding.searchView.requestFocus()
            }
        }
    }

    override fun onQueryTextChange(searchText: String?): Boolean
    {
        searchText?.let {
            if(it.isNotEmpty())
            {
                if(binding.bottomNavigation.menu.getItem(0).isChecked)
                {
                    allGamesViewModel.searchGame(it)
                }
                else
                {
                    myGamesViewModel.searchGame(it)
                }
            }
        }

        return true
    }

    override fun onClose(): Boolean
    {
        binding.searchView.visibility = View.GONE
        binding.lnlSearch.visibility = View.VISIBLE

        if(binding.bottomNavigation.menu.getItem(0).isChecked)
        {
            allGamesViewModel.callGamesApi()
        }
        else
        {
            myGamesViewModel.loadGames()
        }
        return false
    }

    override fun onQueryTextSubmit(searchText: String?): Boolean
    {
        binding.searchView.clearFocus()
        return true
    }
}