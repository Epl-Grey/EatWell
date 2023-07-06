package com.example.myapplication.firebase

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ProductModel(
    val login: String? = null,
    val name: String? = null
)
