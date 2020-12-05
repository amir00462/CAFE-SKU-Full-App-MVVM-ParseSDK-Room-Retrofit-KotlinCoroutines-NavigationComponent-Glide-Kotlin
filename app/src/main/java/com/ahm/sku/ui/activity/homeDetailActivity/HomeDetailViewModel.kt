package com.ahm.sku.ui.activity.homeDetailActivity

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahm.sku.model.AppRepository
import com.ahm.sku.model.local.chatData.ChatData
import com.ahm.sku.model.local.homeData.HomeData
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeDetailViewModel(private val repository: AppRepository) : ViewModel() {

    fun getHomeDataByCardId(id: Int): LiveData<HomeData> {
        return repository.getHomeDataByCardId(id)
    }


}
