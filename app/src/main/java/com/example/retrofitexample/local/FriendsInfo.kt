package com.example.retrofitexample.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.retrofitexample.Dob
import com.example.retrofitexample.Location
import com.example.retrofitexample.Name
import com.example.retrofitexample.Picture

@Entity(tableName = "friends_table")
data class FriendsInfo (
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