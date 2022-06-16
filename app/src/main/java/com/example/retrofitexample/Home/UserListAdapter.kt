package com.example.retrofitexample.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.ResultsItem
import com.example.retrofitexample.databinding.UserListBinding
import com.example.retrofitexample.selectedUser
import com.squareup.picasso.Picasso

class UserListAdapter(
    val clickListener: (onNavigate: Boolean) -> Unit
) :
    ListAdapter<ResultsItem, UserListAdapter.UserViewHolder>(
        UserDiffItemCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : UserViewHolder = UserViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class UserViewHolder(val binding: UserListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): UserViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = UserListBinding.inflate(layoutInflater, parent, false)
                return UserViewHolder(binding)
            }
        }

        fun bind(item: ResultsItem, clickListener: (onNavigate: Boolean) -> Unit) {
            binding.email.text = item.email
            binding.name.text = item.name?.first + " " +
                    item.name?.last
            binding.phoneNumber.text = item.phone
            Picasso.get()
                .load(item.picture?.large)
                .resize(250, 250)
                .into(binding.photo)

            binding.viewMoreButton.setOnClickListener {
                selectedUser.userInfo.email = item.email!!
                selectedUser.userInfo.name = item.name!!
                selectedUser.userInfo.phone = item.phone!!
                selectedUser.userInfo.location = item.location!!
                selectedUser.userInfo.dob = item.dob!!
                selectedUser.userInfo.picture = item.picture!!
                clickListener(true)
            }
        }

    }


}