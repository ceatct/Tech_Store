package com.inspire.techstore.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inspire.techstore.R
import com.inspire.techstore.api.data.SelectInputModel
import com.makeramen.roundedimageview.RoundedImageView

class SelectInputAdapter(private var inputs: List<SelectInputModel>) : RecyclerView.Adapter<SelectInputAdapter.MyViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_select_input, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.input.text = inputs[position].name
        holder.image.setImageResource(inputs[position].image)

        holder.checkbox.isChecked = position == selectedPosition

        holder.checkbox.setOnClickListener {
            onCheckboxClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return inputs.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var input: TextView
        var image: RoundedImageView
        var checkbox: CheckBox

        init {
            input = itemView.findViewById(R.id.input)
            image = itemView.findViewById(R.id.image)
            checkbox = itemView.findViewById(R.id.checkbox)
        }
    }

    private val viewHolders = mutableListOf<MyViewHolder>()

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
        super.onViewAttachedToWindow(holder)
        viewHolders.add(holder)
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        super.onViewDetachedFromWindow(holder)
        viewHolders.remove(holder)
    }

    private fun onCheckboxClicked(position: Int) {
        if (selectedPosition != position) {
            val oldPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(oldPosition)
            notifyItemChanged(selectedPosition)
        }
    }

    fun isItemSelected(): Boolean {
        return selectedPosition != RecyclerView.NO_POSITION
    }


}
