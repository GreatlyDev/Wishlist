package com.example.wishlist

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : ComponentActivity() {
    private val wishlistItems = mutableListOf<WishlistItem>()
    private lateinit var wishlistAdapter: WishlistAdapter
    private lateinit var nameInput: EditText
    private lateinit var priceInput: EditText
    private lateinit var urlInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameInput = findViewById(R.id.itemNameInput)
        priceInput = findViewById(R.id.itemPriceInput)
        urlInput = findViewById(R.id.itemUrlInput)

        wishlistAdapter = WishlistAdapter(
            items = wishlistItems,
            onItemClick = ::openWishlistUrl,
            onItemLongClick = ::confirmItemRemoval,
        )
        findViewById<RecyclerView>(R.id.wishlistRecyclerView).apply {
            adapter = wishlistAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        findViewById<Button>(R.id.addItemButton).setOnClickListener {
            addWishlistItem()
        }
    }

    private fun addWishlistItem() {
        val name = nameInput.text.toString().trim()
        val price = priceInput.text.toString().trim()
        val url = urlInput.text.toString().trim()
        val validation = WishlistItemValidator.validate(name, price, url)

        nameInput.error = validation.nameError
        priceInput.error = validation.priceError
        urlInput.error = validation.urlError
        if (!validation.isValid) return

        wishlistItems += WishlistItem(name, price, url)
        wishlistAdapter.notifyItemInserted(wishlistItems.lastIndex)
        findViewById<RecyclerView>(R.id.wishlistRecyclerView).scrollToPosition(wishlistItems.lastIndex)
        nameInput.text.clear()
        priceInput.text.clear()
        urlInput.text.clear()
    }

    private fun openWishlistUrl(item: WishlistItem) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
        if (intent.resolveActivity(packageManager) == null) {
            Toast.makeText(this, R.string.no_browser_available, Toast.LENGTH_SHORT).show()
            return
        }

        startActivity(intent)
    }

    private fun confirmItemRemoval(position: Int) {
        val item = wishlistItems.getOrNull(position) ?: return

        AlertDialog.Builder(this)
            .setTitle(R.string.remove_item_title)
            .setMessage(getString(R.string.remove_item_message, item.name))
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.remove) { _, _ ->
                wishlistItems.removeAt(position)
                wishlistAdapter.notifyItemRemoved(position)
            }
            .show()
    }
}
