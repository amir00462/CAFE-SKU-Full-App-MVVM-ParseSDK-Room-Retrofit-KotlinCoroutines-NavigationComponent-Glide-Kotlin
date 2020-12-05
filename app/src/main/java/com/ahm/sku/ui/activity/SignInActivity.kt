package com.ahm.sku.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import cn.pedant.SweetAlert.SweetAlertDialog
import com.ahm.coronam.androidWrapper.NetWorkChecker
import com.ahm.sku.R
import com.ahm.sku.ext.BaseActivity
import com.parse.LogInCallback
import com.parse.ParseException
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignInActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

//        SetupKeyboard(parent_SignInActivity)

    }

    // manage the keyboard =>
    private fun hideSoftKeyboard() {
        val inputMethodManager: InputMethodManager = getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            currentFocus!!.windowToken, 0
        )
    }
    @SuppressLint("ClickableViewAccessibility")
    fun SetupKeyboard(view: View) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v, event ->
                hideSoftKeyboard()
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                SetupKeyboard(innerView)
            }
        }
    }

    fun onClickSignIn(view: View) {

        when (view.id) {

            R.id.btn_SignIn_SignInActivity -> {

                if (NetWorkChecker(this).checkNetwork) {

                    if (
                        signIn_IdStudent.text.isNullOrBlank() ||
                        signIn_password.text.isNullOrBlank()
                    ) {

                        toastEasy("لطفا تمام مقادیر را پر کنید")

                    } else {

                        btn_SignIn_SignInActivity.startAnimation()

                        val username = signIn_IdStudent.text.toString()
                        val password = signIn_password.text.toString()

                        // Use Parse to Log In =>
                        ParseUser.logInInBackground(
                            username,
                            password,
                            LogInCallback { user, exception ->
                                if (user != null) {

                                    toastEasy("خوش آمدید")

                                    btn_SignIn_SignInActivity.revertAnimation {
                                        btn_SignIn_SignInActivity.setBackgroundResource(R.drawable.shape_signup_newuser)
                                    }

                                    anim_SignInActivity.playAnimation()

                                    Handler().postDelayed(1500) {
                                        val intent = Intent(this, MainActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        startActivity(intent)
                                    }


                                } else {

                                    btn_SignIn_SignInActivity.revertAnimation {
                                        btn_SignIn_SignInActivity.setBackgroundResource(R.drawable.shape_signup_newuser)
                                    }


                                    when (exception.code) {
                                        101 -> {
                                            toastEasy("شما قبلا عضو نشده اید! ابتدا ثبت نام کنید")
                                        }

                                        ParseException.CONNECTION_FAILED -> {
                                            toastEasy("اینترنت متصل نیست")
                                        }
                                        else -> {
                                            toastEasy(exception.message!!)
                                        }
                                    }

                                    ParseUser.logOut()

                                }

                            })
                    }


                } else {
                    toastEasy("اینترنت را متصل کنید")
                }

            }

            R.id.signIn_Intent -> {

                poshtibany()

            }
        }
    }

    private fun poshtibany() {

        val alertDialog = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
        alertDialog.contentText = "مشکل را به ما اطلاع بده"
        alertDialog.showCancelButton(false)
        alertDialog.confirmText = "پیام به پشتیبانی"
        alertDialog.setConfirmClickListener {

            val number = "+989900668721"
            val url = "https://api.whatsapp.com/send?phone=$number"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)

            toastEasy("ارسال به پشتیبانی : $number")

            alertDialog.dismiss()

        }
        alertDialog.setCancelable(true)
        alertDialog.show()


//        val dialog = Dialog(this)
//        val view = View.inflate(this, R.layout.dialog_reshte_not_found, null) as LinearLayout
//        dialog.setContentView(view)
//        dialog.setCancelable(false)
//        dialog.show()


        //        val nagDialog = Dialog(requireContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
//        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        nagDialog.setCancelable(false)
//        nagDialog.setContentView(R.layout.dialog_full_size_image)
//        val btnClose: Button = nagDialog.findViewById(R.id.btnIvClose) as Button
//        val ivPreview: ImageView = nagDialog.findViewById(R.id.iv_preview_image) as ImageView
//        ivPreview.setBackgroundDrawable(data)
//        btnClose.setOnClickListener {
//            nagDialog.dismiss()
//        }
//        nagDialog.show()


    }

    private fun toastEasy(data: String) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show()
    }

}


/*

override fun onBackPressed() {
    finishAffinity()
}

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_new_user)

    // the first installation of app =>
    ParseInstallation.getCurrentInstallation().saveInBackground()

}

fun onClickNewUser(view: View) {
    when (view.id) {
        R.id.programmer_Instagram -> {
            openProgrammerInstagram()
        }
        R.id.programmer_Website -> {
            openProgrammerWebsite()
        }
        R.id.programmer_Telegram -> {
            openProgrammerTelegram()
        }
        R.id.btn_signIn -> {
            signInIntent()
        }
        R.id.btn_signUp -> {
            signUpIntent()
        }
    }
}

private fun signUpIntent() {
    val intent = Intent(this, SignUpActivity::class.java)
    overridePendingTransition(
        R.anim.fade_in,
        R.anim.fade_out
    )
    startActivity(intent)
}

private fun signInIntent() {
    val intent = Intent(this, SignInActivity::class.java)
    overridePendingTransition(
        R.anim.fade_in,
        R.anim.fade_out
    )
    startActivity(intent)
}

private fun openProgrammerTelegram() {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/amir00462"))
    startActivity(intent)
}

private fun openProgrammerWebsite() {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://daneshgoocenter.ir"))
    startActivity(intent)
}

private fun openProgrammerInstagram() {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.img_instagram.com/amir00462"))
    overridePendingTransition(
        R.anim.fade_in,
        R.anim.fade_out
    )
    startActivity(intent)
}

*/
