package com.ahm.sku.ui.fragment.news

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahm.sku.R
import com.ahm.sku.databinding.ItemChatBinding
import com.ahm.sku.model.local.chatData.ChatData
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.io.ByteArrayOutputStream
import java.util.*

class NewsAdapter(
    private val clickListener: (Bitmap , ImageView) -> Unit
) : RecyclerView.Adapter<NewsAdapter.MyViewHolder>(), KoinComponent {

    private var badgeNum = 0

    private val chatData = arrayListOf<ChatData>()
    private val glide: RequestManager by inject()

    inner class MyViewHolder(private val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun clearAnimation() {
            binding.root.clearAnimation()
        }

        @SuppressLint("SetTextI18n")
        fun bind(chatData: ChatData, clickListener: (Bitmap , ImageView) -> Unit) {

            when {

                chatData.isImageMode -> {

                    binding.viewNewMessageMode.visibility = View.GONE
                    binding.viewTextImageMode.visibility = View.GONE
                    binding.viewTextMode.visibility = View.GONE
                    binding.viewImageMode.visibility = View.VISIBLE

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        binding.imgMainImageMode.clipToOutline = true
                    }

                    glide
                        .load(chatData.imageUrl)
                        .fitCenter()
                        .override(900, 900)
                        .error(R.drawable.img_error)
                        .into(binding.imgMainImageMode)

                    binding.imgMainImageMode.setOnClickListener {
                        val data = binding.imgMainImageMode.drawable.toBitmap()
                        clickListener(data , binding.imgMainImageMode)
                    }

                    binding.txtTimeImgMode.text = chatData.timePersian

                }

                chatData.isTextMode -> {

                    binding.viewNewMessageMode.visibility = View.GONE
                    binding.viewTextImageMode.visibility = View.GONE
                    binding.viewTextMode.visibility = View.VISIBLE
                    binding.viewImageMode.visibility = View.GONE

                    binding.txtTimeTextMode.text = chatData.timePersian
                    binding.txtTextTextMoe.text = chatData.text

                }

                chatData.isImageTextMode -> {

                    binding.viewNewMessageMode.visibility = View.GONE
                    binding.viewTextImageMode.visibility = View.VISIBLE
                    binding.viewTextMode.visibility = View.GONE
                    binding.viewImageMode.visibility = View.GONE

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        binding.imgMainTextImageMode.clipToOutline = true
                    }

                    glide
                        .load(chatData.imageUrl)
                        .fitCenter()
                        .override(900, 900)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .error(R.drawable.img_error)
                        .into(binding.imgMainTextImageMode)

                    binding.imgMainTextImageMode.setOnClickListener {
                        val data = binding.imgMainTextImageMode.drawable.toBitmap()
                        clickListener(data , binding.imgMainTextImageMode)
                    }

                    binding.txtTimeTextImageMode.text = chatData.timePersian

                    binding.txtTextTextImageMode.text = chatData.text

                }

                chatData.isNewDataExistedMode -> {

                    if (badgeNum > 0) {

                        binding.viewNewMessageMode.visibility = View.VISIBLE
                        binding.viewTextImageMode.visibility = View.GONE
                        binding.viewTextMode.visibility = View.GONE
                        binding.viewImageMode.visibility = View.GONE

                        binding.txtNewMessage.text = "$badgeNum پیام جدید "

                    }

                }

            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemChatBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_chat, parent, false)
        return MyViewHolder(binding)
    }


    override fun getItemCount(): Int = chatData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(chatData[position], clickListener)

        // Set the view to fade in
//        setScaleAnimation(holder.itemView)
//        setAnimation(holder.itemView, position)

    }

    fun setList(data: List<ChatData>, badgeNumReceived: Int) {

        badgeNum = badgeNumReceived

        chatData.clear()
        chatData.addAll(data)
    }


    private fun setScaleAnimation(view: View) {
        val anim = ScaleAnimation(
            0.0f,
            1.0f,
            0.0f,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        anim.duration = 500
        view.startAnimation(anim)
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500
        view.startAnimation(anim)
    }

    private var lastPosition = 0
    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val anim = AlphaAnimation(0.0f, 1.0f)
            anim.duration = 500
            viewToAnimate.startAnimation(anim)
            lastPosition = position
        }
    }



}