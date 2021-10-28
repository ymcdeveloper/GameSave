package br.com.ymc.gamesave.ui.activities

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import br.com.ymc.gamesave.R
import br.com.ymc.gamesave.databinding.ActivityGameDetailBinding
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.model.toGameDB
import br.com.ymc.gamesave.util.*
import br.com.ymc.gamesave.viewModels.GameDetailViewModel
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameDetailActivity : AppCompatActivity(), View.OnClickListener
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
    }

    private fun setObservers()
    {
        //Check if game is already added
        viewModel.isGameAdded.observe(this, {
            if(it)
            {
                binding.fabAdd.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red, null))
                binding.fabAdd.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_delete))
            }
            else
            {
                binding.fabAdd.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorSecondary, null))
                binding.fabAdd.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_add))
            }
        })

        viewModel.game.observe(this, { result ->
            when(result)
            {
                is Resource.Success -> {
                    result.data?.let { setupUI(it) }
                }

                is Resource.Error ->{
                    Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show()
                }

                else -> Unit
            }
        })
    }

    private fun setupUI(game : Game)
    {
        binding.txtName.text = game.name
        binding.txtSummary.text = game.summary
        binding.ratingBar.rating = game.totalRating?.valueToRating() ?: 0f
        binding.txtReleaseDate.text = game.releaseDate?.toDate()
        binding.fabAdd.visibility = View.VISIBLE

        if(game.cover != null)
        {
            Glide.with(this)
                .load(Const.URL_IMAGE_THUMB.createImageURL(game.cover.image_id))
                .into(binding.imgCover)
        }
        else
        {
            Glide.with(this)
                .load(R.drawable.ic_no_image)
                .into(binding.imgCover)
        }

        viewModel.checkGameAdded(game.id)
    }

    private fun setupViewSettings()
    {
        binding.fabAdd.setOnClickListener(this)
        binding.imgBack.setOnClickListener(this)

        binding.ratingBar.isEnabled = false
    }

    override fun onClick(view: View?)
    {
        when(view)
        {
            binding.fabAdd ->
            {
                viewModel.game.let {
                    if(viewModel.isGameAdded.value!!)
                    {
                        viewModel.gameDeleted = true
                        viewModel.deleteGame()
                        Snackbar.make(binding.root, R.string.game_deleted, Snackbar.LENGTH_LONG).show()
                    }
                    else
                    {
                        viewModel.insertGameToDB()
                        Snackbar.make(binding.root, R.string.game_added, Snackbar.LENGTH_LONG).show()
                    }
                }

                gameId?.let { viewModel.checkGameAdded(it) }
            }

            binding.imgBack -> onBackPressed()
        }
    }

    override fun onBackPressed()
    {
        if(viewModel.gameDeleted)
        {
            setResult(RESULT_OK)
        }

        finish()
    }

    //    private fun hideAppBarFab(fab: FloatingActionButton) {
//        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
//        val behavior = params.behavior as FloatingActionButton.Behavior
//        behavior.isAutoHideEnabled = false
//        fab.hide()
//    }
}