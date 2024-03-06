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

class CartAdapter : RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    private var productList: List<ProductModelItem>? = null

    fun setProductList(productList: List<ProductModelItem>?) {
        this.productList = productList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = productList?.get(position)
        holder.name.text = product?.title
        holder.price.text = "$ ${product?.price}"
        Glide.with(holder.itemView.context)
            .load(product?.image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.image)

        holder.itemView.setOnClickListener {

            val id = productList?.get(position)?.id.toString()
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
        return if (productList == null) 0 else productList!!.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: RoundedImageView
        var name: TextView
        var price: TextView

        init {
            image = itemView.findViewById(R.id.image)
            name = itemView.findViewById(R.id.name)
            price = itemView.findViewById(R.id.price)
        }
    }
}
