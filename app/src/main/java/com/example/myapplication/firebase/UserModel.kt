package com.example.myapplication.firebase

import com.google.firebase.database.IgnoreExtraProperties

const val SEX_MALE: String = "male"
const val SEX_FEMALE: String = "female"

@IgnoreExtraProperties
data class UserModel(
    val login: String? = null,
    val passwordHash: String? = null,
    val sex: String? = null,
    val birthdate: String? = null,
    val weight: String? = null,
    val height: String? = null,
    val activity: String? = null,
    val aim: String? = null
)
