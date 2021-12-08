package br.com.ymc.gamesave.presentation.activities

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import br.com.ymc.gamesave.R
import br.com.ymc.gamesave.databinding.ActivityGameDetailBinding
import br.com.ymc.gamesave.core.util.*
import br.com.ymc.gamesave.data.remote.dto.Game
import br.com.ymc.gamesave.viewModels.GameDetailViewModel
import br.com.ymc.gamesave.viewModels.UIState
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameDetailActivity : AppCompatActivity(), View.OnClickListener
{
    private lateinit var binding : ActivityGameDetailBinding
    var gameId : Int? = null

    private val viewModel : GameDetailViewModel by viewModels()

    private var snackbar: Snackbar? = null

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
            setupFAB(it)
        })

        viewModel.game.observe(this, { game ->
            setupUI(game)
        })

        viewModel.state.observe(this) { state ->
            when (state)
            {
                is UIState.Success ->
                {
                    snackbar?.dismiss()
                }

                is UIState.Error ->
                {
                    binding.root.let {
                        snackbar?.dismiss()
                        Snackbar.make(it, state.message, Snackbar.LENGTH_LONG).show()
                    }
                }

                is UIState.Loading ->
                {
                    binding.root.let {
                        snackbar = Snackbar.make(it, "Loading...", Snackbar.LENGTH_INDEFINITE)
                        snackbar!!.show()
                    }
                }
            }
        }
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

    fun setupFAB(isAdded : Boolean)
    {
        if(isAdded)
        {
            binding.fabAdd.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red, null))
            binding.fabAdd.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_delete))
        }
        else
        {
            binding.fabAdd.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorSecondary, null))
            binding.fabAdd.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_add))
        }
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
                viewModel.game.let { game ->
                    if(viewModel.isGameAdded.value!!)
                    {
                        game.value?.let {
                            viewModel.deleteGame(it)
                        }

                        Snackbar.make(binding.root, R.string.game_deleted, Snackbar.LENGTH_LONG).show()
                    }
                    else
                    {
                        game.value?.let {
                            viewModel.insertGameToDB(it)
                        }

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
        viewModel.isGameDeleted.observe(this, {
            if(it)
            {
                setResult(RESULT_OK)
            }
        })

        finish()
    }

    //    private fun hideAppBarFab(fab: FloatingActionButton) {
//        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
//        val behavior = params.behavior as FloatingActionButton.Behavior
//        behavior.isAutoHideEnabled = false
//        fab.hide()
//    }
}