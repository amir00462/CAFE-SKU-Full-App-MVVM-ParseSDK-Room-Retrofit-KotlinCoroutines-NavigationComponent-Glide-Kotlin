package com.ahm.sku.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import cn.pedant.SweetAlert.SweetAlertDialog
import com.ahm.coronam.androidWrapper.NetWorkChecker
import com.ahm.sku.R
import com.ahm.sku.ext.BaseActivity
import com.ahm.sku.model.AppRepository
import com.parse.ParseException
import com.parse.ParseUser
import com.parse.SignUpCallback
import com.rengwuxian.materialedittext.MaterialEditText
import kotlinx.android.synthetic.main.activity_sign_up.*


/*
//private fun initTextInputs() {
//
//    val autoCompleteSubject = inputSubjectAddNewText.editText as? AutoCompleteTextView
//    val autoCompleteChild = inputChildAddNewText.editText as? AutoCompleteTextView
//
//    val itemsSubject = resources.getStringArray(R.array.chipGroupAll)
//    val adapter = ArrayAdapter(requireContext(), R.layout.item_string_array, itemsSubject)
//
//    autoCompleteSubject?.setAdapter(adapter)
//    autoCompleteSubject?.setOnItemClickListener { _, _, _, _ ->
//
//        when(autoCompleteSubject.text.toString()) {
//
//            "تولد" -> {
//                val itemsChild = resources.getStringArray(R.array.chipGroupTavalodType)
//                val adapterChild = ArrayAdapter(requireContext() , R.layout.item_string_array , itemsChild)
//                autoCompleteChild?.setAdapter(adapterChild)
//            }
//
//            "سالگرد" -> {
//                val itemsChild = resources.getStringArray(R.array.chipGroupSalgardType)
//                val adapterChild = ArrayAdapter(requireContext() , R.layout.item_string_array , itemsChild)
//                autoCompleteChild?.setAdapter(adapterChild)
//            }
//
//            "عید ها" -> {
//                val itemsChild = resources.getStringArray(R.array.chipGroupEidType)
//                val adapterChild = ArrayAdapter(requireContext() , R.layout.item_string_array , itemsChild)
//                autoCompleteChild?.setAdapter(adapterChild)
//            }
//
//            "جک ها" -> {
//                val itemsChild = resources.getStringArray(R.array.chipGroupJokeType)
//                val adapterChild = ArrayAdapter(requireContext() , R.layout.item_string_array , itemsChild)
//                autoCompleteChild?.setAdapter(adapterChild)
//            }
//
//            else -> {
//                Toast.makeText(context , "خطای ناشناخته رخ داده است" , Toast.LENGTH_SHORT).show()
//            }
//
//        }
//
//
//    }
//
//
//
//}
*/

class SignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

//        SetupKeyboard(parent_SignUpActivity)
        initTextInputs()

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

    fun onClickSignUp(view: View) {
        when (view.id) {

            R.id.btn_signUp -> {


                if (NetWorkChecker(this).checkNetwork) {

                    if (
                        signUp_IdStudent.text.isNullOrBlank() ||
                        signUp_PhoneNumber.text.isNullOrBlank() ||
                        signUp_name.text.isNullOrBlank() ||
                        SignUp_txt_Year.text.isNullOrBlank() ||
                        SignUp_txt_NameDaneshkade.text.isNullOrBlank() ||
                        SignUp_txt_NameReshte.text.isNullOrBlank()
                    ) {
                        toastEasy("لطفا تمام مقادیر را پر کنید")
                    } else {

                        btn_signUp.startAnimation()

                        val idStudent = signUp_IdStudent.text.toString()
                        val number = signUp_PhoneNumber.text.toString()
                        val name = signUp_name.text.toString()
                        val voroodi = SignUp_txt_Year.text.toString()
                        val daneshkade = SignUp_txt_NameDaneshkade.text.toString()
                        val reshte = SignUp_txt_NameReshte.text.toString()

                        val user = ParseUser()
                        user.username = idStudent
                        user.setPassword(number)
                        user.put(AppRepository.NAME, name)
                        user.put(AppRepository.RESHTE, reshte)
                        user.put(AppRepository.VOROODI, voroodi)
                        user.put(AppRepository.NUMBER, number)
                        user.put(AppRepository.DANESHKADE, daneshkade)

                        // use Parse to Sign Up =>
                        user.signUpInBackground(SignUpCallback { itExcept ->


                            if (itExcept == null) {

                                toastEasy("ثبت نام با موفقیت انجام شد")

                                btn_signUp.revertAnimation {
                                    btn_signUp.setBackgroundResource(R.drawable.shape_signup_newuser)
                                }

                                anim_SignUpActivity.playAnimation()

                                Handler().postDelayed(1500) {
                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                }

                            } else {

                                btn_signUp.revertAnimation {
                                    btn_signUp.setBackgroundResource(R.drawable.shape_signup_newuser)
                                }

                                ParseUser.logOut()

                                when (itExcept.code) {
                                    ParseException.USERNAME_TAKEN -> {
                                        toastEasy("این نام کاربری قبلا انتخاب شده است")
                                    }
                                    ParseException.CONNECTION_FAILED -> {
                                        toastEasy("اینترنت متصل نیست")
                                    }
                                    ParseException.USERNAME_MISSING -> {
                                        toastEasy("نام کاربری وارد نشده است")
                                    }
                                    ParseException.PASSWORD_MISSING -> {
                                        toastEasy("رمز ورود وارد نشده است")
                                    }
                                    else -> {
                                        toastEasy(itExcept.message!!)
                                    }
                                }

                            }

                        })

                    }
                } else {
                    toastEasy("اینترنت را متصل کنید")
                }


                /*

                if (
                    signUp_IdStudent.text.isNullOrBlank()

                        ) {

                    toastEasy("لطفا تمام مقادیر را پر کنید")

                } else {
                    btn_signUp.startAnimation()
                    val idStudent = signUp_IdStudent.text.toString()
                    val number = signUp_PhoneNumber.text.toString()
                    val name = signUp_name.text.toString()
                    val daneshkade = SignUp_txt_NameDaneshkade.text.toString()
                    val reshte = SignUp_txt_NameReshte.text.toString()

                    val user = ParseUser()
                    user.username = idStudent
                    user.setPassword(number)
                    user.put(AppRepository.NAME, name)
                    user.put(AppRepository.RESHTE, reshte)
                    user.put(AppRepository.NUMBER, number)
                    user.put(AppRepository.DANESHKADE, daneshkade)

                    // use Parse to Sign Up =>
                    user.signUpInBackground(SignUpCallback { itExcept ->


                        if (itExcept == null) {
                            toastEasy("ثبت نام با موفقیت انجام شد")

                            val color = ContextCompat.getColor(this, android.R.color.white)
                            val icon = BitmapFactory.decodeResource(
                                resources,
                                R.drawable.ic_check_accept_success
                            )
                            btn_signUp.doneLoadingAnimation(color, icon)


                            anim_SignUpActivity.playAnimation()

                            Handler().postDelayed(1500) {
                                val intent = Intent(this, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }

                        } else {

                            btn_signUp.revertAnimation()

                            ParseUser.logOut()

                            when (itExcept.code) {
                                ParseException.USERNAME_TAKEN -> {
                                    toastEasy("این نام کاربری قبلا انتخاب شده است")
                                }
                                ParseException.CONNECTION_FAILED -> {
                                    toastEasy("اینترنت متصل نیست")
                                }
                                ParseException.USERNAME_MISSING -> {
                                    toastEasy("نام کاربری وارد نشده است")
                                }
                                ParseException.PASSWORD_MISSING -> {
                                    toastEasy("رمز ورود وارد نشده است")
                                }
                                else -> {
                                    toastEasy(itExcept.message!!)
                                }
                            }

                        }
                    })



                }


                */
            }

            R.id.signUp_NotFoundReshte -> {
                notFoundReshte()
            }

        }
    }

    private fun notFoundReshte() {

//        val dialog = Dialog(this)
//        val view = View.inflate(this, R.layout.dialog_reshte_not_found, null) as LinearLayout
//
//        dialog.setContentView(view)
//        dialog.setCancelable(true)
//        dialog.show()


        val alertDialog = SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)

        val view = View.inflate(this, R.layout.dialog_reshte_not_found, null) as LinearLayout
        val autoComp = view.findViewById(R.id.Dialog_txt_txt_NameDaneshkade) as AutoCompleteTextView
        val reshte = view.findViewById(R.id.signUp_IdStudent) as MaterialEditText
        val itemsSubject = resources.getStringArray(R.array.signUpDaneshkadeList)
        val adapter = ArrayAdapter(this, R.layout.item_string_array, itemsSubject)
        autoComp.setAdapter(adapter)

        alertDialog.setCustomView(view)
        alertDialog.showCancelButton(false)
        alertDialog.confirmText = "قبول"
        alertDialog.setConfirmClickListener {

            SignUp_txt_NameDaneshkade.text = autoComp.text
            SignUp_txt_NameReshte.text = reshte.text

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

    override fun onDestroy() {
        btn_signUp.dispose()
        super.onDestroy()
    }

    private fun initTextInputs() {

        val autoCompleteYear = SignUp_parent_Year.editText as? AutoCompleteTextView
        val autoCompleteSubject = SignUp_parent_NameDaneshkade.editText as? AutoCompleteTextView
        val autoCompleteChild = SignUp_parent_NameReshte.editText as? AutoCompleteTextView

        val itemYear = resources.getStringArray(R.array.signUpDYearList)
        val adapterYear = ArrayAdapter(this, R.layout.item_string_array, itemYear)

        val itemsSubject = resources.getStringArray(R.array.signUpDaneshkadeList)
        val adapter = ArrayAdapter(this, R.layout.item_string_array, itemsSubject)

        autoCompleteYear?.setAdapter(adapterYear)

        autoCompleteSubject?.setAdapter(adapter)
        autoCompleteSubject?.setOnItemClickListener { _, _, _, _ ->
            when (autoCompleteSubject.text.toString()) {
                "دانشکده فنی مهندسی" -> {
                    val itemsChild = resources.getStringArray(R.array.signUpReshteFaniMohandesi)
                    val adapterChild =
                        ArrayAdapter(this, R.layout.item_string_array, itemsChild)
                    autoCompleteChild?.setAdapter(adapterChild)
                }
                "دانشکده منابع طبیعی و علوم زمین" -> {
                    val itemsChild =
                        resources.getStringArray(R.array.signUpReshteOloomTabiiiOlomZamin)
                    val adapterChild =
                        ArrayAdapter(this, R.layout.item_string_array, itemsChild)
                    autoCompleteChild?.setAdapter(adapterChild)
                }
                "دانشکده علوم ریاضی" -> {
                    val itemsChild = resources.getStringArray(R.array.signUpReshteOloomRiazi)
                    val adapterChild =
                        ArrayAdapter(this, R.layout.item_string_array, itemsChild)
                    autoCompleteChild?.setAdapter(adapterChild)
                }
                "دانشکده علوم پایه" -> {
                    val itemsChild = resources.getStringArray(R.array.signUpReshteOloomPaye)
                    val adapterChild =
                        ArrayAdapter(this, R.layout.item_string_array, itemsChild)
                    autoCompleteChild?.setAdapter(adapterChild)
                }
                "دانشکده دامپزشکی" -> {
                    val itemsChild = resources.getStringArray(R.array.signUpReshteDampezeshki)
                    val adapterChild =
                        ArrayAdapter(this, R.layout.item_string_array, itemsChild)
                    autoCompleteChild?.setAdapter(adapterChild)
                }
                "دانشکده کشاورزی" -> {
                    val itemsChild = resources.getStringArray(R.array.signUpReshteKeshavarzi)
                    val adapterChild =
                        ArrayAdapter(this, R.layout.item_string_array, itemsChild)
                    autoCompleteChild?.setAdapter(adapterChild)
                }
                "دانشکده ادبیات و علوم انسانی" -> {
                    val itemsChild = resources.getStringArray(R.array.signUpReshteAdabiat)
                    val adapterChild =
                        ArrayAdapter(this, R.layout.item_string_array, itemsChild)
                    autoCompleteChild?.setAdapter(adapterChild)
                }
                "دانشکده هنر فارسان" -> {
                    val itemsChild = resources.getStringArray(R.array.signUpReshteHonarFarsan)
                    val adapterChild =
                        ArrayAdapter(this, R.layout.item_string_array, itemsChild)
                    autoCompleteChild?.setAdapter(adapterChild)
                }
                "دانشکده فنی مهندسی بروجن" -> {
                    val itemsChild = resources.getStringArray(R.array.signUpReshteFaniBorojen)
                    val adapterChild =
                        ArrayAdapter(this, R.layout.item_string_array, itemsChild)
                    autoCompleteChild?.setAdapter(adapterChild)
                }
                else -> {
                    Toast.makeText(this, "خطای ناشناخته رخ داده است", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}
