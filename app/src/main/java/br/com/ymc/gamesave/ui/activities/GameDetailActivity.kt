package br.com.ymc.gamesave.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import br.com.ymc.gamesave.R
import br.com.ymc.gamesave.databinding.ActivityGameDetailBinding
import br.com.ymc.gamesave.model.toGameDB
import br.com.ymc.gamesave.util.Const
import br.com.ymc.gamesave.util.Utility
import br.com.ymc.gamesave.util.createImageURL
import br.com.ymc.gamesave.util.valueToRating
import br.com.ymc.gamesave.viewModels.GameDetailViewModel
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameDetailActivity : AppCompatActivity()
{
    private lateinit var binding : ActivityGameDetailBinding
    var gameId : Int? = null

    private val viewModel : GameDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            gameId = it.getInt(Const.EXTRA_GAME_ID)

            gameId?.let { id ->
                if(Utility.isOnline(this))
                {
                    viewModel.getGame(id)
                }
                else
                {
                    viewModel.getGameFromDB(id)
                }
            }
        }

        setupViewSettings()
        setObservers()
        setClickListeners()
    }

    fun setClickListeners()
    {
        binding.fabAdd.setOnClickListener {
            hideAppBarFab(binding.fabAdd)

            viewModel.game.let {
                viewModel.insertGameToDB(viewModel.game.value!!)
            }

            Snackbar.make(binding.root, R.string.game_added, Snackbar.LENGTH_LONG).show()
        }
    }

    fun setObservers()
    {
        //Check if game is already added
        viewModel.isGameAdded.observe(this, {
            if(it)
            {
                hideAppBarFab(binding.fabAdd)
            }
        })

        //Observe game called from api
        viewModel.game.observe(this, { game ->
            binding.txtName.text = game.name
            binding.txtSummary.text = game.summary
            binding.toolbar.title = game.name
            binding.ratingBar.rating = game.total_rating?.valueToRating() ?: 0f

            if(game.cover != null)
            {
                Glide.with(this)
                    .load(Const.URL_IMAGE_THUMB.createImageURL(game.cover.image_id))
                    .into(binding.imgCover)
            }

            viewModel.checkGameAdded(game.id)
        })
    }

    fun setupViewSettings()
    {
        binding.collapsingToolbar.isTitleEnabled = false
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.ratingBar.isEnabled = false
    }

    private fun hideAppBarFab(fab: FloatingActionButton) {
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        fab.hide()
    }
}