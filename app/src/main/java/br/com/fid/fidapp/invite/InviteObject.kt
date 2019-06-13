package br.com.fid.fidapp.invite

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class InviteObject(
    @SerializedName("accepted")
    val accepted: Boolean,
    @SerializedName("deleted")
    val deleted: Boolean,
    @SerializedName("eventEndDate")
    val eventEndDate: String,
    @SerializedName("eventId")
    val eventId: Int,
    @SerializedName("eventName")
    val eventName: String,
    @SerializedName("eventPrice")
    val eventPrice: Int,
    @SerializedName("eventStartDate")
    val eventStartDate: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("paid")
    val paid: Boolean,
    @SerializedName("favorito")
    var favorito:Boolean = false
){
    fun toJson():String{
        return Gson().toJson(this)
    }
}