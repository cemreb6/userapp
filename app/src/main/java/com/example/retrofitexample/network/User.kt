package com.example.retrofitexample

import com.example.retrofitexample.local.UserInfo
import com.google.gson.annotations.SerializedName

data class User(

    @field:SerializedName("results")
    val results: ArrayList<ResultsItem>,
) {
    fun asDatabaseModel(): List<UserInfo> {
        return results.map {
            UserInfo(
                phone = it.phone!!,
                name = it.name!!,
                dob = it.dob!!,
                email = it.email!!,
                location = it.location!!,
                picture = it.picture!!
            )
        }
    }
}

data class ResultsItem(

    @field:SerializedName("phone")
    val phone: String?,

    @field:SerializedName("name")
    val name: Name?,

    @field:SerializedName("email")
    val email: String?,

    @field:SerializedName("location")
    val location: Location?,

    @field:SerializedName("picture")
    val picture: Picture? = null,

    @field:SerializedName("dob")
    val dob: Dob? = null
)

data class Location(
    val country: String? = null,
    val city: String? = null,
    val postcode: String?,
    val state: String? = null
)

data class Name(
    val last: String?,
    val title: String?,
    val first: String?
)

data class Dob(
    val age: String?
)

data class Picture(
    val thumbnail: String? = null,
    val large: String? = null,
    val medium: String? = null
)
