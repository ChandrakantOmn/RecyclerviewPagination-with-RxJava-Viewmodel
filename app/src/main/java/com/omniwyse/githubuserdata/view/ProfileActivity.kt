package com.omniwyse.pagination.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.omniwyse.githubuserdata.R
import com.omniwyse.githubuserdata.model.User
import com.omniwyse.pagination.helpers.setGlideImage
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val user=intent.getParcelableExtra<User>("DATA")
       // profile_img.setGlideImage(this,user.avatarUrl)
      //  profile_name.text=user.login.capitalize()

    }
}



