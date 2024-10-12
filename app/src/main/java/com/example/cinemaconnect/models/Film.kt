package com.example.cinemaconnect.models

import android.os.Parcel
import android.os.Parcelable
import kotlin.collections.ArrayList

data class Film(
    var Title: String? = null,
    var Description: String? = null,
    var Poster: String? = null,
    var Time: String? = null,
    var Trailer: String? = null,
    var Imdb: Int = 0,
    var Year: Int = 0,
    var Price: Double = 0.0,
    var Genre: ArrayList<String> = ArrayList(),
    var Casts: ArrayList<Cast> = ArrayList()
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.createStringArrayList()?:ArrayList(),
        parcel.createTypedArrayList(Cast.CREATOR)?:ArrayList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Title)
        parcel.writeString(Description)
        parcel.writeString(Poster)
        parcel.writeString(Time)
        parcel.writeString(Trailer)
        parcel.writeInt(Imdb)
        parcel.writeInt(Year)
        parcel.writeDouble(Price)
        parcel.writeStringList(Genre)
        parcel.writeTypedList(Casts)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Film> {
        override fun createFromParcel(parcel: Parcel): Film {
            return Film(parcel)
        }

        override fun newArray(size: Int): Array<Film?> {
            return arrayOfNulls(size)
        }
    }
}
