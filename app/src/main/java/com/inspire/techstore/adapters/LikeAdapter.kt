package com.inspire.techstore.adapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inspire.techstore.R
import com.inspire.techstore.db.like.Like
import com.inspire.techstore.db.like.LikeViewModel
import com.inspire.techstore.fragments.ViewFragment
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LikeAdapter (private var viewModelProvider: ViewModelProvider) : RecyclerView.Adapter<LikeAdapter.MyViewHolder>() {

    private lateinit var likeViewModel: LikeViewModel
    private var productList: List<Like>? = null
    private var recyclerView: RecyclerView? = null // Store RecyclerView reference

    fun setProductList(productList: List<Like>?) {
        this.productList = productList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_like, parent, false)
        likeViewModel = viewModelProvider[LikeViewModel::class.java]
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = productList?.get(position)

        likeViewModel = viewModelProvider[LikeViewModel::class.java]

        // Load image only if item is visible
        if (isVisible()) {
            loadImage(holder, product)
        }

        // Set other item data
        holder.name.text = product?.title
        holder.oldPrice.text = "$1.20"
        holder.price.text = "$" + product?.price.toString()

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

        holder.action.setOnClickListener{
            deleteItem(productList?.get(position)!!)
            Toast.makeText(holder.itemView.context, "Deleted!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun deleteItem(like: Like){
        likeViewModel.deleteLike(like)
    }

    override fun getItemCount(): Int {
        return if (productList == null) 0 else productList!!.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    private fun isVisible(): Boolean {
        val firstVisibleItem = (recyclerView!!.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
        val lastVisibleItem = (recyclerView!!.layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()
        return getCurrentRecyclerViewPosition(recyclerView!!) in firstVisibleItem..lastVisibleItem
    }

    private fun getCurrentRecyclerViewPosition(recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
        return layoutManager?.findFirstVisibleItemPosition() ?: RecyclerView.NO_POSITION
    }

    @SuppressLint("SetTextI18n")
    private fun loadImage(holder: MyViewHolder, product: Like?) {
        CoroutineScope(Dispatchers.IO).launch {
            val bitmap = Glide.with(holder.itemView.context)
                .asBitmap()
                .load(product?.image)
                .submit()
                .get()

            withContext(Dispatchers.Main) {
                holder.imageView.setImageBitmap(bitmap)
            }
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: RoundedImageView
        var name: TextView
        var oldPrice: TextView
        var price: TextView
        var action: ImageView

        init {
            imageView = itemView.findViewById(R.id.imageView)
            name = itemView.findViewById(R.id.name)
            oldPrice = itemView.findViewById(R.id.old_price)
            price = itemView.findViewById(R.id.price)
            action = itemView.findViewById(R.id.action)
        }
    }
}
