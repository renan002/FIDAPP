package br.com.micdev.fid2.event


import com.google.gson.annotations.SerializedName
import java.util.*

data class EventResponse(
    @SerializedName("content")
    val content: List<Content>,
    @SerializedName("empty")
    val empty: Boolean,
    @SerializedName("first")
    val first: Boolean,
    @SerializedName("last")
    val last: Boolean,
    @SerializedName("number")
    val number: Int,
    @SerializedName("numberOfElements")
    val numberOfElements: Int,
    @SerializedName("pageable")
    val pageable: Pageable,
    @SerializedName("size")
    val size: Int,
    @SerializedName("sort")
    val sort: Sort,
    @SerializedName("totalElements")
    val totalElements: Int,
    @SerializedName("totalPages")
    val totalPages: Int
){
    data class Content(
        @SerializedName("endDate")
        val endDate: Date,
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
        val startDate: Date,
        @SerializedName("tokenText")
        val tokenText: String
    )

    data class Pageable(
        @SerializedName("offset")
        val offset: Int,
        @SerializedName("pageNumber")
        val pageNumber: Int,
        @SerializedName("pageSize")
        val pageSize: Int,
        @SerializedName("paged")
        val paged: Boolean,
        @SerializedName("sort")
        val sort: Sort,
        @SerializedName("unpaged")
        val unpaged: Boolean
    )

    data class Sort(
        @SerializedName("empty")
        val empty: Boolean,
        @SerializedName("sorted")
        val sorted: Boolean,
        @SerializedName("unsorted")
        val unsorted: Boolean
    )
}