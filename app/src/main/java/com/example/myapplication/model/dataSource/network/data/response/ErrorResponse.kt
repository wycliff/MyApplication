package com.example.myapplication.model.dataSource.network.data.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val errorData: ErrorData?,
    @SerializedName("errors")
    val errorObjects: List<ErrorObject>,
)

data class ErrorData(
    @SerializedName("config")
    val config: Any?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)

data class ErrorObject(
    @SerializedName("field")
    val `field`: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("rule")
    val rule: String,
)