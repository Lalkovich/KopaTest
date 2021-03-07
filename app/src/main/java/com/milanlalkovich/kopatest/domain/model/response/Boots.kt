package com.milanlalkovich.kopatest.domain.model.response

data class Boots(
    var imageUrl: String = "",
    var title: String = "",
    var width: Int? = null,
    var length: Int? = null,
    var price: Int? = null,
    var bootsLength: Int? = null,
    var material: String = "",
    var description: String = "",
    var id: String,
    //var isAchieved: Boolean = false,
    //var userUid: String = ""
)