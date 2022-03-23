package ray.mintcat.jx3

import ray.mintcat.jx3.datas.ServerData
import java.util.*

object Utils {

    /*
	 * 随机生成国内IP地址
	 */
    fun getRandomIp(): String {

        // ip范围
        val range = arrayOf(
            intArrayOf(607649792, 608174079),
            intArrayOf(1038614528, 1039007743),
            intArrayOf(1783627776, 1784676351),
            intArrayOf(2035023872, 2035154943),
            intArrayOf(2078801920, 2079064063),
            intArrayOf(-1950089216, -1948778497),
            intArrayOf(-1425539072, -1425014785),
            intArrayOf(-1236271104, -1235419137),
            intArrayOf(-770113536, -768606209),
            intArrayOf(-569376768, -564133889)
        )
        val rdint = Random()
        val index: Int = rdint.nextInt(10)
        return num2ip(range[index][0] + Random().nextInt(range[index][1] - range[index][0]))
    }

    /*
	 * 将十进制转换成ip地址
	 */
    fun num2ip(ip: Int): String {
        val b = IntArray(4)
        b[0] = (ip shr 24 and 0xff)
        b[1] = (ip shr 16 and 0xff)
        b[2] = (ip shr 8 and 0xff)
        b[3] = (ip and 0xff)
        return b[0].toString() + "." + b[1].toString() + "." + b[2].toString() + "." + b[3].toString()
    }

    fun ServerData.getStatus(): String {
        return if (status == 1) {
            "运行中"
        } else {
            "维护中"
        }
    }

}