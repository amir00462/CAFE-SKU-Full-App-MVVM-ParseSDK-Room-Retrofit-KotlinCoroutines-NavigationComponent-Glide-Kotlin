package com.ahm.sku.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ahm.sku.ext.BaseActivity
import com.ahm.sku.model.AppRepository
import com.parse.ParseUser

class SplashActivity : BaseActivity() {
    private lateinit var sharedPreferences: SharedPreferences


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

//        // Mehman Usage =>
//        sharedPreferences = getSharedPreferences(
//            AppRepository.MEHMAN_USER ,
//            Context.MODE_PRIVATE
//        )

        if(ParseUser.getCurrentUser() == null && !sharedPreferences.getBoolean(AppRepository.MEHMAN_USER, false)) {

            val intent = Intent(this, NewUserActivity::class.java)
            startActivity(intent)

        }

        else if (
            ParseUser.getCurrentUser() == null && sharedPreferences.getBoolean(AppRepository.MEHMAN_USER, false)
                ) {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


        } else if (ParseUser.getCurrentUser() != null) {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
        
    }
}