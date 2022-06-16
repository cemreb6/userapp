package com.example.retrofitexample.local

import androidx.room.TypeConverter
import com.example.retrofitexample.Dob
import com.example.retrofitexample.Location
import com.example.retrofitexample.Name
import com.example.retrofitexample.Picture
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


class Converter {
    @TypeConverter
    fun toName(data: String?): Name? {
        val gson = Gson()
        if (data == null) {
            return null
        }
        val type: Type = object : TypeToken<Name?>() {}.type
        return gson.fromJson<Name?>(data, type)
    }

    @TypeConverter
    fun nameToObject(myObjects: Name?): String? {
        val gson = Gson()
        return gson.toJson(myObjects)
    }

    @TypeConverter
    fun toPicture(data: String?): Picture? {
        val gson = Gson()
        if (data == null) {
            return null
        }
        val type: Type = object : TypeToken<Picture?>() {}.type
        return gson.fromJson<Picture?>(data, type)
    }

    @TypeConverter
    fun pictureToObject(myObjects: Picture?): String? {
        val gson = Gson()
        return gson.toJson(myObjects)
    }

    @TypeConverter
    fun toDob(data: String?): Dob? {
        val gson = Gson()
        if (data == null) {
            return null
        }
        val type: Type = object : TypeToken<Dob?>() {}.type
        return gson.fromJson<Dob?>(data, type)
    }

    @TypeConverter
    fun dobToObject(myObjects: Dob?): String? {
        val gson = Gson()
        return gson.toJson(myObjects)
    }

    @TypeConverter
    fun toLocation(data: String?): Location? {
        val gson = Gson()
        if (data == null) {
            return null
        }
        val type: Type = object : TypeToken<Location?>() {}.type
        return gson.fromJson<Location?>(data, type)
    }

    @TypeConverter
    fun locationToObject(myObjects: Location?): String? {
        val gson = Gson()
        return gson.toJson(myObjects)
    }
}