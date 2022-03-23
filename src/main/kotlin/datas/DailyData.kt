package ray.mintcat.jx3.datas

import kotlinx.serialization.Serializable

@Serializable
data class DailyData(
    var code: Int = 0,
    var msg: String? = "null",
    var data: DailyDataInfo? = null,
    var time: Long = 0
)
@Serializable
data class DailyDataInfo(
    var DateTime: String = "获取失败",
    var Week: String? = "获取失败",
    var DayWar: String? = "获取失败",
    var DayBattle: String? = "获取失败",
    var DayCommon: String? = "获取失败",
    var DayCamp: String? = "获取失败",
    var WeekCommon: String? = "获取失败",
    var WeekFive: String? = "获取失败",
    var WeekTeam: String? = "获取失败",
)