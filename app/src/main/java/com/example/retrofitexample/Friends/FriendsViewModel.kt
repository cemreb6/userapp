package com.example.retrofitexample.Friends

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitexample.local.UserDatabase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FriendsViewModel(app: Application) :ViewModel(){
    val dao= UserDatabase.getInstance(app).userDao
    val friendsList=dao.getAllFriends()

    private val _isUserExistChannel = Channel<Boolean>()
    val isUserExistChannel = _isUserExistChannel.receiveAsFlow()

    fun findUser(email:String){
        viewModelScope.launch {
            var user= dao.getFriend(email)
            val isUserExist= user !=null

            if(isUserExist){
                if (user != null) {
                    dao.delete(user)
                }
            }

            _isUserExistChannel.send(isUserExist)
        }
    }

}