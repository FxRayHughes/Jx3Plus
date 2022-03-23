package ray.mintcat.jx3.command

import net.mamoe.mirai.Bot
import net.mamoe.mirai.message.data.MessageChain
import okhttp3.FormBody
import ray.mintcat.jx3.Jx3API
import ray.mintcat.jx3.MailUtils
import ray.mintcat.jx3.PluginMain
import ray.mintcat.jx3.libs.NoUpMail
import ray.mintcat.jx3.libs.SaoLib
import ray.mintcat.jx3.libs.UserData

class CMail : BaseCommand("mail") {

    override val alias: MutableList<String>
        get() = mutableListOf("右键")

    override val subCommand: Map<String, String>
        get() = mapOf(
            "send [ID]" to "发送邮件",
            "all" to "给所有好友发送",
            "td" to "退订",
            "dy" to "订阅消息"
        )

    override suspend fun run(sender: Sender, from: MessageChain) {
        val command = from.contentToString().split(" ")
        if (command.size <= 1) {
            this.getInfo(sender, from)
            return
        }
        val bot = Bot.getInstance(137458045L)
        when (command[1]) {
            "send" -> {
                if ((sender.member?.id ?: sender.friend!!.id) == 1523165110L) {
                    val info = from.contentToString()
                        .replace(".mail send ${command[2]} ", "")
                        .replace("<n>", "\n")
                        .split("####")
                    MailUtils.sendMail("${command[2]}@qq.com", info[1], info[0])
                    return
                }
                sender.sendMessage("权限不足！", from)
            }
            "all" -> {
                if ((sender.member?.id ?: sender.friend!!.id) == 1523165110L) {
                    val info = from.contentToString()
                        .replace(".mail all", "")
                        .replace("<n>", "\n")
                        .split("####")
                    bot.friends.forEach {
                        if (!NoUpMail.list.contains(it.id)) {
                            MailUtils.sendMail("${it.id}@qq.com", info[1], info[0])
                        }
                    }
                    return
                }
                sender.sendMessage("权限不足！", from)
            }
            "td" -> {
                val id = (sender.member?.id ?: sender.friend?.id) ?: return
                NoUpMail.list.add(id)
                sender.sendMessage("您已退订消息推送 若回心转意可输入 .mail dy", from)
            }
            "dy" -> {
                val id = (sender.member?.id ?: sender.friend?.id) ?: return
                NoUpMail.list.remove(id)
                sender.sendMessage("您订阅消息推送 若需退订可输入 .mail td", from)
            }
            else -> sender.sendMessage("输入 .${command[1]} 查看命令帮助!", from)
        }
    }

}