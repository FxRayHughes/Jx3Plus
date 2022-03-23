package ray.mintcat.jx3.datas

import kotlinx.serialization.Serializable

@Serializable
data class SaoData(
    val code: Int,
    val msg: String,
    val time: Long,
    val data: SaoInfo
)

@Serializable
data class SaoInfo(
    val text: String? = null,
    val id:Int? = 0
)
