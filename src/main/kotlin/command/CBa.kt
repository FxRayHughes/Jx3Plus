package ray.mintcat.jx3.command

import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildForwardMessage
import okhttp3.FormBody
import ray.mintcat.jx3.BaYiBa
import ray.mintcat.jx3.Jx3API
import ray.mintcat.jx3.MailUtils
import ray.mintcat.jx3.Utils.getStatus
import ray.mintcat.jx3.libs.BaList
import ray.mintcat.jx3.libs.NoUpMail
import ray.mintcat.jx3.libs.SaoLib
import ray.mintcat.jx3.libs.UserData

class CBa : BaseCommand("ba") {

    override val alias: MutableList<String>
        get() = mutableListOf("帮助", "818", "Ba")

    override val subCommand: Map<String, String>
        get() = mapOf(
            "[团牌/ID]" to "查询818",
            "g" to "逛贴吧!",
            "z" to "为团长昭雪[需联系1523165110]"
        )

    override suspend fun run(sender: Sender, from: MessageChain) {
        val command = from.contentToString().split(" ")
        if (command.size <= 1) {
            this.getInfo(sender, from)
            return
        }
        if (command[1] == "g") {
            sender.sendMessage("查询中 请等待...", from)
            val data = BaYiBa.eval(command[1])
            sender.sendMessage("${data.name}查询结束! 共找到 ${data.amount} 条数据", from)
            if (sender.group != null) {
                sender.sendMessageAll(buildForwardMessage(sender.group) {
                    137458045 named data.name says "共找到 ${data.amount} 条数据"
                    if (data.amount != 0) {
                        data.infos.forEach {
                            if (it.zhao.isNotEmpty()) {
                                137458045 named data.name says it.info + "\n" + it.getURL() + "\n" + "已昭雪: ${it.zhao}"
                            } else {
                                137458045 named data.name says it.info + "\n" + it.getURL()
                            }
                        }
                    }
                })
            } else {
                sender.sendMessageAll(buildForwardMessage(sender.friend!!) {
                    137458045 named data.name says "共找到 ${data.amount} 条数据"
                    if (data.amount != 0) {
                        data.infos.forEach {
                            if (it.zhao.isNotEmpty()) {
                                137458045 named data.name says it.info + "\n" + it.getURL() + "\n" + "已昭雪: ${it.zhao}"
                            } else {
                                137458045 named data.name says it.info + "\n" + it.getURL()
                            }
                        }
                    }
                })
            }
            return
        }
        if (command[1] == "z") {
            if ((sender.member?.id ?: sender.friend!!.id) == 1523165110L) {
                BaList.list[command[2]] = from.contentToString().split("${command[2]} ")[1]
                sender.sendMessage("处理成功！", from)
                return
            }
            sender.sendMessage("权限不足！请联系1523165110 人工审核后昭雪（删除搜索+解释）", from)
            return
        }
        sender.sendMessage("查询中 请等待...", from)
        val data = BaYiBa.eval(command[1])
        sender.sendMessage("${data.name}查询结束! 共找到 ${data.amount} 条数据", from)
        if (sender.group != null) {
            sender.sendMessageAll(buildForwardMessage(sender.group) {
                137458045 named data.name says "${data.name} => 共找到 ${data.amount} 条数据"
                if (data.amount != 0) {
                    data.infos.forEach {
                        if (it.zhao.isNotEmpty()) {
                            137458045 named data.name says it.info + "\n" + it.getURL() + "\n" + "已昭雪: ${it.zhao}"
                        } else {
                            137458045 named data.name says it.info + "\n" + it.getURL()
                        }
                    }
                }
            })
        } else {
            sender.sendMessageAll(buildForwardMessage(sender.friend!!) {
                137458045 named data.name says "共找到 ${data.amount} 条数据"
                if (data.amount != 0) {
                    data.infos.forEach {
                        if (it.zhao != "") {
                            137458045 named data.name says it.info + "\n" + it.getURL() + "\n" + "已昭雪: ${it.zhao}"
                        } else {
                            137458045 named data.name says it.info + "\n" + it.getURL()
                        }
                    }
                }
            })
        }

    }

}