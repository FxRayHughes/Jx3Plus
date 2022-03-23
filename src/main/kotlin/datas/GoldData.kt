package ray.mintcat.jx3.datas

import kotlinx.serialization.Serializable

@Serializable
data class GoldData(
    var code: Int = 0,
    var msg: String? = "null",
    var data: MutableList<GoldDataInfo> = mutableListOf(),
    var time: Long = 0
)

@Serializable
data class GoldDataInfo(
    var `5173`: String,
    var `7881`: String,
    var server: String,
    var wanbaolou: String,
    var tieba: String,
    var dd373: String,
    var uu898: String,
    var time: Long
) {
    fun getMean(): Double {
        return (tieba.toDouble() + wanbaolou.toDouble()) / 2
    }
}