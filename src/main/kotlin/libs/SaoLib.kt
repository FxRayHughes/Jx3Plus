package ray.mintcat.jx3.libs

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value
import ray.mintcat.jx3.Jx3API

object SaoLib : AutoSavePluginData("SaoLib") {
    val sao: MutableList<String> by value()

    fun getSao(): String {
        val list = mutableListOf<String>()
        (0..10).forEach {_->
            list.add(Jx3API.getSao()!!.data.text!!)
        }
        list.addAll(sao)
        return list.random()
    }
}