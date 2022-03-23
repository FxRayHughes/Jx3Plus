package ray.mintcat.jx3.command

import net.mamoe.mirai.message.data.MessageChain
import ray.mintcat.jx3.Jx3API
import ray.mintcat.jx3.libs.SaoLib
import ray.mintcat.jx3.shortcommand.ShortCommand

class CSao : BaseCommand("sao"), ShortCommand {

    override val alias: MutableList<String>
        get() = mutableListOf("骚话")

    override val subCommand: Map<String, String>
        get() = mapOf(
            "send" to "播放骚话",
            "add" to "添加骚话"
        )

    override suspend fun run(sender: Sender, from: MessageChain) {
        val command = from.contentToString().split(" ")
        val data = SaoLib.getSao()
        if (command.size <= 1) {
            this.getInfo(sender, from)
            sender.sendMessage("试试发送 骚话 或 说点好听的", from)
            return
        }
        when (command[1]) {
            "send" -> sender.sendMessage(data, from)
            "add" -> {
                val adds = from.contentToString().replace(".sao add ", "")
                if (SaoLib.sao.contains(adds)){
                    sender.sendMessage("该骚话已经被收录过了 再说点别的吧", from)
                    return
                }
                SaoLib.sao.add(adds)
                sender.sendMessage("感谢你 这句骚话已经被记录了", from)
            }
            else -> sender.sendMessage(data, from)
        }
    }

    override val short: List<String>
        get() = listOf("骚话", "说点好听的")

    override suspend fun evalShort(short: String, sender: Sender, from: MessageChain) {
        val data = Jx3API.getSao() ?: return
        sender.sendMessage(data.data.text!!, from)
    }
}