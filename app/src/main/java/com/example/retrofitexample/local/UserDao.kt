package com.example.retrofitexample.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userInfo: UserInfo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(users: List<UserInfo>)

    @Update
    suspend fun update(userInfo: UserInfo)

    @Delete
    suspend fun delete(userInfo: UserInfo)

    @Query("SELECT * FROM user_table")
    fun getAll(): LiveData<List<UserInfo>>

    @Query("SELECT * FROM friends_table")
    fun getAllFriends(): LiveData<List<FriendsInfo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: FriendsInfo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllFriends(user: List<FriendsInfo>)

    @Update
    suspend fun update(user: FriendsInfo)

    @Delete
    suspend fun delete(user: FriendsInfo)

    @Query("SELECT *FROM friends_table where email= :email")
    suspend fun getFriend(email:String): FriendsInfo?

    @Query("SELECT * from user_table where email= :email")
    suspend fun foundedUsers(email:  String) : List<UserInfo>?

}