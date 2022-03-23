package ray.mintcat.jx3.command

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.buildForwardMessage
import ray.mintcat.jx3.Jx3API
import ray.mintcat.jx3.PluginMain
import ray.mintcat.jx3.datas.MacroData
import ray.mintcat.jx3.datas.Talent

class CMacro : BaseCommand("macro") {

    override val alias: MutableList<String>
        get() = mutableListOf("查宏")

    override val subCommand: Map<String, String>
        get() = mapOf(
            "[关键词]" to "查询宏 可以是心法 作者 介绍",
        )

    override suspend fun run(sender: Sender, from: MessageChain) {
        val command = from.contentToString().split(" ")
        if (command.size <= 1) {
            this.getInfo(sender, from)
            return
        }
        sender.sendMessage(
            """
            找到了 ${Jx3API.getMacro(command[1])!!.data!!.total} 个相关信息
        """.trimIndent(), from
        )
        if (Jx3API.getMacro(command[1])!!.data!!.total == 0) {
            return
        }
        val data = Jx3API.getMacro(command[1])!!.data!!.list!!
        val it = data.random()
        sender.sendMessage("为您推送 ${it.author}的 ${it.post_title} 👇点击下方查看👇")
        val forward = buildForwardMessage(sender.group!!) {
            1523165110 named "机器人开发者: 东方即白" says """
                数据来源于Jx3Box
                关键词: ${command[1]}
                找到了 ${Jx3API.getMacro(command[1])!!.data!!.total} 个相关信息
                
                机器人为免费机器人禁止营销
                构建纯净服务环境
            """.trimIndent()
            it.ID!! named "${it.author}" says it.post_title!!
            137458045 named "Jx3侍剑道童" says """
                        作者: ${it.author}
                        版本: ${it.post_meta!!.zlp}
                    """.trimIndent()
            it.post_meta!!.data!!.forEach { postInfo ->
                137458045 named "作者: ${it.author}" says "======================"
                137458045 named "作者: ${it.author}" says postInfo.name!!
                val talent = if (postInfo.talent?.isEmpty() == true) {
                    Talent("", "", "")
                } else {
                    Json.decodeFromString(postInfo.talent!!)
                }
                137458045 named "作者: ${it.author}" says """
                            心法: ${talent.xf}
                            奇穴: ${talent.sq}
                            速度: ${postInfo.speed ?: "无要求"}
                        """.trimIndent()
                if (postInfo.desc?.isNotEmpty() == true){
                    137458045 named "作者: ${it.author}" says (postInfo.desc ?: "无特殊备注")
                }
                137458045 named "作者: ${it.author}" says "宏:"
                137458045 named "作者: ${it.author}" says "${postInfo.macro}"
                137458045 named "作者: ${it.author}" says "云端宏: ${it.author}#${postInfo.name!!}"
            }

        }

        sender.sendMessageAll(forward)
    }

}