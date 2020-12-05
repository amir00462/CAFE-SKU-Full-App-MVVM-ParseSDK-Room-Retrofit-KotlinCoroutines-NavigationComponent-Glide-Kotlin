package com.ahm.sku.ui.fragment.home

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.pedant.SweetAlert.SweetAlertDialog
import com.ahm.coronam.androidWrapper.NetWorkChecker
import com.ahm.sku.R
import com.ahm.sku.androidWrapper.G
import com.ahm.sku.model.AppRepository
import com.ahm.sku.model.local.chatData.ChatData
import com.ahm.sku.model.local.homeData.HomeData
import com.ahm.sku.model.local.toolsList.ToolsList
import com.ahm.sku.receiver.AlarmReceiver
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.glide.slider.library.SliderLayout
import com.glide.slider.library.slidertypes.TextSliderView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.ParseInstallation
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private val glide: RequestOptions by inject()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var edit: SharedPreferences.Editor
    private val homeViewModelFactory: HomeViewModelFactory by inject()
    private lateinit var homeViewModel: HomeViewModel


    override fun onDestroy() {
        edit.commit()
        super.onDestroy()
    }

    private fun startNotificationService() {

        val intent = Intent(context, AlarmReceiver::class.java)

        val pending = PendingIntent.getBroadcast(
            context,
            AppRepository.REQ_ALARM,
            intent,
            0
        )


        val currentTime = System.currentTimeMillis() + AppRepository.TWELVE_HOURS_MILLISECONDS

        val alarm = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            currentTime, // every Playing // for the first time
            AppRepository.FIVE_DAYS_MILLIES,
            pending
        )

    }

    // Main Thread =>
    @SuppressLint("CommitPrefEdits")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        G.whereTo = 0

        sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
        edit = sharedPreferences.edit()

        G.tmpFather =
            sharedPreferences.getString(AppRepository.FATHER, AppRepository.PARENT_DATA_PENDING)!!


        if (sharedPreferences.getBoolean(AppRepository.FIRST_USE, true)) {
            firstUse()
        } else if (NetWorkChecker(requireContext()).checkNetwork && System.currentTimeMillis() - sharedPreferences.getLong(
                AppRepository.UPDATE_DATA_DELAY_TIME,
                0L
            ) > AppRepository.PENDING_REFRESH_DATA
        ) {
            updateAndSetDataFromNetExceptChatData()
        } else {
            initDataToShow()
        }

        if (NetWorkChecker(requireContext()).checkNetwork && !sharedPreferences.getBoolean(
                AppRepository.FIRST_USE,
                false
            ) && System.currentTimeMillis() - sharedPreferences.getLong(
                AppRepository.UPDATE_DATA_DELAY_TIME_CHAT,
                0L
            ) > AppRepository.PENDING_REFRESH_DATA_NEWS
        ) {
            updateChatData()
        }

        if (NetWorkChecker(requireContext()).checkNetwork && System.currentTimeMillis() - sharedPreferences.getLong(
                AppRepository.UPDATE_DATA_DELAY_TIME_PARENT,
                0L
            ) > AppRepository.PENDING_REFRESH_DATA_PARENT
        ) {
            updateFatherFromNet()
        }

    }

    @SuppressLint("CheckResult")
    private fun initSlider() {


//        sliderMain.stopAutoCycle()

        // from server side =>
        val list = arrayListOf<String>()
        val url = sharedPreferences.getString(
            AppRepository.CODE_SLIDER_PARENT_URL,
            AppRepository.PENDING_SLIDER_URL
        )!!
        val number = sharedPreferences.getString(
            AppRepository.CODE_SLIDER_PARENT_NUMBER,
            AppRepository.PENDING_SLIDER_NUMBER
        )!!.toInt()

        val requestOptions = RequestOptions()
        requestOptions
            .centerCrop()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .error(R.drawable.img_error)

        for (i in 0 until number) {

            val sliderView = TextSliderView(context)
            // if you want show image only / without description text use DefaultSliderView instead

            val urll = url + (i + 1).toString() + ".jpeg"

            // initialize SliderLayout
            sliderView
                .image(urll)
                .setRequestOption(requestOptions)

            sliderMain.addSlider(sliderView)
        }

        sliderMain.setDuration(3500)
        sliderMain.stopCyclingWhenTouch(false)


        sliderMain.setPresetTransformer(SliderLayout.Transformer.DepthPage)


//        val animSlide: Animation =
//            AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_from_top)
//        sliderMain.startAnimation(animSlide)
//        sliderMain.visibility = View.VISIBLE
//        sliderMain.startAutoCycle()

    }

    private fun initDataToShow() {


        // init Slider =>
        initSlider()

        // init ProductList =>
        initProductLists()


    }


    private fun initProductLists() {

        val currentActivity = requireActivity()

        val data10 = homeViewModel.getAllHomeDataByGroupId(1)
        data10.observe(viewLifecycleOwner, Observer {
            productWorkWithSess.initRecycler(it, currentActivity)
            return@Observer
        })

        val data1 = homeViewModel.getAllHomeDataByGroupId(2)
        data1.observe(viewLifecycleOwner, Observer {
            productSamaneVaSite.initRecycler(it, currentActivity)
            return@Observer
        })


        val data2 = homeViewModel.getAllHomeDataByGroupId(3)
        data2.observe(viewLifecycleOwner, Observer {
            productDaneshkade.initRecycler(it, currentActivity)
            return@Observer
        })


        val data3 = homeViewModel.getAllHomeDataByGroupId(4)
        data3.observe(viewLifecycleOwner, Observer {
            productAmakenEdari.initRecycler(it, currentActivity)
            return@Observer
        })


        val data5 = homeViewModel.getAllHomeDataByGroupId(6)
        data5.observe(viewLifecycleOwner, Observer {
            productKhabgah.initRecycler(it, currentActivity)
            return@Observer
        })


        val data6 = homeViewModel.getAllHomeDataByGroupId(7)
        data6.observe(viewLifecycleOwner, Observer {
            productSelfVaTaghziye.initRecycler(it, currentActivity)
            return@Observer
        })


        val data7 = homeViewModel.getAllHomeDataByGroupId(8)
        data7.observe(viewLifecycleOwner, Observer {
            productTafrihi.initRecycler(it, currentActivity)
            return@Observer
        })

    }

    // retriew new data =>
    private fun updateAndSetDataFromNetExceptChatData() {

        // Receive Data =>
        val home = homeViewModel.getHomeDataNet(G.tmpFather)
        val uniPics = homeViewModel.getUniPicsNet(G.tmpFather)
        val toolsList = homeViewModel.getToolsListNet(G.tmpFather)

        home.enqueue(object : Callback<List<HomeData>> {
            override fun onFailure(call: Call<List<HomeData>>, t: Throwable) {
                Toast.makeText(context, "مشکل در دریافت اطلاعات از نت !", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<List<HomeData>>,
                response: Response<List<HomeData>>
            ) {

                val data = response.body()
                if (data != null) {

                    homeViewModel.deleteAllHomeData()
                    homeViewModel.insertAllHomeData(data)

                }


            }

        })
        uniPics.enqueue(object : Callback<List<String>> {
            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Toast.makeText(context, "مشکل در دریافت اطلاعات !", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {

                val data = response.body()
                if (data != null) {

                    edit
                        .putString(AppRepository.CODE_SLIDER_PARENT_URL, data[0])

                    edit
                        .putString(AppRepository.CODE_SLIDER_PARENT_NUMBER, data[1])

                    edit
                        .putString(AppRepository.CODE_UNI_IMAGE_PARENT_URL, data[2])

                    edit
                        .putString(AppRepository.CODE_UNI_IMAGE_PARENT_NUMBER, data[3])

                    edit.apply()

                }


            }


        })
        toolsList.enqueue(object : Callback<List<ToolsList>> {
            override fun onFailure(call: Call<List<ToolsList>>, t: Throwable) {
                Toast.makeText(context, "مشکل در دریافت اطلاعات از نت !", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<List<ToolsList>>,
                response: Response<List<ToolsList>>
            ) {

                val data = response.body()

                if (data != null) {

                    homeViewModel.deleteAllToolsList()
                    homeViewModel.insertAllToolsList(data)

                }

            }


        })

        // update time updated in base data =>
        edit
            .putLong(
                AppRepository.UPDATE_DATA_DELAY_TIME,
                System.currentTimeMillis()
            )

        edit.apply()

        initDataToShow()


    }

    private fun updateFatherFromNet() {

        // Receive Data =>
        val father = homeViewModel.getFatherNet()
        father.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {

                Toast.makeText(context, "مشکل در دریافت اطلاعات از نت !", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {

                val data = response.body()

                if (data != null) {

                    if (data != AppRepository.PARENT_DATA_PENDING) {

                        G.tmpFather = data
                        edit.putString(AppRepository.FATHER, data)
                        edit.apply()

                    }

                }

            }


        })

        // update time updated in base data =>
        edit
            .putLong(
                AppRepository.UPDATE_DATA_DELAY_TIME_PARENT,
                System.currentTimeMillis()
            )

        edit.apply()

    }

    private fun updateChatData() {

        // Receive Data =>
        val chat = homeViewModel.getChatDataNet(G.tmpFather)
        chat.enqueue(object : Callback<List<ChatData>> {

            override fun onFailure(call: Call<List<ChatData>>, t: Throwable) {
                Toast.makeText(context, "مشکل در دریافت اطلاعات از نت !", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<List<ChatData>>,
                response: Response<List<ChatData>>
            ) {

                val data = response.body()

                if (data != null) {


                    val dataNew = homeViewModel.chatData.observe(viewLifecycleOwner, Observer {

                        Handler().postDelayed(100) {

                            var countDataBase: Int = 0
                            it.forEach { _ ->
                                countDataBase++
                            }

                            if (data.count() > countDataBase) {

                                // create badge of update =>
                                val nav =
                                    requireActivity().findViewById<BottomNavigationView>(R.id.nav_view_bottomnav)
                                val badge = nav.getOrCreateBadge(R.id.navigation_news)
                                badge.isVisible = true

                                badge.number = data.count() - countDataBase

                                // set to dataBase =>
                                homeViewModel.deleteAllChatData()
                                homeViewModel.insertAllChatData(data)

                            }

                        }

                    })


                }

            }


        })

        // update time updated in base data =>
        edit
            .putLong(
                AppRepository.UPDATE_DATA_DELAY_TIME_CHAT,
                System.currentTimeMillis()
            )

        edit.apply()

    }

    private fun initChatForFirstTime() {

        // Receive Data =>
        val chat = homeViewModel.getChatDataNet(G.tmpFather)
        chat.enqueue(object : Callback<List<ChatData>> {
            override fun onFailure(call: Call<List<ChatData>>, t: Throwable) {
                Toast.makeText(context, "مشکل در دریافت اطلاعات از نت !", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<List<ChatData>>,
                response: Response<List<ChatData>>
            ) {

                val data = response.body()

                if (data != null) {

                    homeViewModel.deleteAllChatData()
                    homeViewModel.insertAllChatData(data)

                    // create badge of update =>
                    val nav =
                        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view_bottomnav)
                    val badge = nav.getOrCreateBadge(R.id.navigation_news)
                    badge.isVisible = true


                }

            }


        })


        // update time updated in base data =>
        edit
            .putLong(
                AppRepository.UPDATE_DATA_DELAY_TIME_CHAT,
                System.currentTimeMillis()
            )

        edit.apply()

    }

    private fun firstUse() {

        // apply notification =>
        startNotificationService()

        // apply shared pref =>
        edit.putString(AppRepository.FATHER, AppRepository.PARENT_DATA_PENDING).apply()

        edit.putBoolean(AppRepository.FIRST_USE, false).apply()

        edit.apply()

        // show Dialogs for first use =>
        showFirstUseToorMajaziAndDialogs(1)

        // get Accept Of Permissions =>
//        getAcceptForPermissions()

        // init data from net for first time =>
        if (NetWorkChecker(requireContext()).checkNetwork) {
            updateAndSetDataFromNetExceptChatData()
            initChatForFirstTime()

            // first Use =>
            ParseInstallation.getCurrentInstallation().saveInBackground()

        }


    }

    private fun getAcceptForPermissions() {


        requestPermissions(
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            AppRepository.STORAGE_PERMISSION_CODE
        )


    }

    private fun showFirstUseToorMajaziAndDialogs(id: Int) {

        when (id) {

            1 -> {
                showDialog(
                    "صفحه خانه",
                    "اینجا میتونی تمام قسمت های مختلف دانشگاه رو ببینی! با کلیک رو هرکدومشون کلی اطلاعات درموردشون به دست میاری! از جمله عکس ، فیلم ، مکانیابی و ...",
                    2
                )
            }
            2 -> {
                showDialog(
                    "صفحه ابزار",
                    "اینجا ابزار گذاشتیم که کمکت میکنه خیلی از چیزایی که برا پیدا کرنش باید کلی وقت میزاشتی رو اینجا راحت پیدا کنی!",
                    3
                )
            }
            3 -> {
                showDialog(
                    "صفحه اخبار داغ",
                    "اصل کارو اینجا گذاشتن، روزانه سر بزن خبرای جدیدو بخون ، عکسای یادگاری دانشجوها رو ببین و از بقیه بروز تر باش ! حتی میتونی سوژه هاتو برامون بفرستی تا بزاریمش اینجا :)",
                    4
                )
            }
            4 -> {
                showDialog(
                    "دانشگاه شهرکرد",
                    "تو این قسمت کلی اطلاعات و عکس درباره دانشگاه هست که قراره کلی باهاش کیف کنی! هر عکس خاطره انگیزی اگه از دانشگاه داری برامون بفرست تا بزاریمش تو گالریمون! دقت کن که با کلیک بر روی هر عکس اونو با سایز بزرگ مشاهده کنی!",
                    5
                )
            }
            5 -> {
                showDialog(
                    "منوی کشویی",
                    "توی منوی کشویی سمت راستت کلی آدرس سایت و ابزار دیگه هست که حیفه یه نگاهی به هرکدوم نندازی !",
                    6
                )
            }
            6 -> {
                showDialog(
                    "کلام آخر",
                    "کلام آخر اینکه اگه از اپ خوشت اومد حتما اونو برا دوستات بفرست (از منو کشویی) و مطمئن باش ما از این حرکتت خیلی خوشحال میشیم. اگه دوستی رو میشناسی که جدیدالوروده که دیگه واجب میشه براش بفرستی که خیلیی به دردش میخوره! مرسی ازت :)",
                    7
                )
            }


            else -> return


        }

    }

    private fun showDialog(text: String, sub: String, parentId: Int) {

        val view = View.inflate(context, R.layout.dialog_hemayat_az_ma, null) as LinearLayout
        val text1 = view.findViewById<TextView>(R.id.txtDialog)
        text1.text = text
        val alertDialog = SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
        alertDialog.titleText = sub
        alertDialog.confirmText = "فهمیدم"
        alertDialog.showCancelButton(false)
        alertDialog.setCustomView(view)
        alertDialog.setConfirmClickListener {

            alertDialog.dismiss()

            if (parentId != 7)
                showFirstUseToorMajaziAndDialogs(parentId)


        }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    // create viewModel and viewModelFactory =>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this, homeViewModelFactory)
            .get(HomeViewModel::class.java)

        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onStop() {
        sliderMain.stopAutoCycle()
        super.onStop()
    }

}