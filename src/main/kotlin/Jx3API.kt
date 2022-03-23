package ray.mintcat.jx3

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import ray.mintcat.jx3.datas.*
import java.text.SimpleDateFormat
import java.util.*

object Jx3API {

    val json = Json {
        coerceInputValues = true
    }

    fun httpGet(url: String): String? {
        return try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .addHeader("x-forwarded-for", Utils.getRandomIp())
                .build()
            val response = client.newCall(request).execute()
            response.body?.string()
        } catch (e: Exception) {
            "请求异常: ${e.message} "
        }
    }

    fun httpPost(url: String, body: RequestBody): String? {
        return try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .post(body)
                .addHeader("x-forwarded-for", Utils.getRandomIp())
                .build()
            val response = client.newCall(request).execute()
            response.body?.string()
        } catch (e: Exception) {
            "请求异常: ${e.message} "
        }
    }

    fun httpGetBaiDuTieBa(url: String): String? {
        return try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .addHeader("Cookie","BIDUPSID=29D2423BB73372BFDF26478CDAB8538A; PSTM=1628072930; __yjs_duid=1_11b8f1c411b6ad1841562b56504bc0501628072951168; Hm_lvt_7d6994d7e4201dd18472dd1ef27e6217=1643467190; BAIDUID=79451E430ECF125F0A89FDD742A23F15:FG=1; MCITY=-%3A; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; BDUSS=dQeDdxWTlWanJvN0xnOXJuNHRMdDBqOXAzTzJsQ2FWNG9CMmhNck9LR2VGbHBpRVFBQUFBJCQAAAAAAAAAAAEAAAD2zSPTsaG6yb~JwNayu7zTzMcAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAJ6JMmKeiTJial; BDUSS_BFESS=dQeDdxWTlWanJvN0xnOXJuNHRMdDBqOXAzTzJsQ2FWNG9CMmhNck9LR2VGbHBpRVFBQUFBJCQAAAAAAAAAAAEAAAD2zSPTsaG6yb~JwNayu7zTzMcAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAJ6JMmKeiTJial; STOKEN=0d7b446aaeb6d1669282a56d2bf0724dc893822a97e83b3d230d45a2ce72107e; Hm_lvt_287705c8d9e2073d13275b18dbd746dc=1645681348,1645965467,1647134234,1647777914; BAIDUID_BFESS=79451E430ECF125F0A89FDD742A23F15:FG=1; BAIDU_WISE_UID=wapp_1648017183476_647; RT=\"sl=0&ss=l136rxix&tt=0&bcn=https%3A%2F%2Ffclog.baidu.com%2Flog%2Fweirwood%3Ftype%3Dperf&z=1&dm=baidu.com&si=m5tesnou8ad&ul=3xkj&hd=3xqt\"; wise_device=0; Hm_lvt_98b9d8c2fd6608d564bf2ac2ae642948=1648009842,1648009897,1648016127,1648024869; Hm_lpvt_98b9d8c2fd6608d564bf2ac2ae642948=1648024869; USER_JUMP=-1; ab_sr=1.0.1_NmM3YzMwYmU0NjhjZTI5OGI3NmEyY2NiY2ZiOWI3OGY0NTdjNGRmYzIzYTM1MjAyOTdjYmIwMDViY2IyMTAxZWM3ZGU4MGM3OWEwODIyYTIzZTFkOGFjNDVjYWI4ZjY4YjlhMzMyZjdmOGVkOTBjZWVhODU2NTdkZTBhOTk5M2YxZjMzNzhmZjJkOTJlODNkZjA2ZTQ0ODdlMzljNzE3NDk5NGVkMWZhZjZkMDc0YmNiMjlmMmE1ZTc2MTVkYjhi; st_key_id=17; st_data=de3b0ad776d3b68cf1d72c9812f025d369f040c32ba9c06c7a7a0d75d9326dabec9d288cc47884b11a2e5d8e45cab8d111d35f1411153c58043b16d435ae5a99344ebe072fc5ef33cee4d791e27f2a26bf1107e99f9fdcc57d7fcee991f8a57e; st_sign=35567774; H_PS_PSSID=35839_35106_31660_34813_36086_34584_36142_36122_36032_36065_35801_35955_26350_36115_22160_36061; delPer=0; PSINO=1; BA_HECTOR=2g0kakag8h018584eg1h3lnug0q")
            .build()
            val response = client.newCall(request).execute()
            response.body?.string()
        } catch (e: Exception) {
            "请求异常: ${e.message} "
        }
    }

    fun getCheck(): CheckData? {
        val api = httpGet("https://jx3api.com/app/check") ?: return null
        return json.decodeFromString<CheckData>(api)
    }

    fun getDaily(): DailyData? {
        //https://jx3api.com/app/daily
        val api = httpGet("https://jx3api.com/app/daily") ?: return null
        return json.decodeFromString<DailyData>(api)
    }

    fun getGold(server: String): GoldData? {
        val api = httpGet("https://www.jx3api.com/app/demon?server=${server}") ?: return null
        return json.decodeFromString<GoldData>(api)
    }

    fun getMacro(info: String): MacroData? {
        val api =
            httpGet("https://cms.jx3box.com/api/cms/posts?type=macro&per=18&page=1&search=${info}&order=update&client=std")
                ?: return null
        return try {
            json.decodeFromString<MacroData>(api)
        } catch (e: Exception) {
            MacroData(0, "获取失败", null)
        }
    }

    fun getTime(): String {
        val calendar = Calendar.getInstance(Locale.CHINA);
        val time = calendar.time;
        val data = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return data.format(time)
    }

    fun getSao(): SaoData? {
        val api = httpGet("https://jx3api.com/app/random") ?: return null
        return json.decodeFromString<SaoData>(api)
    }

    fun getPriceIDData(item: String): PriceData? {
        val body = FormBody.Builder()
            .add("name", item)
            .build()
        val api = httpPost("https://www.j3price.top:8088/black-api/api/outward", body) ?: return null
        return json.decodeFromString<PriceData>(api)
    }

    fun getPriceInfo(item: String): PriceInfo? {
        return getPriceIDData(item)?.data?.get(0)
    }

    fun getPrice(regionId: Int, item: String): Price? {
        val id = getPriceInfo(item)!!.id
        val body = FormBody.Builder()
            .add("regionId", regionId.toString())
            .add("outwardId", id.toString())
            .build()
        val post =
            httpPost("https://www.j3price.top:8088/black-api/api/common/search/index/prices", body)
                ?: return null
        val json = Json {
            ignoreUnknownKeys = true
        }
        return json.decodeFromString<Price>(post)
    }

    fun getItemPrice(item: String, region: String): MutableList<PriceItemStack> {
        val items = mutableListOf<PriceItemStack>()
        if (getPrice(getRegionID(region), item)?.data?.prices != null || getPrice(getRegionID(region), item)?.data?.prices?.isNotEmpty() == true
        ) {
            val itemss = getPrice(getRegionID(region), item)?.data?.prices!!
            items.addAll(itemss)
        }
        return items
    }


    fun getRegionID(string: String): Int {
        return when (string) {
            "双梦", "华乾", "唯满侠", "姨妈", "六合一", "电八", "剑胆金榜", "电点" -> 1
            "念破", "纵月", "五鸢", "雪絮", "双剑", "双一" -> 2
            "长安", "龙虎", "蝶恋花", "电一" -> 3
            "飞龙在天", "双二" -> 4
            "青梅煮酒", "双四" -> 5
            else -> -1
        }
    }

}