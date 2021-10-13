package br.com.ymc.gamesave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.ymc.gamesave.fragments.AllGamesFragment
import br.com.ymc.gamesave.fragments.MyGamesFragment
import java.io.File

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