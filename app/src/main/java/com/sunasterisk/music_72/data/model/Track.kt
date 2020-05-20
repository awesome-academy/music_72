package com.sunasterisk.music_72.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Track(
    @SerializedName("description")
    val description: String,
    @SerializedName("download_url")
    val downloadUrl: String,
    @SerializedName("downloadable")
    val downloadable: Boolean,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("favoritings_count")
    val favoritingsCount: Int,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("likes_count")
    val likesCount: Int,
    @SerializedName("playback_count")
    val playbackCount: Int,
    @SerializedName("stream_url")
    val streamUrl: String,
    @SerializedName("streamable")
    val streamable: Boolean,
    @SerializedName("title")
    val title: String,
    @SerializedName("user")
    val user: User
) : Parcelable
