package com.whoismacy.android.marsrealestate

import androidx.lifecycle.ViewModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Estate(
    val id: String,
    val type: String,
    @SerialName("image_src") val imageUrl: String,
    val price: Int,
)

class EstateViewModel : ViewModel()
