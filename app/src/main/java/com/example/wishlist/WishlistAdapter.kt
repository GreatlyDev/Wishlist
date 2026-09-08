package com.example.wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WishlistAdapter(
    private val items: List<WishlistItem>,
    private val onItemClick: (WishlistItem) -> Unit,
    private val onItemLongClick: (Int) -> Unit,
) : RecyclerView.Adapter<WishlistAdapter.WishlistItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.wishlist_item, parent, false)
        return WishlistItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WishlistItemViewHolder, position: Int) {
        holder.bind(items[position], onItemClick, onItemLongClick)
    }

    override fun getItemCount(): Int = items.size

    class WishlistItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.wishlistItemName)
        val priceTextView: TextView = itemView.findViewById(R.id.wishlistItemPrice)
        val urlTextView: TextView = itemView.findViewById(R.id.wishlistItemUrl)

        fun bind(
            item: WishlistItem,
            onItemClick: (WishlistItem) -> Unit,
            onItemLongClick: (Int) -> Unit,
        ) {
            nameTextView.text = item.name
            priceTextView.text = item.price
            urlTextView.text = item.url
            itemView.contentDescription = itemView.context.getString(
                R.string.wishlist_item_content_description,
                item.name,
            )
            itemView.setOnClickListener { onItemClick(item) }
            itemView.setOnLongClickListener {
                val position = bindingAdapterPosition
                if (position == RecyclerView.NO_POSITION) return@setOnLongClickListener false

                onItemLongClick(position)
                true
            }
        }
    }
}
