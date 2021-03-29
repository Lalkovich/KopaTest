package com.milanlalkovich.kopatest.domain.model.response

import com.milanlalkovich.kopatest.domain.entity.ImageEntity

/**
 *  Created by Android Studio on 05.03.2021 21:35
 *  Developer: Dima Iakubenko
 */

data class BootsModel(
    var images: List<String> = listOf(),
    var title: String = "",
    var width: Double? = null,
    var price: Double? = null,
    var length: Double? = null,
    var bootsLength: Double? = null,
    var material: String = "",
    var description: String = "",
    var isArchived: Boolean = false,
    var userUid: String = "",
)
