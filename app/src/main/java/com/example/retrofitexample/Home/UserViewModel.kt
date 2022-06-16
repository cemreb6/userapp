package com.example.retrofitexample

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitexample.local.UserInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class UserViewModel : ViewModel() {
    lateinit var users : User


    private var _isUserExistChannel = Channel<Boolean>()
    val isUserExistChannel = _isUserExistChannel.receiveAsFlow()

    fun getUsers() {
        viewModelScope.launch {
            try {
                users=ApiClient.getApiClient().getUsers(10)
                _isUserExistChannel.send(!users.results.isNullOrEmpty())


            } catch (e: Exception) {
                e.message?.let { it1 -> Log.e("Error", it1) }
            }
        }
    }



}

object selectedUser{
    var pic= Picture()
    var name = Name("","","")
    var location= Location("","","","")
    var dob= Dob("")
    var userInfo= UserInfo("",name,pic, location,dob,"")

}