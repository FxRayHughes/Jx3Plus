package ray.mintcat.jx3.command

import net.mamoe.mirai.contact.isAdministrator
import net.mamoe.mirai.contact.isOwner
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildForwardMessage
import ray.mintcat.jx3.Jx3API
import ray.mintcat.jx3.Utils.getStatus
import ray.mintcat.jx3.libs.UserData
import ray.mintcat.jx3.shortcommand.ShortCommand

class CServer : BaseCommand("server"), ShortCommand {

    override val alias: MutableList<String>
        get() = mutableListOf("查服")

    override val subCommand: Map<String, String>
        get() = mapOf(
            "[服务器名称]" to "查询目标服务器",
            "all" to "查看所有服务器信息",
            "bind" to "绑定当前群的服务器"
        )

    override suspend fun run(sender: Sender, from: MessageChain) {
        val command = from.contentToString().split(" ")
        if (command.size <= 1) {
            this.getInfo(sender, from)
            return
        }
        val servers = Jx3API.getCheck() ?: return
        if (command[1] == "all") {
            sender.sendMessage("开始查询!", from)
            sender.sendMessageAll(buildForwardMessage(sender.group!!) {
                servers.data.forEach {
                    137458045 named it.server says "${it.server} => ${it.getStatus()}"
                }
            })
            return
        }
        if (command[1] == "list"){
            sender.sendMessage("""
                电点: 双梦 华乾 唯满侠 姨妈 六合一 电八 剑胆金榜
                双一: 念破 纵月 五鸢 雪絮 双剑
                电一: 长安 龙虎 蝶恋花
                双二: 飞龙在天
                双四: 青梅煮酒
            """.trimIndent(),from)
        }
        if (command[1] == "bind") {
            if (sender.member!!.id == 1523165110L || sender.member.isOwner() || sender.member.isAdministrator()) {
                if (command.size <= 2) {
                    sender.sendMessage("Error: 参数缺失", from)
                    return
                }
                val info = servers.data.firstOrNull { it.server == command[2] }
                if (info == null) {
                    sender.sendMessage("Error: 未找到服务器 ${command[2]} 请查证后再次查询", from)
                    return
                }
                UserData.region[sender.group!!.id] = command[2]
                sender.sendMessage("${sender.group.id} 绑定到了 ${command[2]}", from)
                return
            } else {
                sender.sendMessage("Error: 权限不足", from)
                return
            }
        }
        val info = servers.data.firstOrNull { it.server == command[1] }
        if (info == null) {
            sender.sendMessage("Error: 未找到服务器 ${command[1]} 请查证后再次查询", from)
            return
        }
        sender.sendMessage(
            """
            ${info.server}:
            隶属于: ${info.zone}
            当前状态: ${info.getStatus()}
        """.trimIndent()
        )

    }

    override val short: List<String>
        get() = listOf("开门")

    override suspend fun evalShort(short: String, sender: Sender, from: MessageChain) {
        val servers = Jx3API.getCheck() ?: return
        val server = UserData.region[sender.group!!.id]
        if (server == null) {
            sender.sendMessage("当前群未绑定服务器.", from)
            return
        }
        val info = servers.data.firstOrNull { it.server == server }
        if (info == null) {
            sender.sendMessage("Error: 未找到服务器 $server 请查证后再次查询", from)
            return
        }
        sender.sendMessage(
            """
            ${info.server}:
            隶属于: ${info.zone}
            当前状态: ${info.getStatus()}
        """.trimIndent()
        )
    }
}