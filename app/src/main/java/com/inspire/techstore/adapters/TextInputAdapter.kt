package com.inspire.techstore.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.inspire.techstore.R

class TextInputAdapter(private var inputs: List<String>) : RecyclerView.Adapter<TextInputAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_input_text, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.input.hint = inputs[position]
    }

    override fun getItemCount(): Int {
        return inputs.size
    }

    fun isAllFieldsFilled(): Boolean {
        for (holder in viewHolders) {
            if (holder.input.text.isNullOrEmpty()) {
                return false
            }
        }
        return true
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var input: TextInputEditText

        init {
            input = itemView.findViewById(R.id.input)
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
}
