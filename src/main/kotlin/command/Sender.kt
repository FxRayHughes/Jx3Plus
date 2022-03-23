package ray.mintcat.jx3.command

import io.ktor.http.*
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.contact.Friend
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.message.data.MessageSource.Key.quote

class Sender(
    val group: Group?,
    val member: Member?,
    val friend: Friend?
) {
    suspend fun sendMessage(message: String) {
        if (group != null) {
            group.sendMessage(message)
            return
        } else if (member != null) {
            member.sendMessage(message)
            return
        } else if (friend != null) {
            friend.sendMessage(message)
            return
        }
    }

    suspend fun sendFriendMessage(message: String) {
        if (member != null) {
            member.sendMessage(message)
            return
        } else if (friend != null) {
            friend.sendMessage(message)
            return
        }
    }

    suspend fun sendMessage(message: String, from: MessageChain) {
        if (group != null) {
            group.sendMessage(from.quote() + message)
            return
        } else if (member != null) {
            member.sendMessage(from.quote() + message)
            return
        } else if (friend != null) {
            friend.sendMessage(from.quote() + message)
            return
        }
    }

    suspend fun sendMessageAll(message: Message) {
        if (group != null) {
            group.sendMessage(message)
            return
        } else if (member != null) {
            member.sendMessage(message)
            return
        } else if (friend != null) {
            friend.sendMessage(message)
            return
        }
    }
}