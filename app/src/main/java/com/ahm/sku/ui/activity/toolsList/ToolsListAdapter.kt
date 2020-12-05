package com.ahm.sku.ui.activity.toolsList

import android.app.Activity
import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ahm.coronam.androidWrapper.NetWorkChecker
import com.ahm.sku.R
import com.ahm.sku.model.AppRepository
import com.ahm.sku.model.local.toolsList.ToolsList
import com.google.android.material.card.MaterialCardView
import org.koin.standalone.KoinComponent
import java.io.File


class ToolsListAdapter(
    private val data: List<ToolsList>,
    private val context: Context ,
    private val activity :Activity
) :
    RecyclerView.Adapter<ToolsListAdapter.CustomViewHolder>(), KoinComponent {

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parentCard: MaterialCardView =
            itemView.findViewById<MaterialCardView>(R.id.parent_tools_list)
        var toolsName: TextView = itemView.findViewById<TextView>(R.id.txt_tools_name)
        var toolsIcon: ImageView = itemView.findViewById<ImageView>(R.id.img_tools_icon)
        var toolsButton: Button = itemView.findViewById<Button>(R.id.txt_tools_button_to_do)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_tools_list, parent, false)
        )
    }

    override fun getItemCount(): Int = data.count()

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val test = data[position].name


        holder.toolsName.text = data[position].name

        when (data[position].groupId) {

            AppRepository.ID_FORM -> {
                holder.toolsIcon.setImageResource(R.drawable.ic_form)
                holder.toolsButton.text = "دریافت فرم !"
                holder.toolsButton.setOnClickListener {

                    if(NetWorkChecker(context).checkNetwork) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            if(context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_DENIED) {

                                // permission denined ! req for it ! =>
                                // get permission =>
                                activity.requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) , AppRepository.STORAGE_PERMISSION_CODE)


                            } else {

                                // download file with permission =>
                                startDownload(data[position].optionInfo , data[position].name + ".pdf")


                            }

                        } else {

                            // download file without permission =>
                            startDownload(data[position].optionInfo , data[position].name + ".pdf")

                        }

                    } else {
                        Toast.makeText(context, "ارتباط با اینترنت میسر نیست !", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            AppRepository.ID_DAFTARCHE_TELEPHONE -> {
                holder.toolsIcon.setImageResource(R.drawable.ic_number)
                holder.toolsButton.text = "تماس بگیر !"
                holder.toolsButton.setOnClickListener {
                    val number: Uri = Uri.parse("tel:" + data[position].optionInfo)
                    val intent = Intent(Intent.ACTION_DIAL, number)
                    context.startActivity(intent)
                }
            }

            AppRepository.ID_CHART_ENTEKHAB_VAHED -> {
                holder.toolsIcon.setImageResource(R.drawable.ic_form)
                holder.toolsButton.text = "دریافت چارت !"
                holder.toolsButton.setOnClickListener {
                    if(NetWorkChecker(context).checkNetwork) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            if(context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_DENIED) {

                                // permission denined ! req for it ! =>
                                // get permission =>
                                activity.requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) , AppRepository.STORAGE_PERMISSION_CODE)


                            } else {

                                // download file with permission =>
                                startDownload(data[position].optionInfo , data[position].name + ".pdf")


                            }

                        } else {

                            // download file without permission =>
                            startDownload(data[position].optionInfo , data[position].name + ".pdf")

                        }

                    } else {
                        Toast.makeText(context, "ارتباط با اینترنت میسر نیست !", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

    }

    private fun startDownload(url :String , name :String) {

        val request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle("CAFE SKU")
        request.setDescription("در حال دانلود...")
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS , name)

        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
        Toast.makeText(
            context,
            "فایل pdf بعد از دانلود در پوشه downloads موجود است !", Toast.LENGTH_SHORT).show()

        openDownloadedFile(name)

    }

    private fun openDownloadedFile(name: String) {

        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .path + File.separator.toString() +
                    name
        )
        val path = Uri.fromFile(file)
        val pdfOpenintent = Intent(Intent.ACTION_VIEW)
        pdfOpenintent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        pdfOpenintent.setDataAndType(path, "application/pdf")
        try {
            context.startActivity(pdfOpenintent)
        } catch (e: ActivityNotFoundException) {

            Toast.makeText(context, "حطا در باز کردن فایل", Toast.LENGTH_SHORT).show()
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()

        }

    }


}
