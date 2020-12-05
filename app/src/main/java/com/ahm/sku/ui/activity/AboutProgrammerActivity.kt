package com.ahm.sku.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ahm.sku.R
import com.ahm.sku.ext.BaseActivity
import kotlinx.android.synthetic.main.activity_about_programmer.*
import kotlinx.android.synthetic.main.include_main_activity.*
import java.util.*

class AboutProgrammerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_programmer)
        setSupportActionBar(toolbarAboutProgrammer)

        // support RTL =>
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL

        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        Objects.requireNonNull(supportActionBar)!!.setDisplayShowHomeEnabled(true)


        clickListeners()



    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            android.R.id.home -> {

                onBackPressed()
                return true

            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

    }

    private fun clickListeners() {

        programmer_Instagram0.setOnClickListener {
            openProgrammerInstagram()
        }

        programmer_Poonisha0.setOnClickListener {
            openProgrammerPoonisha()
        }

        programmer_Website0.setOnClickListener {
            openProgrammerWebsite()
        }

        programmer_LinkedIn.setOnClickListener {
            openProgrammerLinkedIn()
        }

        btn_goToAnjomanPage.setOnClickListener {

            val intent = Intent(this , AboutAnjomanActivity::class.java)
            startActivity(intent)

        }


    }

    private fun openProgrammerLinkedIn() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/amir-hossein-mohammadi-a1b5a21ba/"))
        startActivity(intent)
    }


    private fun openProgrammerPoonisha() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://ponisha.ir/profile/amir00462"))
        startActivity(intent)
    }

    private fun openProgrammerWebsite() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://daneshgoocenter.ir/"))
        startActivity(intent)
    }

    private fun openProgrammerInstagram() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/amir00462"))
        startActivity(intent)
    }


}