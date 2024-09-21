package com.example.semantics

import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver

val ImageUrl = SemanticsPropertyKey<String>("ImageUrl")
var SemanticsPropertyReceiver.imageUrlProperty by ImageUrl

val DrawableId = SemanticsPropertyKey<Int>("DrawableResId")
var SemanticsPropertyReceiver.drawableId by DrawableId

val ColorId = SemanticsPropertyKey<Int>("ColorResId")
var SemanticsPropertyReceiver.colorId by ColorId
