package ray.mintcat.jx3.command

import net.mamoe.mirai.message.data.MessageChain
import ray.mintcat.jx3.CeYun

class CFuBenCeYun : BaseCommand("ce") {

    override val alias: MutableList<String>
        get() = mutableListOf("副本", "id", "cid")

    override val subCommand: Map<String, String>
        get() = mapOf(
            "[副本ID/玩家ID]" to "测运"
        )

    override suspend fun run(sender: Sender, from: MessageChain) {
        val command = from.contentToString().split(" ")
        if (command.size <= 1) {
            this.getInfo(sender, from)
            return
        }
        sender.sendMessage("查询中 请等待...", from)
        val data = CeYun.eval(command[1].replace("[^0-9]".toRegex(), ""))
        if (data.isNullOrEmpty()) {
            sender.sendMessage("测算失败")
        } else {
            if (CeYun.runHtml(data).contains("卜易居")){
                sender.sendMessage("测算失败")
                return
            }
            sender.sendMessage(CeYun.runHtml(data).split(" ").joinToString("\n"), from)
        }
    }

}