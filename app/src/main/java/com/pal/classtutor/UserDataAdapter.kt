package com.pal.classtutor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide



//ngrok
class UserDataAdapter(
    private val context: Context,
) :
    RecyclerView.Adapter<UserDataAdapter.ViewHolder>() {

    private var heroList: ArrayList<UserModel> = ArrayList()

    fun setNewList(newList: ArrayList<UserModel>) {
        heroList = newList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_data_layout, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = heroList[position]
        holder.nameView.text = listItem.firstName +" " +listItem.lastName
        holder.emailView.text= listItem.email

        Glide.with(context)
            .load(listItem.avatar)
            .into(holder.imageUser)

    }

    override fun getItemCount(): Int {
        return heroList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageUser: ImageView = itemView.findViewById(R.id.imageUser)
        val emailView: TextView = itemView.findViewById(R.id.emailView)
        val nameView: TextView = itemView.findViewById(R.id.nameView)

    }
}