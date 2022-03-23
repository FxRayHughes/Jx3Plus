package ray.mintcat.jx3.command

import net.mamoe.mirai.message.data.MessageChain
import ray.mintcat.jx3.Jx3API
import ray.mintcat.jx3.shortcommand.ShortCommand

class CDaily : BaseCommand("daily"), ShortCommand {

    override val subCommand: Map<String, String>
        get() = mapOf(
            "null" to "今日日常",
        )

    override val alias: MutableList<String>
        get() = mutableListOf("日常")

    override suspend fun run(sender: Sender, from: MessageChain) {
        val data = Jx3API.getDaily()!!.data ?: return
        //{'DayWar': '秘境大战',
        // 'DayBattle': '今日战场',
        // 'DayCommon': '驰援任务',
        // 'DayDraw': '美人画像',
        // 'WeekCommon': '武林通鉴·公共任务',
        // 'WeekFive': '武林通鉴·秘境任务',
        // 'WeekTeam': '武林通鉴·团队秘境',
        // 'DayCamp':"阵营日常"}
        sender.sendMessage(
            """
            日期: ${data.DateTime} (星期${data.Week})
            
            - 秘境大战: ${data.DayWar}
            - 驰援任务: ${data.DayCommon}
            - 阵营日常: ${data.DayCamp}
            - 今日战场: ${data.DayBattle}
            - 武林通鉴·公共任务: ${data.WeekCommon}
            - 武林通鉴·秘境任务: ${data.WeekFive}
            - 武林通鉴·团队秘境: ${data.WeekTeam}
            
            今天也要好好做日常哦!
        """.trimIndent(), from
        )
    }

    override val short: List<String>
        get() = listOf("日常", "周常", "大战","战场")

    override suspend fun evalShort(short: String, sender: Sender, from: MessageChain) {
        val data = Jx3API.getDaily()!!.data ?: return
        sender.sendMessage(
            """
            日期: ${data.DateTime} (星期${data.Week})
            
            - 秘境大战: ${data.DayWar}
            - 驰援任务: ${data.DayCommon}
            - 阵营日常: ${data.DayCamp}
            - 今日战场: ${data.DayBattle}
            - 武林通鉴·公共任务: ${data.WeekCommon}
            - 武林通鉴·秘境任务: ${data.WeekFive}
            - 武林通鉴·团队秘境: ${data.WeekTeam}
            
            今天也要好好做日常哦!
        """.trimIndent(), from
        )
    }
}