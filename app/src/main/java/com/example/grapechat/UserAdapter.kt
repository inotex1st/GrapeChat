package com.example.grapechat

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.grapechat.databinding.ActivitySinghInBinding
import com.example.grapechat.databinding.UserListItemBinding

class UserAdapter : ListAdapter<User, UserAdapter.ItemHolder>(ItemComparator()) {
        class ItemHolder(private val binding: UserListItemBinding) : RecyclerView.ViewHolder(binding.root){
                fun bind(user: User) = with(binding){
                        tvMessage.text = user.message
                        userName.text = user.name
                }
                companion object{
                        fun create(parent: ViewGroup) : ItemHolder{
                                return ItemHolder(UserListItemBinding
                                        .inflate(LayoutInflater.from(parent.context), parent, false))
                        }
                }
        }

        class ItemComparator : DiffUtil.ItemCallback<User>(){
                override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                        return oldItem == newItem
                }

                override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                        return oldItem == newItem
                }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
                TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
                TODO("Not yet implemented")
        }


}