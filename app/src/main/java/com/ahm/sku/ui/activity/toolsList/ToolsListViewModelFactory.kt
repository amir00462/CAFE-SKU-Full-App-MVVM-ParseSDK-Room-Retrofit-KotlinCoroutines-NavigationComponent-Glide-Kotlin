package com.ahm.sku.ui.activity.toolsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahm.sku.model.AppRepository

class ToolsListViewModelFactory(private val repository: AppRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ToolsListViewModel(repository) as T
    }

}