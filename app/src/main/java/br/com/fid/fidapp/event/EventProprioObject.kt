package br.com.fid.fidapp.event

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EventProprioObject (
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("eventCode")
    val eventCode: String?,
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
    val startDate: String,
    @SerializedName("tokenText")
    val tokenText: String?,
    @SerializedName("favoritado")
    var favoritado:Boolean = false
): Serializable{

    fun toJson(): String{
        return Gson().toJson(this)
    }
}
class CompareEventProprioObject:Comparator<EventProprioObject>{
    override fun compare(o1: EventProprioObject, o2: EventProprioObject): Int {
        val fav1 = o1.favoritado
        val fav2 = o2.favoritado

        //Log.e("Compare",fav1.toString()+fav2.toString())
        return fav2.compareTo(fav1)
    }
}