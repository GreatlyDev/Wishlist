package com.example.wishlist

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WishlistFlowTest {
    @Before
    fun initializeIntents() {
        Intents.init()
    }

    @After
    fun releaseIntents() {
        Intents.release()
    }

    @Test
    fun addItem_displaysTheEnteredWishlistRow() {
        ActivityScenario.launch(MainActivity::class.java).use {
            addItem(
                name = "Noise-canceling headphones",
                price = "$79.99",
                url = "https://example.com/headphones",
            )

            onView(withText("Noise-canceling headphones")).check(matches(isDisplayed()))
            onView(withText("$79.99")).check(matches(isDisplayed()))
            onView(withText("https://example.com/headphones")).check(matches(isDisplayed()))
        }
    }

    @Test
    fun longPressItem_confirmsAndRemovesRow() {
        ActivityScenario.launch(MainActivity::class.java).use {
            val name = "Desk lamp"
            addItem(
                name = name,
                price = "$24.99",
                url = "https://example.com/lamp",
            )

            onView(withContentDescription("Wishlist item: $name")).perform(longClick())
            onView(withText("Remove item?")).check(matches(isDisplayed()))
            onView(withText("Remove")).perform(click())

            onView(withText(name)).check(doesNotExist())
        }
    }

    @Test
    fun clickItem_opensItsUrl() {
        ActivityScenario.launch(MainActivity::class.java).use {
            val name = "Coffee grinder"
            val url = "https://example.com/grinder"
            addItem(
                name = name,
                price = "$49.99",
                url = url,
            )

            onView(withContentDescription("Wishlist item: $name")).perform(click())

            Intents.intended(allOf(hasAction(Intent.ACTION_VIEW), hasData(url)))
        }
    }

    private fun addItem(name: String, price: String, url: String) {
        onView(withHint("Item name")).perform(typeText(name))
        onView(withHint("Price")).perform(typeText(price))
        onView(withHint("URL")).perform(typeText(url))
        closeSoftKeyboard()
        onView(withText("Add Item")).perform(click())
    }
}
