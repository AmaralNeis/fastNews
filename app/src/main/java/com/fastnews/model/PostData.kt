package com.fastnews.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "postData")
data class PostData(
    @PrimaryKey
    val id: String = "",
    val author: String = "",
    val thumbnail: String = "",
    val name: String = "",
    @SerializedName("num_comments")
    val numComments: Int = 0,
    val score: Int= 0,
    val title: String = "",
    @SerializedName("created_utc")
    val createdUtc: Long = 0L,
    val url: String = "",
    val imageUrl: String? = "") : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().let { it }?: kotlin.run { "" },
        parcel.readString().let { it }?: kotlin.run { "" },
        parcel.readString().let { it }?: kotlin.run { "" },
        parcel.readString().let { it }?: kotlin.run { "" },
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().let { it }?: kotlin.run { "" },
        parcel.readLong(),
        parcel.readString().let { it }?: kotlin.run { "" },
        parcel.readString().let { it }?: kotlin.run { "" }

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(author)
        parcel.writeString(thumbnail)
        parcel.writeString(name)
        parcel.writeInt(numComments)
        parcel.writeInt(score)
        parcel.writeString(title)
        parcel.writeLong(createdUtc)
        parcel.writeString(url)
        parcel.writeString(imageUrl)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostData> {
        override fun createFromParcel(parcel: Parcel): PostData {
            return PostData(parcel)
        }

        override fun newArray(size: Int): Array<PostData?> {
            return arrayOfNulls(size)
        }
    }
}

data class DataResponse(

    val id: String,
    val author: String = "",
    val thumbnail: String = "",
    val name: String = "",
    @SerializedName("num_comments")
    val numComments: Int,
    val score: Int,
    val title: String = "",
    @SerializedName("created_utc")
    val createdUtc: Long,
    val url: String,
    val preview: Preview?)
