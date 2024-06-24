package com.example.food.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food.databinding.UserBinding
import com.example.food.db.tables.User

class UserAdapter(
    private val context: Context,
    private var list: List<User>
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var onDeleteClickListener: OnDeleteClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserBinding.inflate(LayoutInflater.from(context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = list[position]

        holder.userName.text = user.name

        holder.deleteBtn.setOnClickListener {
            onDeleteClickListener?.onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class UserViewHolder(binding: UserBinding) : RecyclerView.ViewHolder(binding.root) {
        val userName = binding.login
        val deleteBtn = binding.deleteBtn
    }

    fun updateList(newList: List<User>) {
        list = newList
        notifyDataSetChanged()
    }

    fun setOnDeleteClickListener(listener: OnDeleteClickListener) {
        onDeleteClickListener = listener
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(position: Int)
    }
}
