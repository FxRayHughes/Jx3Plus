package ray.mintcat.jx3

import okhttp3.FormBody

object CeYun {


    fun runHtml(string: String): String {
        return HTMLParser.getElementsByClass(string, "content").eachText()[0]
            .replace("您测试的数字吉凶如下", "您测试的副本吉凶如下")
            .split(" 卜易居士手机号吉凶祥批")[0] + " 数据来源于：www.buyiju.com 切勿迷信结论仅供参考"
    }


    fun eval(number: String): String? {
        return Jx3API.httpPost("https://www.buyiju.com/cm/shuzi/", FormBody.Builder().apply {
            add("sjhao", number)
        }.build())
    }

}