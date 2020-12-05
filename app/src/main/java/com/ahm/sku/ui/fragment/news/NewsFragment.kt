package com.ahm.sku.ui.fragment.news

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahm.sku.R
import com.ahm.sku.androidWrapper.G
import com.ahm.sku.model.local.chatData.ChatData
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.android.ext.android.inject


class NewsFragment : Fragment() {

    private val glide: RequestManager by inject()
    private var badgeNum: Int = 0
    private lateinit var adapter: NewsAdapter
    private val newsViewModelFactory: NewsViewModelFactory by inject()
    private lateinit var newsViewModel: NewsViewModel

    // Main Thread =>
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        G.whereTo = -1

        clickListeners()
        initRecyclerScroll()
        initBadge()

        Handler().postDelayed(150) {
            initChats()

        }

    }

    private fun clickListeners() {

        goToFirstOfList.setOnClickListener {

            txt_channel.visibility = View.VISIBLE
            recycler_chat.scrollToPosition(0)
            goToFirstOfList.hide()

        }

    }

    private fun initBadge() {

        val nav = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view_bottomnav)
        val badge = nav.getBadge(R.id.navigation_news)?.number
        if (badge != null) {
            badgeNum = badge
        }
        nav.removeBadge(R.id.navigation_news)

    }

    private fun initRecyclerScroll() {

        recycler_chat.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // add new data fab =>
//                if (dy > 0 && txt_channel.visibility == View.INVISIBLE) {
//
//                    txt_channel.animate().alpha(1.0f).setDuration(500).start()
//                    txt_channel.visibility = View.VISIBLE
//
//                } else if (dy < 0 && txt_channel.visibility == View.VISIBLE) {
//
//                    txt_channel.animate().alpha(0.0f).setDuration(750).start()
//                    txt_channel.visibility = View.INVISIBLE
//
//                }

//                Log.v("test" ,  dy.toString())
//                recyclerView.layoutManager.findFirstVisibleItemPosition()


                val position = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if(position == 0 )
                    goToFirstOfList.hide()
                else
                    goToFirstOfList.show()
//
//
//
//                if(dy < 0) {
//                    goToFirstOfList.show()
////                    goToFirstOfList.visibility = View.VISIBLE
//                } else {
//                    goToFirstOfList.hide()
////                    goToFirstOfList.visibility = View.INVISIBLE
//                }

                // add new data fab =>
//                if (dy >= 0) {
//
//
//
////                    goToFirstOfList.hide()
////                    goToFirstOfList.visibility = View.INVISIBLE
//                } else {
//                    goToFirstOfList.show()
//                    goToFirstOfList.visibility = View.VISIBLE
//                }


            }

        })

    }

    private fun initChats() {

        recycler_chat.visibility = View.INVISIBLE
        val data = newsViewModel.chatData.observe(viewLifecycleOwner, Observer {

            adapter =
                NewsAdapter { bitmap , image ->
                    showFullScreenImage(bitmap , image)

//                    Toast.makeText(context, "با دو انگشت، عکس را زوم کنید", Toast.LENGTH_SHORT)
//                        .show()

                }

            val dd = LinearLayoutManager(context)
            dd.reverseLayout = true
            recycler_chat.layoutManager = dd

            recycler_chat.adapter = adapter

            if (badgeNum > 0) {

                val newData = ChatData(
                    id = 2000,
                    imageUrl = "null",
                    isImageMode = false,
                    isImageTextMode = false,
                    isNewDataExistedMode = true,
                    isTextMode = false,
                    text = "null",
                    timeInMillies = 2000L,
                    timePersian = "null"
                )

                val arr = arrayListOf<ChatData>()
                val count = it.count()

                it.forEachIndexed { index, chatData ->
                    if (index == badgeNum)
                        arr.add(newData)
                    arr.add(chatData)
                }

                adapter.setList(arr, badgeNum)

                recycler_chat.scrollToPosition(badgeNum - 1)

            } else {

                adapter.setList(it, 0)

            }


            adapter.notifyDataSetChanged()
            recycler_chat.scheduleLayoutAnimation()

            recycler_chat.visibility = View.VISIBLE
            setFadeAnimation(recycler_chat)

            return@Observer
        })


    }


    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 800
        view.startAnimation(anim)
    }


    private fun displayPhoto(imageUri: Bitmap , imageView:ImageView ) {

        StfalconImageViewer.Builder(context, listOf(imageUri)) { view, image ->
            Glide.with(this).load(image).into(view)
        }
            .withTransitionFrom(imageView)
            .show()

    }

    private fun showFullScreenImage(data: Bitmap , imageView:ImageView) {

        displayPhoto(data , imageView)

//        G.bitmap = data
//        val intent = Intent(context, FullScreenActivity::class.java)
//        startActivity(intent)


//        val nagDialog = Dialog(requireContext())
//        nagDialog.setCancelable(true)
//        val view = LayoutInflater.from(context).inflate(R.layout.dialog_full_screen_image, null)
//        val image: ZoomInImageView = view.findViewById(R.id.img_show_full_screen) as ZoomInImageView
//        image.setImageDrawable(data)
//        nagDialog.setContentView(view)
//        nagDialog.show()


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

    // create viewModel and viewModelFactory =>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newsViewModel = ViewModelProviders.of(this, newsViewModelFactory)
            .get(NewsViewModel::class.java)


        return inflater.inflate(R.layout.fragment_news, container, false)
    }

}


/*

btn_AddNewTabrikText_Add.setOnClickListener {

    if( NetWorkChecker(requireContext()).checkNetwork && ParseUser.getCurrentUser() != null ) {

        val user = ParseUser.getCurrentUser()
        val nameWriter = user.getString(SubscriberRepository.NAME)
        val urlPic = user.getParseFile(SubscriberRepository.PROFILE_PIC)?.url
        val typeTabrik = txtAddNewData_TypeTabrik.text.toString()
        val relation = txtAddNewData_Relation.text.toString()
        val text = txtAddNewData_Text.text.toString()

        //add data =>
        val data = ParseObject("TextTabrik")
        data.add("dateOfWritten", date)
        data.add("nameWriter", nameWriter)
        data.add("likes", 0)
        data.add("typeTabrik", typeTabrik)
        data.add("nesbatType", relation)
        data.add("text", text)
        data.add("profilePicURL", urlPic)
        data.add("dateWrittenInMillies", dateInMillies)
        data.saveInBackground(SaveCallback {

            if(it == null) {
                Toast.makeText(context, "با موفقیت انجام شد", Toast.LENGTH_SHORT).show()
                G.forceUpdate = true
                requireActivity().onBackPressed()
            }
            else
                Toast.makeText(context, "مشکل در ارسال اطلاعات", Toast.LENGTH_SHORT).show()

        })



    } else
        Toast.makeText(context, "لطفا ابتدا اینترنت را متصل کنید", Toast.LENGTH_SHORT).show()

}

*/


