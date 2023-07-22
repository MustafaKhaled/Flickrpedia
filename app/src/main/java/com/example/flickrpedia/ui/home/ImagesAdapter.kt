package com.example.flickrpedia.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flickrpedia.data.model.Hit
import com.example.flickrpedia.databinding.ImageListItemBinding


class ImagesAdapter(private val itemList: List<Hit>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ImageListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemText = itemList[position]
        holder.bind(itemText)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    interface OnItemClickListener {
        fun onItemClick(hit: Hit)
    }

    inner class ViewHolder(private val binding: ImageListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val itemText = itemList[position]
                    itemClickListener.onItemClick(itemText)
                }
            }
        }

        fun bind(hit: Hit) {
            Glide.with(binding.root).load(hit.userImageUrl).into(binding.profileImage)
            Glide.with(binding.root).load(hit.webformatUrl).into(binding.image)
            binding.usernameProfile.text = hit.user
            binding.executePendingBindings()
        }
    }
}