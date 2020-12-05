package com.ahm.sku.ui.fragment.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahm.sku.model.AppRepository

class NewsViewModelFactory(private val repository: AppRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(repository) as T
    }

}