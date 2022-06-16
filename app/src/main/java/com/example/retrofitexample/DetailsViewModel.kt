package com.example.retrofitexample

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitexample.local.FriendsInfo
import com.example.retrofitexample.local.UserDatabase
import com.example.retrofitexample.local.UserInfo
import com.example.retrofitexample.selectedUser
import kotlinx.coroutines.launch

class DetailsViewModel(app: Application) : ViewModel() {

    private val dao = UserDatabase.getInstance(app).userDao
    val name = nameParser()

    private fun nameParser(): String {
        return "${selectedUser.userInfo.name.first} ${selectedUser.userInfo.name.last}"
    }

    val phone = selectedUser.userInfo.phone
    val address = locationParser()

    private fun locationParser(): String {
        val item= selectedUser.userInfo.location
            return "${item.city!!} / ${item.country!!} / ${item.state!!}" +
                    "\n${item.postcode}"
    }

    val age = selectedUser.userInfo.dob.age
    val email = selectedUser.userInfo.email
    val picture = selectedUser.userInfo.picture.large

    fun addFriend() {
        viewModelScope.launch {
            var user = FriendsInfo(
                selectedUser.userInfo.email,
                selectedUser.userInfo.name,
                selectedUser.userInfo.picture,
                selectedUser.userInfo.location,
                selectedUser.userInfo.dob,
                selectedUser.userInfo.phone
            )
            dao.insert(user)
        }
    }

}