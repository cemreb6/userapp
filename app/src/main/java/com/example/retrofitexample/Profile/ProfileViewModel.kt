package com.example.retrofitexample.Profile

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.*
import androidx.room.Dao
import com.example.retrofitexample.local.ProfileInfo
import com.example.retrofitexample.local.UserDao
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProfileViewModel(val dao: UserDao) : ViewModel() {

    var imageUri=""

    private val _isProfileExistChannel = Channel<Boolean>()
    val isProfileExistChannel = _isProfileExistChannel.receiveAsFlow()


    fun update(){
        viewModelScope.launch {
            dao.deleteAll()
            imageUri.let { dao.insert(ProfileInfo(it)) }
        }
    }
}

class ProfileViewModelFactory(private val dao: UserDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }

}
