package com.example.retrofitexample.Friends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.databinding.FriendsListBinding
import com.example.retrofitexample.local.FriendsInfo
import com.example.retrofitexample.selectedUser
import com.squareup.picasso.Picasso

class FriendsListAdapter(
    val clickListener: (onNavigate: Boolean) -> Unit, val delete: (email: String) -> Unit
) :
    ListAdapter<FriendsInfo, FriendsListAdapter.FriendsViewHolder>(
        FriendsDiffItemCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : FriendsViewHolder = FriendsViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, delete)
    }

    class FriendsViewHolder(val binding: FriendsListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): FriendsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FriendsListBinding.inflate(layoutInflater, parent, false)
                return FriendsViewHolder(binding)
            }
        }

        fun bind(
            item: FriendsInfo,
            clickListener: (onNavigate: Boolean) -> Unit,
            delete: (email: String) -> Unit
        ) {
            Picasso.get()
                .load(item.picture.large)
                .resize(250, 250)
                .into(binding.photo)
            binding.name.text = item.name.first + " " + item.name.last
            binding.viewDetailsButton.setOnClickListener {
                selectedUser.userInfo.picture = item.picture
                selectedUser.userInfo.dob = item.dob
                selectedUser.userInfo.location = item.location
                selectedUser.userInfo.name = item.name
                selectedUser.userInfo.email = item.email
                selectedUser.userInfo.phone = item.phone
                clickListener(true)
            }
            binding.deleteButton.setOnClickListener {
                delete(item.email)
            }
        }


    }


}