package com.milanlalkovich.kopatest.domain.model.response

data class Boots(
    var images: List<String> = listOf(),
    var title: String = "",
    var width: Double? = null,
    var price: Double? = null,
    var length: Double? = null,
    var bootsLength: Double? = null,
    var material: String = "",
    var description: String = "",
    var id: String,
    var isArchived: Boolean = false,
    var userUid: String = ""
)