package com.prongbang.paging.feature.user.ui.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.prongbang.paging.R
import com.prongbang.paging.databinding.UserItemBinding
import com.prongbang.paging.feature.user.model.User

class UserViewHolder(
    private val mBinding: UserItemBinding
) : RecyclerView.ViewHolder(mBinding.root) {

    fun bind(item: User?) {
        mBinding.user = item
        mBinding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): UserViewHolder {

            return UserViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.user_item, parent,
                    false
                )
            )
        }
    }
}