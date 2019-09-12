package com.mburakcakir.kotlinrestsample.ui

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import com.mburakcakir.kotlinrestsample.R
import com.mburakcakir.kotlinrestsample.model.UserModel
import com.github.siyamed.shapeimageview.HexagonImageView
import com.mburakcakir.kotlinrestsample.utils.Utils


class UserAdapter(val userList: List<UserModel>,val  thisActivity: Activity): androidx.recyclerview.widget.RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder?.txtUserName?.text = userList[position].name + " " + userList[position].surname.toUpperCase()
        holder?.txtTime?.text = Utils.generateRandomTime()
        holder?.txtAge?.text = userList[position].age.toString()
        holder?.imgProfile?.setImageBitmap(Utils.getBitmapByString(userList[position].profileImage))
    }

    override fun onCreateViewHolder(parentView: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(parentView?.context).inflate(R.layout.cardview_user, parentView, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner  class ViewHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        val txtUserName = itemView.findViewById<TextView>(R.id.txtUserName)
        val txtTime = itemView.findViewById<TextView>(R.id.txtTime)
        val txtAge = itemView.findViewById<TextView>(R.id.txtAge)
        val imgProfile:HexagonImageView = itemView.findViewById<HexagonImageView>(R.id.imgProfile)


        init {

            itemView.setOnClickListener { v: View  ->
                var indexOfList: Int = getAdapterPosition()
                Utils.openActivity(thisActivity,userList.get(indexOfList))
            }
        }
    }

}