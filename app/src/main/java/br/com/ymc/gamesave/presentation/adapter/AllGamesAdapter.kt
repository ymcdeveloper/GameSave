package br.com.ymc.gamesave.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import br.com.ymc.gamesave.R
import br.com.ymc.gamesave.core.util.Const
import br.com.ymc.gamesave.core.util.createImageURL
import br.com.ymc.gamesave.data.remote.dto.Game
import br.com.ymc.gamesave.databinding.ItemGamesBinding
import com.bumptech.glide.Glide

class AllGamesAdapter(private val arrData : List<Game>? = null) : RecyclerView.Adapter<AllGamesAdapter.MyViewHolder>()
{
    var itemClick: ((gameId : Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        return MyViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_games, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        holder.bind(arrData?.get(position)!!)

        holder.itemView.setOnClickListener {
            itemClick?.invoke(arrData[position].id)
        }
    }

    override fun getItemCount(): Int
    {
        if(arrData == null) return 0

        return arrData.size
    }

    class MyViewHolder(binding: ItemGamesBinding) : RecyclerView.ViewHolder(binding.root)
    {
        private val imgCover : ImageView = binding.imgCover
        private val txtName : TextView = binding.txtName

        fun bind(data: Game)
        {
            txtName.text = data.name

            if(data.cover != null)
            {
                val circularProgressDrawable = CircularProgressDrawable(itemView.context)
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.start()

                Glide.with(imgCover).load(Const.URL_IMAGE_THUMB.createImageURL(data.cover.image_id)).placeholder(circularProgressDrawable).into(imgCover)
            }
            else
            {
                imgCover.setImageResource(R.drawable.ic_no_image)
            }
        }
    }
}