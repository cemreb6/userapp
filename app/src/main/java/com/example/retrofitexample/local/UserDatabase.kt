package com.example.retrofitexample.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.retrofitexample.Dob
import com.example.retrofitexample.Location
import com.example.retrofitexample.Name
import com.example.retrofitexample.Picture

@Database(entities = [UserInfo::class,FriendsInfo::class,ProfileInfo::class], version = 13, exportSchema = false)
@TypeConverters(Converter::class)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null
        fun getInstance(context: Context?): UserDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context!!.applicationContext,
                        UserDatabase::class.java,
                        "user_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}