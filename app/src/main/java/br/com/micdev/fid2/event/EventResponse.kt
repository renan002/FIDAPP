package br.com.micdev.fid2.event


import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("content")
    val content: List<Content>,
    @SerializedName("empty")
    val empty: Boolean, // true
    @SerializedName("first")
    val first: Boolean, // true
    @SerializedName("last")
    val last: Boolean, // true
    @SerializedName("number")
    val number: Int, // 0
    @SerializedName("numberOfElements")
    val numberOfElements: Int, // 0
    @SerializedName("pageable")
    val pageable: Pageable,
    @SerializedName("size")
    val size: Int, // 0
    @SerializedName("sort")
    val sort: Sort,
    @SerializedName("totalElements")
    val totalElements: Int, // 0
    @SerializedName("totalPages")
    val totalPages: Int // 0
){
    data class Sort(
        @SerializedName("empty")
        val empty: Boolean, // true
        @SerializedName("sorted")
        val sorted: Boolean, // true
        @SerializedName("unsorted")
        val unsorted: Boolean // true
    )
    data class Pageable(
        @SerializedName("offset")
        val offset: Int, // 0
        @SerializedName("pageNumber")
        val pageNumber: Int, // 0
        @SerializedName("pageSize")
        val pageSize: Int, // 0
        @SerializedName("paged")
        val paged: Boolean, // true
        @SerializedName("sort")
        val sort: Sort,
        @SerializedName("unpaged")
        val unpaged: Boolean // true
    )
    data class Content(
        @SerializedName("endDate")
        val endDate: String, // 2019-06-04T00:45:30.294Z
        @SerializedName("id")
        val id: Int, // 0
        @SerializedName("isPublic")
        val isPublic: Boolean, // true
        @SerializedName("maxCapacity")
        val maxCapacity: Int, // 0
        @SerializedName("minCapacity")
        val minCapacity: Int, // 0
        @SerializedName("name")
        val name: String, // string
        @SerializedName("ownerName")
        val ownerName: String, // string
        @SerializedName("price")
        val price: Int, // 0
        @SerializedName("startDate")
        val startDate: String, // 2019-06-04T00:45:30.294Z
        @SerializedName("tokenText")
        val tokenText: String // string
    )
}