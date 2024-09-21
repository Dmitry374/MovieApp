package com.example.movieapp.tests

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import com.example.details.R
import com.example.movieapp.ui.MainActivity
import com.example.movieapp.utils.hasColor
import com.example.movieapp.utils.hasDrawable
import com.example.movieapp.utils.hasImageUrl
import org.junit.Rule
import org.junit.Test

class MoviesTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testOpenMovieDetails() = with(composeTestRule) {
        // Проверка отображения BottomBar и SearchBar
        onNodeWithTag("bottom_bar").assertIsDisplayed()
        onNodeWithTag("search_bar").assertIsDisplayed()

        // Проверка отображения списка фильмов
        waitUntil(timeoutMillis = 3000) {
            onNodeWithTag("movies_list").isDisplayed()
        }

        // Проверка ввода названия фильма в строку поиска
        onNodeWithTag("search_bar").apply {
            onNodeWithContentDescription("Search").performTextInput("Point Break")
            onNodeWithContentDescription("Search").assertTextContains("Point Break")
        }

        // Проверка скрытия списка фильмов и отображения списка результатов поиска
        waitUntil(timeoutMillis = 3000) {
            onNodeWithTag("movies_list").isNotDisplayed()
            onNodeWithTag("search_result_lazy_list").isDisplayed()
        }

        // Проверка отображения списка с результатами поиска
        onNodeWithTag("search_result_lazy_list")
            .performScrollToNode(hasTestTag("search_item_0") and hasText("Point Break") and hasText("2015") and hasImageUrl("https://cdn.watchmode.com/posters/01303770_poster_w185.jpg"))
            .performScrollToNode(hasTestTag("search_item_1") and hasText("Point Break") and hasText("1991") and hasImageUrl("https://cdn.watchmode.com/posters/01303771_poster_w185.jpg"))
            .performScrollToNode(hasTestTag("search_item_2") and hasText("Point Break Left") and hasText("2023") and hasImageUrl("https://cdn.watchmode.com/posters/01710219_poster_w185.jpg"))
            .assertIsDisplayed()

        // Нажимаем на первый элемент списка
        onNodeWithTag("search_item_0").performClick()

        // Проверка отображения деталей фильма
        waitUntil(timeoutMillis = 3000) {
            onNodeWithTag("details_screen").isDisplayed()
            onNode(hasTestTag("details_toolbar_title") and hasText("Details")).isDisplayed()
            onNode(hasTestTag("details_backdrop_url") and hasImageUrl("https://cdn.watchmode.com/backdrops/01303770_bd_w780.jpg")).isDisplayed()

            onNode(hasTestTag("details_rating_icon") and hasDrawable(R.drawable.ic_favorite) and hasColor(R.color.rating_text_color)).isDisplayed()
            onNode(hasTestTag("details_rating_text") and hasText("5.1")).isDisplayed()

            onNode(hasTestTag("details_poster_url") and hasImageUrl("https://cdn.watchmode.com/posters/01303770_poster_w185.jpg")).isDisplayed()
            onNode(hasTestTag("details_movie_title") and hasText("Point Break")).isDisplayed()

            onNode(hasTestTag("details_release_date_icon") and hasDrawable(R.drawable.ic_calendar)).isDisplayed()
            onNode(hasTestTag("details_release_date_text") and hasText("2015-12-03")).isDisplayed()

            onNode(hasTestTag("details_duration") and hasText("113 minutes")).isDisplayed()

            onNode(hasTestTag("details_genre_icon") and hasDrawable(R.drawable.ic_ticket)).isDisplayed()
            onNode(hasTestTag("details_genre_text") and hasText("Action")).isDisplayed()

            onNode(hasTestTag("details_description_title") and hasText("Description")).isDisplayed()
            onNode(hasTestTag("details_description") and hasText("A young undercover FBI agent infiltrates a gang of thieves who share a common interest in extreme sports. A remake of the 1991 film, \"Point Break\".")).isDisplayed()

            onNode(hasTestTag("details_genres") and hasText("Action • Adventure • Crime • Thriller")).isDisplayed()
        }

        // Проверка добавления фильма в изранные
        onNodeWithTag("details_add_to_favorite_button").performClick()
        waitUntil(timeoutMillis = 3000) {
            onNode(hasTestTag("details_add_to_favorite_button") and hasDrawable(R.drawable.ic_love) and hasColor(R.color.favorite)).isDisplayed()
        }

        // Проверка удаление фильма из избранных
        onNodeWithTag("details_add_to_favorite_button").performClick()
        waitUntil(timeoutMillis = 3000) {
            onNode(hasTestTag("details_add_to_favorite_button") and hasDrawable(R.drawable.ic_love_border) and hasColor(R.color.favorite)).isDisplayed()
        }
    }
}
