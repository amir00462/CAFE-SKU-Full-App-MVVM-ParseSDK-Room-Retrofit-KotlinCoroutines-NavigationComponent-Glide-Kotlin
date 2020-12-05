package com.ahm.sku.ui.fragment.tools

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahm.sku.R
import com.ahm.sku.androidWrapper.G
import com.ahm.sku.model.AppRepository
import com.ahm.sku.ui.activity.homeDetailActivity.HomeDetailActivity
import com.ahm.sku.ui.activity.toolsList.ToolsListActivity
import kotlinx.android.synthetic.main.fragment_tools.*
import org.koin.android.ext.android.inject

class ToolsFragment : Fragment() {
    private val toolsViewModelFactory: ToolsViewModelFactory by inject()
    private lateinit var toolsViewModel: ToolsViewModel

    // Main Thread =>
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        G.whereTo = -1

        onClickListeners()

    }

    private fun onClickListeners() {

        tools_daryaft_vam.setOnClickListener {
            val intent = Intent(context , HomeDetailActivity::class.java)
            intent.putExtra(AppRepository.CARD_ID , AppRepository.ID_DARYAFT_VAM.toString())
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }

        tools_mehmani.setOnClickListener {
            val intent = Intent(context , HomeDetailActivity::class.java)
            intent.putExtra(AppRepository.CARD_ID , AppRepository.ID_FARAYAND_MEHMANI.toString())
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }

        tools_enteghali.setOnClickListener {
            val intent = Intent(context , HomeDetailActivity::class.java)
            intent.putExtra(AppRepository.CARD_ID , AppRepository.ID_FARAYAND_ENTEGHALI.toString())
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }




        tools_chart_entekhab_vahed.setOnClickListener {
            val intent = Intent(context , ToolsListActivity::class.java)
            intent.putExtra(AppRepository.CARD_ID , AppRepository.ID_CHART_ENTEKHAB_VAHED.toString())
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }

        tools_daftarche_telephone.setOnClickListener {
            val intent = Intent(context , ToolsListActivity::class.java)
            intent.putExtra(AppRepository.CARD_ID , AppRepository.ID_DAFTARCHE_TELEPHONE.toString())
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

        }

        tools_form_aiin_name.setOnClickListener {
            val intent = Intent(context , ToolsListActivity::class.java)
            intent.putExtra(AppRepository.CARD_ID , AppRepository.ID_FORM.toString())
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }

    }

    // create viewModel and viewModelFactory =>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        toolsViewModel = ViewModelProviders.of(this, toolsViewModelFactory)
            .get(ToolsViewModel::class.java)


        return inflater.inflate(R.layout.fragment_tools, container, false)
    }

}
