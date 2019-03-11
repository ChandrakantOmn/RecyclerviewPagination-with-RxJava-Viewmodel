package com.omniwyse.pagination.helpers

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

fun ImageView.setGlideImage(context: Context, avatarUrl: String) {
    Glide.with(context)
        .load(avatarUrl)
        .into(this)
}

fun AppCompatActivity.isNetworkConnected():Boolean{
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return (activeNetwork != null && activeNetwork.isConnected)
}
