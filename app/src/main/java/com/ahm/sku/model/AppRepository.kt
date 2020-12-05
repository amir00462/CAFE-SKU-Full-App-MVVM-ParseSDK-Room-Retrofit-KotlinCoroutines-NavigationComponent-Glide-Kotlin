package com.ahm.sku.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.ahm.sku.androidWrapper.G
import com.ahm.sku.model.local.chatData.ChatData
import com.ahm.sku.model.local.chatData.ChatDataDAO
import com.ahm.sku.model.local.homeData.HomeData
import com.ahm.sku.model.local.homeData.HomeDataDAO
import com.ahm.sku.model.local.toolsList.ToolsList
import com.ahm.sku.model.local.toolsList.ToolsListDAO
import com.ahm.sku.model.net.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository(
    private val chatDataDAO: ChatDataDAO,
    private val homeDataDAO: HomeDataDAO,
    private val apiService: ApiService,
    private val toolsListDAO: ToolsListDAO
) {

    companion object {



        // Req Alarm =>
        const val TWELVE_HOURS_MILLISECONDS = 43200000L
        const val FIVE_DAYS_MILLIES = 432000000L
        const val REQ_ALARM = 2000
        const val REQ_NOTIFICATION = 2001
        const val REQ_PENDING_INTENT = 2002

        // maybe if it is not working to receiving data for images =>
        const val PENDING_SLIDER_URL = "https://www.kafe-it.ir/CafeSku/slider/"
        const val PENDING_SLIDER_NUMBER = "6"
        const val PENDING_UNI_URL = "https://www.kafe-it.ir/CafeSku/allPics/"
        const val PENDING_UNI_NUMBER = "102"

        // for mehman =>
        const val MEHMAN_USER = "mehman"

        // father name for update data from net =>
        const val FATHER = "father"

        // permission =>
        const val STORAGE_PERMISSION_CODE = 10000

        // group Tools =>
        const val ID_FORM = 3000
        const val ID_DAFTARCHE_TELEPHONE = 2000
        const val ID_CHART_ENTEKHAB_VAHED = 1000
        // 1000 -> فرم و آیین نامه
        // 2000 -> دفترچه تلفن
        // 3000 -> چارت انتخاب واحد

        // pictures =>
        const val CODE_SLIDER_PARENT_URL = "sliderParent"
        const val CODE_SLIDER_PARENT_NUMBER = "sliderNumber"
        const val CODE_UNI_IMAGE_PARENT_URL = "uniImagesParent"
        const val CODE_UNI_IMAGE_PARENT_NUMBER = "uniImagesNumber"

        // my own site parent address =>
        const val PARENT_DATA = "http://daneshgoocenter.ir/uploads/"
        const val PARENT_DATA_PENDING = "https://www.kafe-it.ir/CafeSku/data/"

        // Group Ids =>
        // 1 => اتوماسیون دانشجویی سس"
        // 2 => سایت و سامانه ها انلاین
        // 3 => دانشکده ها
        // 4 => اماکن اداری داشگاه
        // 5 => مکان یابی کلاس های درس
        // 6 => خوابگاه ها
        // 7 => سلف و تغذیه
        // 8 => تفریحی دانشگاه
        // 9 => تیم های ورزشی دانشگاه
        // 10 => کارگاه ها و آزمایشگاه ها

        // full image =>
        const val KEY_FULL_SCREEN_IMAGE = "fullScreen"


        // for tools page =>
        const val ID_DARYAFT_VAM = -10
        const val ID_FARAYAND_MEHMANI = -11
        const val ID_FARAYAND_ENTEGHALI = -12

        // for Login and SignUp =>
        const val NAME = "name"
        const val NUMBER = "number"
        const val DANESHKADE = "daneshkade"
        const val RESHTE = "reshte"
        const val VOROODI = "voroodi"

        // for checking first use and Updating Data =>
        const val FIRST_USE = "firstUse"
        const val UPDATE_DATA_DELAY_TIME = "updateJsonFiles"
        const val UPDATE_DATA_DELAY_TIME_CHAT = "updateJsonFilesChat"
        const val UPDATE_DATA_DELAY_TIME_PARENT = "updateJsonFilesParent"

        var PENDING_REFRESH_DATA_NEWS: Long = 7200000L     // 2 Hours
        var PENDING_REFRESH_DATA: Long = 43200000L     // 12 Hours
        var PENDING_REFRESH_DATA_PARENT: Long = 1209600000L     // 2 Weeks

        // for fragments transactions =>
        const val CARD_ID = "card_id"

        // menu item News Fragment =>
        const val NEWS_MENU_ITEM = 1

    }


    // Local => =>

    // HomeData Local =>
    val homeDataLocal = homeDataDAO.getAllHomeData()
    suspend fun insertHomeData(homeData: HomeData): Long {
        return homeDataDAO.insertHomeData(homeData)
    }

    suspend fun insertAllHomeData(homeDatas: List<HomeData>) {
        homeDataDAO.insertAllHomeData(homeDatas)
    }

    suspend fun updateHomeData(homeData: HomeData): Int {
        return homeDataDAO.updateHomeData(homeData)
    }

    suspend fun updateAllHomeData(homeDatas: List<HomeData>) {
        homeDataDAO.updateAllHomeData(homeDatas)
    }

    suspend fun deleteAllHomeData(): Int {
        return homeDataDAO.deleteAllHomeData()
    }

    fun getAllHomeDataByGroupId(id: Int): LiveData<List<HomeData>> {
        return homeDataDAO.getAllHomeDataByGroupId(id)
    }

    fun getHomeDataByCardId(id: Int): LiveData<HomeData> {
        return homeDataDAO.getHomeDataByCardId(id)
    }

    // chat data =>
    val chatData = chatDataDAO.getAllChatDataByTime()
    val chatDataNumber = chatDataDAO.getRowCountChatData()
    suspend fun insertChatData(chatData: ChatData): Long {
        return chatDataDAO.insertChatData(chatData)
    }

    suspend fun insertAllChatData(chatDatas: List<ChatData>) {
        chatDataDAO.insertAllChatData(chatDatas)
    }

    suspend fun deleteAllChatData(): Int {
        return chatDataDAO.deleteAllChatData()
    }

    // tools List =>
    suspend fun insertAllToolsList(toolsData: List<ToolsList>) {
        toolsListDAO.insertAllToolsList(toolsData)
    }

    suspend fun deleteAllToolsList(): Int {
        return toolsListDAO.deleteAllToolsList()
    }

    fun getAllToolsListTelephone(): LiveData<List<ToolsList>> {
        return toolsListDAO.getAllToolsListTelephone()
    }

    fun getAllToolsListForm(): LiveData<List<ToolsList>> {
        return toolsListDAO.getAllToolsListForm()
    }

    fun getAllToolsListChart(): LiveData<List<ToolsList>> {
        return toolsListDAO.getAllToolsListChart()
    }

    // Server => =>

    // homeData =>
//    suspend fun getHomeDataNet(father :String) :LiveData<List<HomeData>> {
//        return apiService.getApi(father).getHomeData()
//    }
//
//    // chatData =>
//    suspend fun getChatDataNet(father :String) :LiveData<List<ChatData>> {
//        return apiService.getApi(father).getChatData()
//    }
//
//    // toolsList =>
//    suspend fun getToolsListNet(father :String) :LiveData<List<ToolsList>> {
//        return apiService.getApi(father).getToolsList()
//    }
//
//    // uni Pics =>
//    suspend fun getUniPicsNet(father :String) :LiveData<List<String>> {
//        return apiService.getApi(father).getUniPics()
//    }
//
//    // get father net =>
//    suspend fun getFatherNet() :LiveData<String> {
//        return apiService.getParentApi().getParentData()
//    }


    // Net =>

    fun getHomeDataNet(father: String) =
        apiService
            .getApi(father)
            .getHomeData()
//            .enqueue(object :Callback<List<HomeData>> {
//                override fun onFailure(call: Call<List<HomeData>>, t: Throwable) {
//                    G.errorTypeNet[0] = "-1"
//                }
//
//                override fun onResponse(call: Call<List<HomeData>>, response: Response<List<HomeData>>) {
//
//                    val data = response.body()
//                    if(data != null) {
//
//                        G.errorTypeNet[0] = "0"
//                        G.HomeDataReceived.addAll(data)
//
//                    }
//
//
//                }
//
//            })


    // chatData =>
    fun getChatDataNet(father: String) =
        apiService
            .getApi(father)
            .getChatData()
//            .enqueue(object :Callback<List<ChatData>> {
//                override fun onFailure(call: Call<List<ChatData>>, t: Throwable) {
//                    G.errorTypeNet[1] = "-1"
//                }
//
//                override fun onResponse(call: Call<List<ChatData>>, response: Response<List<ChatData>>) {
//                    val data = response.body()
//                    if(data != null) {
//                        G.errorTypeNet[1] = "0"
//                        G.ChatDataReceived.addAll(data)
//
//                    }
//                }
//
//
//            })



    // toolsList =>
    fun getToolsListNet(father: String) =
        apiService
            .getApi(father)
            .getToolsList()
//            .enqueue(object :Callback<List<ToolsList>> {
//                override fun onFailure(call: Call<List<ToolsList>>, t: Throwable) {
//                    G.errorTypeNet[2] = "-1"
//                }
//
//                override fun onResponse(call: Call<List<ToolsList>>, response: Response<List<ToolsList>>) {
//                    val data = response.body()
//                    if(data != null) {
//                        G.errorTypeNet[2] = "0"
//                        G.ToolsListReceived.addAll(data)
//
//                    }
//                }
//
//            })

    // uni Pics =>
    fun getUniPicsNet(father: String) =
        apiService
            .getApi(father)
            .getUniPics()
//            .enqueue(object :Callback<List<String>>{
//                override fun onFailure(call: Call<List<String>>, t: Throwable) {
//                    G.errorTypeNet[3] = "-1"
//                }
//
//                override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
//                    val data = response.body()
//                    if(data != null) {
//                        G.errorTypeNet[3] = "0"
//                        G.UniPicsReceived.addAll(data)
//
//                    }
//                }
//
//            })


    // get father net =>
    fun getFatherNet() =
        apiService
            .getParentApi()
            .getParentData()
//            .enqueue(object :Callback<String>{
//                override fun onFailure(call: Call<String>, t: Throwable) {
//                    G.errorTypeNet[4] = "-1"
//                }
//
//                override fun onResponse(call: Call<String>, response: Response<String>) {
//                    val data = response.body()
//                    if(data != null) {
//                        G.errorTypeNet[4] = "0"
//                        G.FatherReceived = data
//                    }
//                }
//
//
//            })



}