package com.ahm.sku.ui.fragment.aboutSku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahm.sku.model.AppRepository

class AboutSkuViewModelFactory(private val repository: AppRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AppRepository::class.java)
            .newInstance(repository)
    }

}