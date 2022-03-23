package ray.mintcat.jx3.command

import net.mamoe.mirai.contact.Contact.Companion.sendImage
import net.mamoe.mirai.contact.Contact.Companion.uploadImage
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildForwardMessage
import net.mamoe.mirai.utils.ExternalResource
import okhttp3.FormBody
import ray.mintcat.jx3.Jx3API
import ray.mintcat.jx3.libs.SaoLib

class CPrice : BaseCommand("price") {

    override val alias: MutableList<String>
        get() = mutableListOf("查价")

    override val subCommand: Map<String, String>
        get() = mapOf(
            "[服名] [时装名]" to "查询不绑定时装价格\n服名可以输入 .server list 查询",
        )

    override suspend fun run(sender: Sender, from: MessageChain) {
        val command = from.contentToString().split(" ")
        if (command.size <= 2) {
            this.getInfo(sender, from)
            return
        }
        val list = listOf(
            "双梦", "华乾", "唯满侠", "姨妈", "六合一", "电八", "剑胆金榜",
            "念破", "纵月", "五鸢", "雪絮", "双剑",
            "长安", "龙虎", "蝶恋花", "飞龙在天", "青梅煮酒"
        )
        if (Jx3API.getRegionID(command[1]) == -1) {
            sender.sendMessage("区服错误 请按照以下填写", from)
            sender.sendMessage(
                """
                电点: 双梦 华乾 唯满侠 姨妈 六合一 电八 剑胆金榜
                双一: 念破 纵月 五鸢 雪絮 双剑
                电一: 长安 龙虎 蝶恋花
                双二: 飞龙在天
                双四: 青梅煮酒
            """.trimIndent(), from
            )
        }
        val data = try {
            Jx3API.getItemPrice(command[2], command[1])
        } catch (e: Exception) {
            sender.sendMessage("出了一点小问题 ${command[2]} ${command[1]} - PriceItem")
            return
        }

        var priceALl = 0.0
        data.forEach { newPriceItemStack ->
            priceALl += newPriceItemStack.price ?: 0.0
        }

        sender.sendMessage(
            """
            [${command[2]}]
            
            在 ${command[1]} 一共找到了 ${data.size} 条数据
            平均价格为 ${priceALl / data.size} 元
        """.trimIndent(), from
        )
    }

}