package br.com.ymc.gamesave.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.ymc.gamesave.R
import br.com.ymc.gamesave.databinding.ItemGamesBinding
import br.com.ymc.gamesave.model.Game
import br.com.ymc.gamesave.util.Const
import br.com.ymc.gamesave.util.createImageURL
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
        private val imgCover = binding.imgCover
        private val txtName = binding.txtName

        fun bind(data: Game)
        {
            txtName.text = data.name

            if(data.cover != null)
            {
                Glide.with(imgCover).load(Const.URL_IMAGE_THUMB.createImageURL(data.cover.image_id)).into(imgCover)
            }
            else
            {
                imgCover.setImageResource(R.drawable.ic_no_image)
            }
        }
    }
}