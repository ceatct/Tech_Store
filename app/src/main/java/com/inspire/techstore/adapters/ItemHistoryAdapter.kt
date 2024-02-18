package com.inspire.techstore.adapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inspire.techstore.R
import com.inspire.techstore.api.data.ProductModelItem
import com.inspire.techstore.fragments.ViewFragment
import com.makeramen.roundedimageview.RoundedImageView

class ItemHistoryAdapter (private var products: ArrayList<ProductModelItem>) : RecyclerView.Adapter<ItemHistoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Glide.with(holder.itemView.context)
            .load(products[position])
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.image)

        holder.name.text = products[position].title
        holder.price.text = "$ ${products[position].price}"

        holder.itemView.setOnClickListener {

            val id = products[position].id.toString()
            val fragment = ViewFragment()

            fragment.arguments = Bundle().apply {
                putString("id", id)
            }

            (holder.itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragments, fragment)
                .addToBackStack(null)
                .commit()
        }

    }

    override fun getItemCount(): Int {
        return products.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView
        var price: TextView
        var image: RoundedImageView

        init {
            name = itemView.findViewById(R.id.name)
            price = itemView.findViewById(R.id.price)
            image = itemView.findViewById(R.id.image)
        }
    }

}
