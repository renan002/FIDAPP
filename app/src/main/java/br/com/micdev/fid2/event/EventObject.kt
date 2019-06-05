package br.com.micdev.fid2.event

import com.google.gson.annotations.SerializedName

data class EventObject (
    @SerializedName("titulo")
    val title: String)