package com.example.androidjobtask.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.example.androidjobtask.R
import com.example.androidjobtask.presentation.data.Item
import com.skydoves.expandablelayout.ExpandableLayout

class TouristAdapter(private val items: MutableList<Item>) : RecyclerView.Adapter<TouristAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ex = itemView.findViewById<ExpandableLayout>(R.id.expandable)
        val textView: TextView = itemView.findViewById(R.id.touristName)
        var g = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_add_cricketer, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]

        holder.apply {
            holder.textView.text = item.text

            holder.ex.setOnClickListener {
                if (holder.g == 0){
                    g=1
                    ex.expand()
                }else{
                    g=0
                    ex.collapse()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: Item) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun removeItem(position: Int) {
        if (position >= 0 && position < items.size) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}

