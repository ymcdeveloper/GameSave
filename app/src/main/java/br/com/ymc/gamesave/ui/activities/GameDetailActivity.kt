package br.com.ymc.gamesave.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import br.com.ymc.gamesave.R
import br.com.ymc.gamesave.databinding.ActivityGameDetailBinding
import br.com.ymc.gamesave.util.Const
import br.com.ymc.gamesave.util.createImageURL
import br.com.ymc.gamesave.util.valueToRating
import br.com.ymc.gamesave.viewModels.GameDetailViewModel
import com.bumptech.glide.Glide
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
                viewModel.getGame(id)
            }
        }

        setupViewSettings()

        viewModel.game.observe(this, { game ->
            binding.txtName.text = game.name
            binding.txtSummary.text = game.summary
            binding.toolbar.title = game.name
            binding.ratingBar.rating = game.total_rating.valueToRating()

            if(game.cover != null)
            {
                Glide.with(this)
                    .load(Const.URL_IMAGE_THUMB.createImageURL(game.cover.image_id))
                    .into(binding.imgCover)
            }
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
}