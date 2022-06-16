package com.example.retrofitexample.Search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.databinding.UserListBinding
import com.example.retrofitexample.local.UserInfo
import com.example.retrofitexample.selectedUser
import com.squareup.picasso.Picasso

class LocalUserListAdapter(
    val clickListener: (onNavigate: Boolean) -> Unit
) :
    ListAdapter<UserInfo, LocalUserListAdapter.LocalUserViewHolder>(
        UserInfoDiffItemCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : LocalUserViewHolder = LocalUserViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: LocalUserViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class LocalUserViewHolder(val binding: UserListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): LocalUserViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = UserListBinding.inflate(layoutInflater, parent, false)
                return LocalUserViewHolder(binding)
            }
        }

        fun bind(
            item: UserInfo,
            clickListener: (onNavigate: Boolean) -> Unit
        ) {
            Picasso.get()
                .load(item.picture.large)
                .resize(250, 250)
                .into(binding.photo)
            binding.name.text = item.name.first + " " + item.name.last
            binding.email.text = item.email!!
            binding.phoneNumber.text = item.phone
            binding.viewMoreButton.setOnClickListener {
                selectedUser.userInfo.picture = item.picture
                selectedUser.userInfo.dob = item.dob
                selectedUser.userInfo.location = item.location
                selectedUser.userInfo.name = item.name
                selectedUser.userInfo.email = item.email
                selectedUser.userInfo.phone = item.phone
                clickListener(true)
            }

        }


    }


}

class UserInfoDiffItemCallback : DiffUtil.ItemCallback<UserInfo>() {
    override fun areItemsTheSame(oldItem: UserInfo, newItem: UserInfo) =
        (oldItem.email == newItem.email)

    override fun areContentsTheSame(oldItem: UserInfo, newItem: UserInfo) =
        (oldItem == newItem)

}

