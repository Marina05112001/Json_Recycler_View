package com.example.myapplication19053

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SubItemAdapter(private val subItems: List<SubItem>) : RecyclerView.Adapter<SubItemAdapter.SubItemViewHolder>() {

    class SubItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val subItemName: TextView = itemView.findViewById(R.id.subItemName)
        private val subItemArticul: TextView = itemView.findViewById(R.id.subItemArticul)
        private val subItemImage: TextView = itemView.findViewById(R.id.subItemImage)

        fun bind(subItem: SubItem) {
            subItemName.text = subItem.name
            subItemArticul.text = subItem.articul
            subItemImage.text = subItem.image
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sub_item_view, parent, false)
        return SubItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubItemViewHolder, position: Int) {
        holder.bind(subItems[position])
    }

    override fun getItemCount(): Int = subItems.size
}