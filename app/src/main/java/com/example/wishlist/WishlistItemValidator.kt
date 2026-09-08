package com.example.wishlist

data class WishlistItemValidation(
    val nameError: String?,
    val priceError: String?,
    val urlError: String?,
) {
    val isValid: Boolean
        get() = nameError == null && priceError == null && urlError == null
}

object WishlistItemValidator {
    fun validate(name: String, price: String, url: String): WishlistItemValidation =
        WishlistItemValidation(
            nameError = if (name.isBlank()) "Enter an item name" else null,
            priceError = if (price.isBlank()) "Enter a price" else null,
            urlError = if (url.isBlank()) "Enter a URL" else null,
        )
}
