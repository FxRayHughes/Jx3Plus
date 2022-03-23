package ray.mintcat.jx3.datas

import kotlinx.serialization.Serializable

@Serializable
data class MacroData(
    var code: Int? = 0,
    var msg: String? = "",
    var data: MacroDataData? = null,
)

@Serializable
data class MacroDataData(
    var total: Int? = 0,
    var per: Int? = 0,
    var pages: Int? = 0,
    var page: Int? = 0,
    var list: List<Macro>? = null,
)

@Serializable
data class Macro(
    var ID: Int? = 0,
    var post_author: Int? = 0,
    var author: String? = "",
    var post_title: String? = "",
    var post_mode: String? = "",
    var post_meta: PostImeta? = null,
    var post_excerpt: String? = "",
    var post_banner: String? = "",
    var post_collection: String? = "",
    var post_type: String? = "",
    var post_subtype: String? = "",
    var post_status: String? = "",
    var original: Int? = 0,
    var client: String? = "",
    var lang: String? = "",
    var zlp: String? = "",
    var tags: String? = "",
    var comment: Int? = 0,
    var visible: String? = "",
    var post_date: String? = "",
    var post_modified: String? = "",
    var sticky: String? = "",
    var color: String? = "",
    var mark: List<String>? = null,
    var meta_1: String? = "",
    var meta_2: String? = "",
    var meta_3: String? = "",
    var meta_4: String? = "",
    var meta_5: String? = "",
    var meta_6: String? = "",
    var meta_7: String? = "",
    var meta_8: String? = "",
    var author_info: AuthorInfo? = null,
)

@Serializable
data class PostImeta(
    var zlp: String? = "",
    var data: List<PostInfo>? = null,
    var lang: String? = ""
)

@Serializable
data class PostInfo(
    var desc: String? = "无特殊备注",
    var icon: Int? = 0,
    var name: String? = "",
    var equip: String? = "",
    var macro: String? = "",
    var speed: String? = "无特殊要求",
    var talent: String? = "",
    var equip_type: String? = ""
)

@Serializable
data class AuthorInfo(
    var display_name: String? = "",
    var user_avatar: String? = "",
    var user_avatar_frame: String? = ""
)

//"{\"version\":\"v20201130\",\"xf\":\"太玄经\",\"sq\":\"3,1,1,1,1,1,1,1,1,1,2,1\"}"
@Serializable
data class Talent(
    var version: String? = "",
    var xf: String? = "",
    var sq: String? = ""
)