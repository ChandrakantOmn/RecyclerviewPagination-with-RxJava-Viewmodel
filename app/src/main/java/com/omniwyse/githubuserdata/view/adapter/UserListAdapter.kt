package com.omniwyse.githubuserdata.view.adapter

import android.annotation.TargetApi
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.omniwyse.githubuserdata.R
import com.omniwyse.githubuserdata.model.User
import com.omniwyse.githubuserdata.view.MainActivity
import com.omniwyse.pagination.helpers.setGlideImage
import com.omniwyse.pagination.view.ProfileActivity
import java.util.*


class UserListAdapter(private val mCtx: Context, var listType: Int) :
    RecyclerView.Adapter<UserListAdapter.ItemViewHolder>() {

    private var items: MutableList<User> = ArrayList()
    fun addItems(items: List<User>) {
        this.items.addAll(items)
        notifyDataSetChanged()
        Log.d("SIZE", "${this.items.size}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: View? = if (listType != 0) LayoutInflater.from(mCtx).inflate(R.layout.user_grid, parent, false)
        else LayoutInflater.from(mCtx).inflate(R.layout.user_item, parent, false)
        return ItemViewHolder(view!!)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item.login
        holder.imageView.setGlideImage(mCtx, item.avatarUrl)
        holder.userLayout.setOnClickListener {
         //   val pairs1: Pair<View, String> = Pair.create(holder.imageView, "imageTranse")
//            val options = ActivityOptions.makeSceneTransitionAnimation(mCtx as MainActivity, pairs1)
            mCtx.startActivity(
                Intent(mCtx, ProfileActivity::class.java))
                  //  .putExtra("DATA", item), options.toBundle()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getLastVisibleItemId(): Int {
        return if (items.isEmpty()) {
            0
        } else items[items.size - 1].id
    }

    fun getList(): MutableList<User> {
        return items
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.profile_name)
        var imageView: ImageView = itemView.findViewById(R.id.profile_imageview)
        var userLayout: RelativeLayout = itemView.findViewById(R.id.profie_layout)

    }

}
