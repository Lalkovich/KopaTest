package com.milanlalkovich.kopatest.domain.model.response

/**
 *  Created by Android Studio on 05.03.2021 21:35
 *  Developer: Dima Iakubenko
 */

data class BootsModel(
    var imageUrl: String = "",
    var title: String = "",
    var width: Int? = null,
    var price: Int? = null,
    var length: Int? = null,
    var bootsLength: Int? = null,
    var material: String = "",
    var description: String = "",
    var isArchived: Boolean = false,
    var userUid: String = ""
)
