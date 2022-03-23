package ray.mintcat.jx3.libs

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value

object UserData : AutoSavePluginData("UserData") {
    val region: MutableMap<Long, String> by value()
}