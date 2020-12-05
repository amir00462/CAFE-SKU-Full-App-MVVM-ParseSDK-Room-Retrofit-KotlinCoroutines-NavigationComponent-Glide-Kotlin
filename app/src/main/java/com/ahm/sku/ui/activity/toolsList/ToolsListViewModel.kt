package com.ahm.sku.ui.activity.toolsList

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahm.sku.model.AppRepository
import com.ahm.sku.model.local.chatData.ChatData
import com.ahm.sku.model.local.homeData.HomeData
import com.ahm.sku.model.local.toolsList.ToolsList
import com.ahm.sku.ui.activity.homeDetailActivity.HomeDetailViewModel
import com.ahm.sku.ui.activity.homeDetailActivity.HomeDetailViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ToolsListViewModel(private val repository: AppRepository) : ViewModel() {

    fun getAllToolsListChart()  =
        repository.getAllToolsListChart()

    fun getAllToolsListForm() =
        repository.getAllToolsListForm()

    fun getAllToolsListTelephone() =
        repository.getAllToolsListTelephone()

}
