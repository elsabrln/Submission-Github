package com.dicoding.picodiploma.submissiongithub

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.submissiongithub.User
import com.dicoding.picodiploma.submissiongithub.databinding.ItemRowUserBinding


class SearchUserAdapter : RecyclerView.Adapter<SearchUserAdapter.RecyclerViewHolder>(){

    private fun listUsers() = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback?=null
    fun setUser(users: Unit) {

    }
    @SuppressLint("NotifyDataSetChanged")
    fun setUser(items:ArrayList<User>){
        listUsers().clear()
        listUsers().addAll(items)
        this.notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback=onItemClickCallback
    }

    class RecyclerViewHolder(private val binding: ItemRowUserBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            with(binding){
                Glide.with(itemView.context)
                    .load(user.avatar_url)
                    .into(imageView)
                userName.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(listUsers()[position])
        holder.itemView.setOnClickListener{onItemClickCallback?.onItemClicked(listUsers()[position])}
    }

    override fun getItemCount(): Int = listUsers().size

    interface OnItemClickCallback{
        fun onItemClicked(user: User)
    }

    }
