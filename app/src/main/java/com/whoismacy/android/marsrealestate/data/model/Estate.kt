package com.whoismacy.android.marsrealestate.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Estate(
    val id: String,
    val type: String,
    @param:Json(name = "img_src") val imageUrl: String? = null,
    val price: Int,
)
