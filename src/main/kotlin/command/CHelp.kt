package ray.mintcat.jx3.command

import net.mamoe.mirai.Bot
import net.mamoe.mirai.message.data.MessageChain
import okhttp3.FormBody
import ray.mintcat.jx3.Jx3API
import ray.mintcat.jx3.PluginMain
import ray.mintcat.jx3.libs.SaoLib
import ray.mintcat.jx3.libs.UserData

class CHelp : BaseCommand("help") {

    override val alias: MutableList<String>
        get() = mutableListOf("帮助")

    override val subCommand: Map<String, String>
        get() = mapOf(
            "info" to "信息",
            "server" to "查询服务器状态",
            "daily" to "查询日常",
            "gold" to "查询金价",
            "macro" to "云端宏",
            "sao" to "骚话",
            "price" to "查价",
            "ba" to "百度贴吧(目前只收录唯满侠吧)",
            "ce" to "测运吉凶"
        )

    override suspend fun run(sender: Sender, from: MessageChain) {
        val command = from.contentToString().split(" ")
        if (command.size <= 1) {
            this.getInfo(sender, from)
            return
        }
        /**
         * if (!subCommand.containsKey(command[1])) {
        sender.sendMessage("Error: ${command[0]} 内不存在此子命令 ${command[1]}", from)
        }
         */
        val bot = Bot.getInstance(137458045L)
        when (command[1]) {
            "info" -> sender.sendMessage(
                """
                Jx3侍剑道童
                是由十大功劳[QQ:1523165110]
                基于Mirai开发的一个 免费的 Jx3信息查询机器人
                接入方法 添加机器人好友并拉入目标群即可
            """.trimIndent(), from
            )
            "send" -> {
                if ((sender.member?.id ?: sender.friend!!.id) == 1523165110L) {
                    bot.groups.forEach {
                        val info = from.contentToString().replace(".help send ", "")
                        Sender(it, sender.member, null).sendMessage("群发: $info")
                    }
                    return
                }
                sender.sendMessage("输入 .${command[1]} 查看命令帮助!", from)
            }
            "all" -> {
                if ((sender.member?.id ?: sender.friend!!.id) == 1523165110L) {
                    bot.friends.forEach {
                        val info = from.contentToString().replace(".help all ", "")
                        Sender(null, sender.member, it).sendMessage("群发: $info")
                    }
                    return
                }
                sender.sendMessage("输入 .${command[1]} 查看命令帮助!", from)
            }
            "to" -> {
                if ((sender.member?.id ?: sender.friend!!.id) == 1523165110L) {
                    val group = Bot.getInstance(137458045L).getGroup(command[2].toLong())
                    val info = from.contentToString().replace(".help to ${command[2]} ", "")
                    Sender(group, sender.member, null).sendMessage(info)
                    return
                }
                sender.sendMessage("输入 .${command[1]} 查看命令帮助!", from)
            }
            else -> sender.sendMessage("输入 .${command[1]} 查看命令帮助!", from)
        }
    }

}