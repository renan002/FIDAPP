package br.com.micdev.fid2.invite


import com.google.gson.annotations.SerializedName

data class InviteResponse(
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isPublic")
    val isPublic: Boolean,
    @SerializedName("maxCapacity")
    val maxCapacity: Int,
    @SerializedName("minCapacity")
    val minCapacity: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("ownerName")
    val ownerName: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("startDate")
    val startDate: String
)