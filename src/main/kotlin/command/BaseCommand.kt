package ray.mintcat.jx3.command

import net.mamoe.mirai.message.data.MessageChain
import ray.mintcat.jx3.PluginMain

abstract class BaseCommand(val group: String) {

    abstract val subCommand: Map<String, String>

    abstract suspend fun run(sender: Sender, from: MessageChain)

    abstract val alias: MutableList<String>

    fun load() {
        PluginMain.logger.info("注入命令: $group")
        Command.commands.add(this)
    }

    suspend fun getInfo(sender: Sender, from: MessageChain) {
        val list = mutableListOf<String>()
        list.add("Jx3侍剑道童:")
        list.add("主命令: .$group")
        list.add("子命令:")
        subCommand.forEach { (key, info) ->
            list.add(" - $key")
            list.add("   $info")
        }
        sender.sendMessage(list.joinToString("\n"), from)
    }

}