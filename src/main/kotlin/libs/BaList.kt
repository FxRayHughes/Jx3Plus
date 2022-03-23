package ray.mintcat.jx3.libs

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value
import ray.mintcat.jx3.Jx3API

object BaList : AutoSavePluginData("BaList") {
    val list: MutableMap<String, String> by value()
}