package ray.mintcat.jx3.libs

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value
import ray.mintcat.jx3.Jx3API

object NoUpMail : AutoSavePluginData("NoUpMail") {
    val list: MutableList<Long> by value()
}