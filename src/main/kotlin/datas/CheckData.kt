package ray.mintcat.jx3.datas

import kotlinx.serialization.Serializable

@Serializable
data class CheckData(
    val code: Int,
    val msg: String,
    val time: Long,
    val data: List<ServerData>
)

@Serializable
data class ServerData(
    val zone: String? = "",
    val server: String,
    val status: Int
)