package com.example.movieapp.utils

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.ui.test.SemanticsMatcher
import com.example.semantics.ColorId
import com.example.semantics.DrawableId
import com.example.semantics.ImageUrl

fun hasImageUrl(imageUrl: String): SemanticsMatcher =
    SemanticsMatcher.expectValue(ImageUrl, imageUrl)

fun hasDrawable(@DrawableRes id: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(DrawableId, id)

fun hasColor(@ColorRes id: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(ColorId, id)
