package com.example.myapplication19053

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val context: Context, private val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemName: TextView = itemView.findViewById(R.id.itemName)

        fun bind(item: Item) {
            itemName.text = item.name
            itemView.setOnClickListener {
                if (item.type is String) {
                    // Переход к деталям, если type - String
                    val intent = Intent(context, DetailActivity::class.java).apply {
                        putExtra("name", item.name)
                        putExtra("type", item.type as String)
                    }
                    context.startActivity(intent)
                } else if (item.type is List<*>) {
                    // Переход к новому списку, если type - List<SubItem>
                    val intent = Intent(context, SubItemListActivity::class.java).apply {
                        putExtra("name", item.name)
                        putParcelableArrayListExtra("subItems", item.type as ArrayList<SubItem>)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }
}