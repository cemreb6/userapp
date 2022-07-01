package com.example.retrofitexample.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.retrofitexample.*

@Entity(tableName = "user_table")
data class UserInfo(
    @PrimaryKey
    var email: String,
    @TypeConverters(Converter::class)
    var name: Name,
    @TypeConverters(Converter::class)
    var picture: Picture,
    @TypeConverters(Converter::class)
    var location: Location,
    @TypeConverters(Converter::class)
    var dob: Dob,
    var phone: String
)

 fun List<UserInfo>.asDomainModel(): List<ResultsItem> {
    return map {
        ResultsItem(
            phone = it.phone,
            name= it.name,
            email = it.email,
            location = it.location,
            picture = it.picture,
            dob=it.dob
        )
    }
}
