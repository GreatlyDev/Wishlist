package com.example.wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WishlistAdapter(
    private val items: List<WishlistItem>,
) : RecyclerView.Adapter<WishlistAdapter.WishlistItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.wishlist_item, parent, false)
        return WishlistItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WishlistItemViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.name
        holder.priceTextView.text = item.price
        holder.urlTextView.text = item.url
    }

    override fun getItemCount(): Int = items.size

    class WishlistItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.wishlistItemName)
        val priceTextView: TextView = itemView.findViewById(R.id.wishlistItemPrice)
        val urlTextView: TextView = itemView.findViewById(R.id.wishlistItemUrl)
    }
}
