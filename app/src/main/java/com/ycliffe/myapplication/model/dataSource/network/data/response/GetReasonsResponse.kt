package com.ycliffe.myapplication.model.dataSource.network.data.response

import com.google.gson.annotations.SerializedName

data class GetReasonsResponse(
    @SerializedName("data")
    val reasons: List<ReasonsData>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)
data class ReasonsData(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String
)