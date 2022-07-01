package com.example.retrofitexample.local

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName= "profile_table")
data class ProfileInfo (
    @PrimaryKey
    var picture: String
)