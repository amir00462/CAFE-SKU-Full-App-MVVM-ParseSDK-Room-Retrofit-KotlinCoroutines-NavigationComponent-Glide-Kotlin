package com.ahm.sku.ui.fragment.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahm.sku.model.AppRepository
import com.ahm.sku.model.local.chatData.ChatData
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: AppRepository) : ViewModel() {

    // Chat Data =>
    val chatData = repository.chatData

}
