package com.example.retrofitexample.Home

import androidx.recyclerview.widget.DiffUtil
import com.example.retrofitexample.ResultsItem

class UserDiffItemCallback : DiffUtil.ItemCallback<ResultsItem>() {
    override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem) =
        (oldItem.email == newItem.email)

    override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem) =
        (oldItem == newItem)

}
