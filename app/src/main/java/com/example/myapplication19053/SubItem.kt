package com.example.myapplication19053

import android.content.Context
import android.os.Parcel
import android.os.Parcelable

data class SubItem(
    val name: String,
    val articul: String,
    val image: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(articul)
        parcel.writeString(image)
    }

    override fun describeContents(): Int = 0

    fun getImageResource(context: Context): Int {
        return context.resources.getIdentifier(image, "drawable", context.packageName)
    }

    companion object CREATOR : Parcelable.Creator<SubItem> {
        override fun createFromParcel(parcel: Parcel): SubItem = SubItem(parcel)
        override fun newArray(size: Int): Array<SubItem?> = arrayOfNulls(size)
    }
}
