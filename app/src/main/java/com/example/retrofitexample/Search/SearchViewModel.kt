package com.example.retrofitexample.Search

import android.app.Application
import androidx.lifecycle.*
import com.example.retrofitexample.User
import com.example.retrofitexample.local.UserDatabase
import com.example.retrofitexample.local.UserInfo
import com.example.retrofitexample.network.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.IOException

class SearchViewModel(private val app: Application) : ViewModel() {
    val dao = UserDatabase.getInstance(app).userDao

    private val userRepository = UserRepository(UserDatabase.getInstance(app))

    val users = userRepository.users

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)


    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private var _foundedUserList: MutableList<UserInfo> = mutableListOf()
    val foundedUsersList: List<UserInfo>
        get() = _foundedUserList


    init {
        refreshDataFromRepository()
    }

    private val _isUserExistChannel = Channel<Boolean>()
    val isUserExistChannel = _isUserExistChannel.receiveAsFlow()

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                userRepository.refreshUsers()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                if (users.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    fun searchUser(email: String) {
        viewModelScope.launch {
            val users = dao.foundedUsers(email)
            _isUserExistChannel.send(!users.isNullOrEmpty())
            if(!users.isNullOrEmpty()){
                _foundedUserList.addAll(users)
            }
        }
    }
}

class SearchViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}