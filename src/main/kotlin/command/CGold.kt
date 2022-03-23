package ray.mintcat.jx3.command

import net.mamoe.mirai.message.data.MessageChain
import ray.mintcat.jx3.Jx3API
import ray.mintcat.jx3.libs.UserData
import ray.mintcat.jx3.shortcommand.ShortCommand
import java.text.SimpleDateFormat
import java.util.*

class CGold : BaseCommand("gold"), ShortCommand {

    override val alias: MutableList<String>
        get() = mutableListOf("查金")

    override val subCommand: Map<String, String>
        get() = mapOf(
            "[服务器名]" to "返回该服务器当前各平台金价",
        )

    override suspend fun run(sender: Sender, from: MessageChain) {
        val command = from.contentToString().split(" ")
        if (command.size <= 1) {
            this.getInfo(sender, from)
            return
        }
        val servers = Jx3API.getCheck() ?: return
        if (servers.data.firstOrNull { it.server == command[1] } == null) {
            sender.sendMessage("Error: 未找到服务器 ${command[1]} 请查证后再次查询", from)
            return
        }
        val gold = Jx3API.getGold(command[1]) ?: return

        val time = SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(Date(gold.time))
        sender.sendMessage(
            """
            ${command[1]}金价:
            数据时间: $time
            
            万宝楼: ${gold.data.first().wanbaolou}
            贴吧: ${gold.data.first().tieba}
            5173: ${gold.data.first().`5173`}
            7881: ${gold.data.first().`7881`}
            dd373: ${gold.data.first().dd373}
            
            参考均价: ${gold.data.first().getMean()}
        """.trimIndent(), from
        )
    }

    override val short: List<String>
        get() = listOf("金价", "查金价")

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
        val gold = Jx3API.getGold(server) ?: return

        sender.sendMessage(
            """
            ${server}金价:
            数据时间: ${Jx3API.getTime()}
            
            万宝楼: ${gold.data.first().wanbaolou}
            贴吧: ${gold.data.first().tieba}
            5173: ${gold.data.first().`5173`}
            7881: ${gold.data.first().`7881`}
            dd373: ${gold.data.first().dd373}
            
            参考均价: ${gold.data.first().getMean()}
        """.trimIndent(), from
        )
    }
}