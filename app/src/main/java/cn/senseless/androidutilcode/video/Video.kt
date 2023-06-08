package cn.senseless.androidutilcode.video

import android.os.Parcel
import android.os.Parcelable

data class VideoResp(val code: Int, val result: List<Video>)

data class Video(
    val id: Long,
    val path: String?,
    val cover_path: String?,
    val title: String?,
    val head_url: String?,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Video> {
        override fun createFromParcel(parcel: Parcel): Video {
            return Video(parcel)
        }

        override fun newArray(size: Int): Array<Video?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(path)
        parcel.writeString(cover_path)
        parcel.writeString(title)
        parcel.writeString(head_url)
    }
}