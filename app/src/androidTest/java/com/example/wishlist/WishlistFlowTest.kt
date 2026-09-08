package com.example.wishlist

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WishlistFlowTest {
    @Test
    fun addItem_displaysTheEnteredWishlistRow() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withHint("Item name")).perform(typeText("Noise-canceling headphones"))
            onView(withHint("Price")).perform(typeText("$79.99"))
            onView(withHint("URL")).perform(typeText("https://example.com/headphones"))
            closeSoftKeyboard()
            onView(withText("Add Item")).perform(click())

            onView(withText("Noise-canceling headphones")).check(matches(isDisplayed()))
            onView(withText("$79.99")).check(matches(isDisplayed()))
            onView(withText("https://example.com/headphones")).check(matches(isDisplayed()))
        }
    }
}
