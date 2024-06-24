package com.example.homework1

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

data class TodoItem(
    val id: String?,
    val text: String?,
    val importance: Importance,
    val deadline: Date? = null,
    var isDone: Boolean,
    val createdAt: Date,
    val updatedAt: Date? = null
)
