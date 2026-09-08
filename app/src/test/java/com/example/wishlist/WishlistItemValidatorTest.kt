package com.example.wishlist

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class WishlistItemValidatorTest {
    @Test
    fun validate_acceptsCompleteWishlistItem() {
        val result = WishlistItemValidator.validate(
            name = "  Headphones ",
            price = "$79.99",
            url = " https://example.com ",
        )

        assertTrue(result.isValid)
        assertNull(result.nameError)
        assertNull(result.priceError)
        assertNull(result.urlError)
    }

    @Test
    fun validate_reportsAnErrorForEachBlankField() {
        val result = WishlistItemValidator.validate(name = "", price = "", url = "")

        assertFalse(result.isValid)
        assertEquals("Enter an item name", result.nameError)
        assertEquals("Enter a price", result.priceError)
        assertEquals("Enter a URL", result.urlError)
    }
}
