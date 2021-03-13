package com.milanlalkovich.kopatest.domain.entity

import android.net.Uri

sealed class ImageEntity {
    object SelectImage : ImageEntity()
    data class Image(
        var imageUri: Uri? = null
    ) : ImageEntity()
}
