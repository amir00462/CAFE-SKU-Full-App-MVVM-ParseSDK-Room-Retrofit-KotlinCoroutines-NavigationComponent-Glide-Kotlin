package com.ahm.sku.di

import com.ahm.sku.model.AppRepository
import com.ahm.sku.model.local.AppDataBase
import com.ahm.sku.model.net.ApiService
import com.ahm.sku.ui.activity.homeDetailActivity.HomeDetailViewModelFactory
import com.ahm.sku.ui.activity.toolsList.ToolsListViewModelFactory
import com.ahm.sku.ui.fragment.aboutSku.AboutSkuViewModelFactory
import com.ahm.sku.ui.fragment.home.HomeViewModelFactory
import com.ahm.sku.ui.fragment.news.NewsViewModelFactory
import com.ahm.sku.ui.fragment.profile.ProfileViewModelFactory
import com.ahm.sku.ui.fragment.tools.ToolsViewModelFactory
import com.bumptech.glide.Glide
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import kotlin.math.sin

val viewModule = module() {


    // Repository =>
    single { AppDataBase.getInstance(androidContext()).toolsListDAO }
    single { AppDataBase.getInstance(androidContext()).chatDataDAO }
    single { AppDataBase.getInstance(androidContext()).homeDataDAO }
    single { ApiService() }
    single { AppRepository(get(), get(), get() , get()) }

    // ViewModel Factory =>
    single { ToolsListViewModelFactory(get()) }
    single { HomeDetailViewModelFactory(get()) }
    single { HomeViewModelFactory(get()) }
    single { ToolsViewModelFactory(get()) }
    single { NewsViewModelFactory(get()) }
    single { ProfileViewModelFactory(get()) }
    single { AboutSkuViewModelFactory(get()) }


    // Libraries =>
    single { Glide.with(androidContext()) }

}