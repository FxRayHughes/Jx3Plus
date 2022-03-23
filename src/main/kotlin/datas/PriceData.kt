package ray.mintcat.jx3.datas

import kotlinx.serialization.Serializable

@Serializable
data class PriceData(
    val state: Int? = 0,
    val timestamp: Long? = 0,
    val status: Int? = 0,
    val error: String? = "",
    val message: String? = "",
    val data: List<PriceInfo>
)

@Serializable
data class PriceInfo(
    val id: Int? = 0,
    val outwardName: String? = "",
    val outwardAlias: String? = "",
    val outwardAlias1: String? = ""
)

@Serializable
data class Price(
    val state: Int? = 0,
    val timestamp: Long? = 0,
    val message: String? = "",
    val name: String? = "",
    val status: Int? = 0,
    val error: String? = "",
    val path: String? = "",
    val data:PriceOther
)

@Serializable
data class PriceStack(
    val attention: Int? = 0,
    val info: String? = "",
    val name: String? = "",
    val name1: String? = "",
    val name2: String? = "",
    val useTime: Long? = 0,
    val prices: List<NewPriceItemStack>
)

@Serializable
data class PriceInfoI(
    val message: String? = null,
    val state: Int? = null,
    val data: PriceStackII
)

@Serializable
data class PriceStackII(
    val attention: Int? = 0,
    val info: String? = "",
    val name: String? = "",
    val name1: String? = "",
    val name2: String? = "",
    val useTime: Long? = 0,
    val images: List<PriceItemImages>
)

@Serializable
data class PriceItemImages(
    val hide: Int? = 0,
    val hots: Int? = 0,
    val id: Int? = 0,
    val image: String? = "",
    val isHot: Int? = 0,
    val nick: String? = "",
    val outwardId: Int? = 0,
    val outwardName: String? = ""
)


@Serializable
data class NewPriceItemStack(
    val audit: Int? = 0,
    val exterior: String? = "",
    val id: Int? = 0,
    val now: Int? = 0,
    val outwardId: Int? = 0,
    val outwardName: String? = "",
    val price: Int? = 0,
    val pricer: String? = "",
    val region: String? = "",
    val regionAlias: String? = "",
    val regionId: Int? = 0,
    val saleCode: String? = "",
    val server: String? = "",
    val serverId: String? = "",
    val tradeTime: String? = ""

)

@Serializable
data class PriceOther(
    val prices: List<PriceItemStack>,
)

@Serializable
data class PriceRegion(
    val id: Int? = 0,
    val createdTime: String? = "",
    val updatedTime: String? = "",
    val regionName: String? = "",
    val regionNick: String? = "",
    val charge: String? = ""
)

@Serializable
data class PriceItemStack(
    val id: Int? = 0,
    val price: Double? = 0.0,
    val region: String? = "",
    val regionAlias: String? = "",
    val regionId: Int? = 0,
    val server: String? = "",
    val serverId: Int? = 0,
    val saleCode: String? = "",
    val tradeTime: String? = "",
    val outwardName: String? = "",
    val outwardId: Int? = 0,
    val audit: Int? = 0,
    val now: Double? = 0.0,
    val exterior: String? = "",
    val pricer: String? = ""
)