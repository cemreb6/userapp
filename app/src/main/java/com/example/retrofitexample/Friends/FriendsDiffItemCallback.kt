package com.example.retrofitexample.Friends

import androidx.recyclerview.widget.DiffUtil
import com.example.retrofitexample.local.FriendsInfo
import com.example.retrofitexample.local.UserInfo

class FriendsDiffItemCallback : DiffUtil.ItemCallback<FriendsInfo>() {
    override fun areItemsTheSame(oldItem: FriendsInfo, newItem: FriendsInfo) =
        (oldItem.email == newItem.email)

    override fun areContentsTheSame(oldItem: FriendsInfo, newItem: FriendsInfo) =
        (oldItem == newItem)

}
