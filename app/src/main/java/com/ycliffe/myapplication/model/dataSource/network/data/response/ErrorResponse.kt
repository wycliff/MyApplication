package com.ycliffe.myapplication.model.dataSource.network.data.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message")
    val message: String?,
)

