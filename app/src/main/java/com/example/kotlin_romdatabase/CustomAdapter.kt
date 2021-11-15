package com.example.kotlin_romdatabase

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_romdatabase.table.user

class CustomAdapter(val userlist:List<user> ,val customCallBack:CallBackInterface):RecyclerView.Adapter<CustomAdapter.CustomerViewHolder>() {
    class CustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
           val textView :TextView=itemView.findViewById(R.id.textview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
       val view : View = LayoutInflater.from(parent.context).inflate(R.layout.recycleriviewdesign,parent,false)
       return CustomerViewHolder(view)
    }
    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val userdata =userlist.get(position)
        holder.textView.text ="     "+userdata.id.toString()+ " "+userdata.username + " "+userdata.userpassword

        holder.textView.setOnClickListener {
            userdata.id?.let {
                customCallBack.getUserId(it)
            }
        }
        holder.textView.setOnLongClickListener(object :  View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {
                customCallBack.longPressGetUser(userdata)
                return true
            }

        })
    }
    override fun getItemCount(): Int {
        return userlist.size
    }


    interface CallBackInterface{
        fun getUserId(userid :Int)
        fun longPressGetUser(usermodel:user)
    }
}