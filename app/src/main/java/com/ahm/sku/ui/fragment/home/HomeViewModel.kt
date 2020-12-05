package com.ahm.sku.ui.fragment.home

import android.content.SharedPreferences
import androidx.core.os.postDelayed
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahm.sku.androidWrapper.G
import com.ahm.sku.model.AppRepository
import com.ahm.sku.model.local.chatData.ChatData
import com.ahm.sku.model.local.homeData.HomeData
import com.ahm.sku.model.local.toolsList.ToolsList
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.logging.Handler

class HomeViewModel(private val repository: AppRepository) : ViewModel() {

    // Home Data =>
    var isLoading = ObservableField(false)
    fun getAllHomeDataByGroupId(id: Int): LiveData<List<HomeData>> {
        return repository.getAllHomeDataByGroupId(id)
    }

    fun insertAllHomeData(homeDatas: List<HomeData>): Job = viewModelScope.launch {
        repository.insertAllHomeData(homeDatas)
    }

    fun deleteAllHomeData(): Job = viewModelScope.launch {
        val newRowId = repository.deleteAllHomeData()
    }

    // Chat Data =>
    val chatData = repository.chatData
    val chatDataNumber = repository.chatDataNumber
    fun insertChatData(data: ChatData): Job = viewModelScope.launch {
        repository.insertChatData(data)
    }

    fun insertAllChatData(data: List<ChatData>): Job = viewModelScope.launch {
        repository.insertAllChatData(data)
    }

    fun deleteAllChatData(): Job = viewModelScope.launch {
        repository.deleteAllChatData()
    }

    fun rowExistedInDataBaseNumber(): Int {
        var returnType = 0

        chatDataNumber.observeForever {iit ->

            android.os.Handler().postDelayed(1000) {
                returnType = iit
            }

        }

        return returnType
    }

    // Tools List =>
    fun insertAllToolsList(data: List<ToolsList>): Job = viewModelScope.launch {
        repository.insertAllToolsList(data)
    }

    fun deleteAllToolsList(): Job = viewModelScope.launch {
        repository.deleteAllToolsList()
    }


    // online works =>
    fun getHomeDataNet(father: String) =
        repository.getHomeDataNet(father)

    fun getChatDataNet(father: String) =
        repository.getChatDataNet(father)

    fun getToolsListNet(father: String) =
        repository.getToolsListNet(father)

    fun getUniPicsNet(father: String) =
        repository.getUniPicsNet(father)

    fun getFatherNet() =
        repository.getFatherNet()


//    val homeDataNet by lazyDeferred {
//        repository.getHomeDataNet(G.tmpFather)
//    }
//    val chatDataNet by lazyDeferred {
//        repository.getChatDataNet(G.tmpFather)
//    }
//    val toolsListNet by lazyDeferred {
//        repository.getToolsListNet(G.tmpFather)
//    }
//    val uniPicsNet by lazyDeferred {
//        repository.getUniPicsNet(G.tmpFather)
//    }
//
//    val fatherNet by lazyDeferred {
//        repository.getFatherNet()
//    }


//    fun getHomeDataFromNet(father:String)  {
//        repository.getHomeDataNet(father)
//    }
//    fun getChatDataFromNet(father:String)  {
//        repository.getChatDataNet(father)
//    }
//    fun getToolsListFromNet(father:String)  {
//        repository.getToolsListNet(father)
//    }
//    fun getUniPicsFromNet(father:String)  {
//        repository.getUniPicsNet(father)
//    }


}
