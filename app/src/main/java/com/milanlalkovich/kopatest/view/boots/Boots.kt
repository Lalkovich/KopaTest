package com.milanlalkovich.kopatest.view.boots

import com.google.gson.annotations.SerializedName

data class Boots(
    @SerializedName("image")
    var imageUrl: String = "",
    @SerializedName("title")
    var title: String = "",
    @SerializedName("width")
    var width: Int? = null,
    @SerializedName("price")
    var price: Int? = null,
    @SerializedName("bootsLength")
    var bootsLength: Int? = null,
    @SerializedName("material")
    var material: String = ""
)