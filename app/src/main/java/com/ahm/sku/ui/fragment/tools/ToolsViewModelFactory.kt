package com.ahm.sku.ui.fragment.tools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahm.sku.model.AppRepository
import com.ahm.sku.ui.fragment.aboutSku.AboutSkuViewModel

class ToolsViewModelFactory(private val repository: AppRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ToolsViewModel(repository) as T
    }

}