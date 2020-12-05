package com.ahm.sku.ui.fragment.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ahm.sku.R
import com.ahm.sku.androidWrapper.G
import com.ahm.sku.model.AppRepository
import com.google.android.material.shape.CornerFamily
import com.parse.ParseUser
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject


class ProfileFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private val profileViewModelFactory: ProfileViewModelFactory by inject()
    private lateinit var profileViewModel: ProfileViewModel

    // Main Thread =>
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        G.whereTo = -1

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)


        setTheData()

    }

    private fun setTheData() {

        if(sharedPreferences.getBoolean(AppRepository.MEHMAN_USER, false)) {

            profileUserParent.visibility = View.GONE
            profileMehmanParent.visibility = View.VISIBLE


        } else {

            profileUserParent.visibility = View.VISIBLE
            profileMehmanParent.visibility = View.GONE

            val user = ParseUser.getCurrentUser()
            txt_ProfilePage_StudentId.text = user.username
            txt_ProfilePage_Number.text = user.getString(AppRepository.NUMBER)
            txt_ProfilePage_Name.text = user.getString(AppRepository.NAME)
            txt_ProfilePage_Daneshkade.text = user.getString(AppRepository.DANESHKADE)
            txt_ProfilePage_Reshte.text = user.getString(AppRepository.RESHTE)
            txt_ProfilePage_Voroodi.text = user.getString(AppRepository.VOROODI)

        }

    }

//    private fun setTheRadius() {
//        val radius = resources.getDimension(R.dimen.margin_8dp_normal)
//        headImageProfile.shapeAppearanceModel = headImageProfile.shapeAppearanceModel
//            .toBuilder()
//            .setAllCorners(CornerFamily.ROUNDED, radius)
//            .build()
//    }


    // create viewModel and viewModelFactory =>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        profileViewModel = ViewModelProviders.of(this, profileViewModelFactory)
            .get(ProfileViewModel::class.java)


        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

}
