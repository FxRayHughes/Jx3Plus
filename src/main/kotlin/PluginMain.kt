package ray.mintcat.jx3

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.NewFriendRequestEvent
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.utils.info
import ray.mintcat.jx3.command.Command
import ray.mintcat.jx3.command.Sender
import ray.mintcat.jx3.libs.BaList
import ray.mintcat.jx3.libs.NoUpMail
import ray.mintcat.jx3.libs.SaoLib
import ray.mintcat.jx3.libs.UserData

object PluginMain : KotlinPlugin(
    JvmPluginDescription(
        id = "ray.mintcat.jx3",
        name = "Jx3Plus",
        version = "1.0.0"
    ) {
        author("1523165110")
        // author 和 info 可以删除.
    }
) {
    override fun onEnable() {
        logger.info { "Plugin loaded" }
        //配置文件目录 "${dataFolder.absolutePath}/"
        UserData.reload()
        SaoLib.reload()
        Command.load()
        NoUpMail.reload()
        BaList.reload()
        val eventChannel = GlobalEventChannel.parentScope(this)
        globalEventChannel().subscribeAlways<GroupMessageEvent> {
            Command.runCommand(Sender(this.group, this.sender, null), this.message)
        }

        globalEventChannel().subscribeAlways<FriendMessageEvent> {
            Command.runCommand(Sender(null, null, friend), this.message)
        }
        eventChannel.subscribeAlways<FriendMessageEvent> {
            //好友信息
        }
        eventChannel.subscribeAlways<NewFriendRequestEvent> {
            //自动同意好友申请
            accept()
            Sender(null, null, bot.getFriend(1523165110L)).sendMessage("${this.fromId} 使用了机器人！")
        }
        eventChannel.subscribeAlways<BotInvitedJoinGroupRequestEvent> {
            //自动同意加群申请
            accept()
            Sender(null, null, bot.getFriend(1523165110L)).sendMessage("${this.groupId}群 使用了机器人！")
        }
    }
}
