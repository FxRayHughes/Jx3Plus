package ray.mintcat.jx3.shortcommand

import net.mamoe.mirai.message.data.MessageChain
import ray.mintcat.jx3.command.Command
import ray.mintcat.jx3.command.Sender

interface ShortCommand {

    val short: List<String>

    suspend fun evalShort(short: String, sender: Sender, from: MessageChain)

    fun loadS() {
        Command.shortCommands.add(this)
    }

}