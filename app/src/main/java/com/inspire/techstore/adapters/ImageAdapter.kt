package com.inspire.techstore.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inspire.techstore.R
import com.makeramen.roundedimageview.RoundedImageView

class ImageAdapter(private var images: ArrayList<String>) : RecyclerView.Adapter<ImageAdapter.Pager2ViewHolder>(){

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val itemImage: RoundedImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(images[position])
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.itemImage)
    }
}