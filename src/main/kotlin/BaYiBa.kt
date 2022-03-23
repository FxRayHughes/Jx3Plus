package ray.mintcat.jx3

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import ray.mintcat.jx3.libs.BaList
import java.net.URLEncoder
import java.util.regex.Matcher
import java.util.regex.Pattern

object BaYiBa {

    fun String.getElement(): Element {
        val document = Jsoup.parse(this)
        return document.html(this)
    }

    fun eval(name: String): BaData {
        //https://tieba.baidu.com/f/search/res?ie=utf-8&kw=唯满侠&qw=卖汤圆
        //https://tieba.baidu.com/f/search/res?ie=utf-8isnew=0&kw=唯满侠&qw=卖汤圆&rn=10&un=&only_thread=0&sm=1&sd=&ed=&pn=3
        //https://tieba.baidu.com/f/search/res?ie=utf-8isnew=1&kw=唯满侠&qw=卖汤圆&un=&rn=10&pn=0&sd=&ed=&sm=1&only_thread=1
        //https://tieba.baidu.com/f/search/res?ie=utf-8isnew=1&kw=唯满侠&qw=卖汤圆&rn=10&un=&only_thread=1&sm=1&sd=&ed=&pn=3
        //https://tieba.baidu.com/f/search/res?isnew=1&kw=%CE%A8%C2%FA%CF%C0&qw=%C2%F4%CC%C0%D4%B2&rn=10&un=&only_thread=1&sm=1&sd=&ed=&pn=5
        val data = BaData(name)
        val info =
            Jx3API.httpGet("https://tieba.baidu.com/f/search/res?ie=utf-8isnew=1&kw=唯满侠&qw=${data.name}&rn=10&un=&only_thread=1&sm=1&sd=&ed=&pn=1")
                ?: return data
        var page = 1
        val test = HTMLParser.getElementsByClass(info, "last")
        if (test.isNotEmpty()) {
            page = test.attr("href").split("&pn=")[1].toIntOrNull() ?: 1
        }
        val regex = Pattern.compile("贴子[0-9]+篇")
        val pager = HTMLParser.getElementsByClass(info, "pager pager-search").eachText()
        val list = mutableListOf<TieData>()
        if (page >= 20) {
            page = 20
        }
        (0..page).forEach {
            evalmore(data, it).forEach { ins ->
                if (list.size <= 99) {
                    if (isContainChinese(ins.info)
                        && ins.info != "按时间倒序"
                        && ins.info != "按时间顺序"
                        && ins.info != "查看更多结果"
                        && ins.info != "按相关性顺序"
                    ) {
                        if (ins.info.contains("毒瘤")
                            || ins.info.contains("避雷")
                            || ins.info.contains("818")
                            || ins.info.contains("616")
                        ) {
                            list.add(0, ins)
                        } else {
                            list.add(ins)
                        }
                    }
                }
            }
        }
        data.infos = list.pai()
        if (pager.size >= 1) {
            val matcher = regex.matcher(
                (HTMLParser.getElementsByClass(info, "pager pager-search").eachText() ?: mutableListOf(
                    "帖子1篇",
                    "帖子1篇"
                ))[0]
            )
            while (matcher.find()) {
                val infoS = matcher.group(0).replace("贴子", "").replace("篇", "")
                if (infoS.isNotEmpty()) {
                    data.amount = infoS.toIntOrNull() ?: 0
                }
            }
        } else {
            data.amount = data.infos.size
        }
        return data
    }

    fun evalmore(data: BaData, page: Int): MutableList<TieData> {
        //?ie=utf-8isnew=1&kw=
        //https://tieba.baidu.com/f/search/res?ie=utf-8isnew=1&kw=%CE%A8%C2%FA%CF%C0&qw=%C2%F4%CC%C0%D4%B2&un=&rn=10&pn=0&sd=&ed=&sm=1&only_thread=1
        //https://tieba.baidu.com/f/search/res?ie=utf-8isnew=1&kw=唯满侠&qw=卖汤圆&rn=10&un=&only_thread=1&sm=1&sd=&ed=&pn=2
        val info =
            Jx3API.httpGet("https://tieba.baidu.com/f/search/res?ie=utf-8isnew=1&kw=唯满侠&qw=${data.name}&rn=10&un=&only_thread=1&sm=1&sd=&ed=&pn=${page}")!!
        val list = mutableListOf<TieData>()
        HTMLParser.getElementsByClass(info, "bluelink").forEach {
            val dataz = TieData(it.text(), it.attr("href"))
            if (BaList.list.keys.firstOrNull { z -> z == it.text() } != null) {
                dataz.zhao = BaList.list.firstNotNullOfOrNull { m ->
                    it.text().contains(m.key)
                    m.value
                } ?: ""
            }
            list.add(dataz)
        }
        return list
    }

}

fun String.url(): String {
    return URLEncoder.encode(this, "GBK")
}


class BaData(
    var name: String,
    var amount: Int = 0,
    var infos: MutableList<TieData> = mutableListOf(),
)

class TieData(
    val info: String,
    val http: String,
    var zhao: String = ""
) {
    fun getURL(): String {
        return "https://tieba.baidu.com/$http"
    }
}

fun isContainChinese(str: String): Boolean {
    val p = Pattern.compile("[\u4e00-\u9fa5]")
    val m: Matcher = p.matcher(str)
    return m.find()
}

fun String.contains(element: Collection<String>): Boolean {
    element.forEach {
        if (this.contains(it)) {
            return true
        }
    }
    return false
}

fun MutableList<TieData>.pai(): MutableList<TieData> {
    val save = mutableListOf<TieData>()
    this.forEach {
        if (it.info.contains("毒瘤")
            || it.info.contains("避雷")
            || it.info.contains("818")
            || it.info.contains("616")
        ) {
            save.add(0, it)
        } else {
            save.add(it)
        }
    }
    return save
}