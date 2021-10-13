package br.com.ymc.gamesave.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.ymc.gamesave.R
import br.com.ymc.gamesave.ui.activities.fragments.AllGamesFragment
import br.com.ymc.gamesave.ui.activities.fragments.MyGamesFragment

class MainActivity : AppCompatActivity()
{
    private val allGamesFragment = AllGamesFragment()
    private val myGamesFragment = MyGamesFragment()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}