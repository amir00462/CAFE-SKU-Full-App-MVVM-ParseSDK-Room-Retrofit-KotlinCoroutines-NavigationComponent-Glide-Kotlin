package com.ahm.sku.ui.activity.toolsList

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahm.sku.R
import com.ahm.sku.ext.BaseActivity
import com.ahm.sku.model.AppRepository
import kotlinx.android.synthetic.main.activity_tools_list.*
import org.koin.android.ext.android.inject
import java.util.*

class ToolsListActivity : BaseActivity() {

    private val toolsListViewModelFactory: ToolsListViewModelFactory by inject()
    private lateinit var toolsListViewModel: ToolsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tools_list)
        setSupportActionBar(toolbarToolsList)

        toolbarToolsList.title = "دفترچه تلفن"

        // support RTL =>
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL

        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        Objects.requireNonNull(supportActionBar)!!.setDisplayShowHomeEnabled(true)

        val data = intent.getStringExtra(AppRepository.CARD_ID)

        if (data != null)
            initData(data.toInt())

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

    private fun initData(id: Int) {

        when (id) {

            AppRepository.ID_FORM -> {

                toolbarToolsList.title = "فرم و آیین نامه ها"

                val data1 = toolsListViewModel.getAllToolsListForm()
                data1.observe(this , androidx.lifecycle.Observer {

                    val adapter = ToolsListAdapter(it , this , this)
                    recycler_tools_list.layoutManager = LinearLayoutManager(this)
                    recycler_tools_list.adapter = adapter

                    return@Observer

                })

            }

            AppRepository.ID_CHART_ENTEKHAB_VAHED -> {

                toolbarToolsList.title = "چارت انتخاب واحد"

                val data1 = toolsListViewModel.getAllToolsListChart()
                data1.observe(this , androidx.lifecycle.Observer {

                    val adapter = ToolsListAdapter(it , this , this)
                    recycler_tools_list.layoutManager = LinearLayoutManager(this)
                    recycler_tools_list.adapter = adapter

                    return@Observer

                })


            }

            AppRepository.ID_DAFTARCHE_TELEPHONE -> {

                toolbarToolsList.title = "دفترچه تلفن"

                val data1 = toolsListViewModel.getAllToolsListTelephone()
                data1.observe(this , androidx.lifecycle.Observer {

                    val adapter = ToolsListAdapter(it , this , this)
                    recycler_tools_list.adapter = adapter
                    recycler_tools_list.layoutManager = LinearLayoutManager(this)

                    return@Observer

                })

            }

        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {

            AppRepository.STORAGE_PERMISSION_CODE -> {

                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "لطفا دوباره بر روی دانلود کلیک کنید !", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this, "اجازه دانلود داده نشد !", Toast.LENGTH_SHORT).show()
                }

            }

        }


    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        toolsListViewModel = ViewModelProviders.of(this, toolsListViewModelFactory)
            .get(ToolsListViewModel::class.java)

        return super.onCreateView(name, context, attrs)
    }

}