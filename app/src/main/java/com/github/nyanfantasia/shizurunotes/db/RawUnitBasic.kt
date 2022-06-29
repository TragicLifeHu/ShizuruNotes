package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.Statics
import com.github.nyanfantasia.shizurunotes.data.Chara
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Suppress("PropertyName")
class RawUnitBasic {
    var unit_id = 0
    var unit_conversion_id = 0
    var unit_name: String? = null
    var prefab_id = 0
    var move_speed = 0
    var search_area_width = 0
    var atk_type = 0
    var normal_atk_cast_time = 0.0
    var guild_id = 0
    var comment: String? = null
    var start_time: String? = null
    var age: String? = null
    var guild: String? = null
    var race: String? = null
    var height: String? = null
    var weight: String? = null
    var birth_month: String? = null
    var birth_day: String? = null
    var blood_type: String? = null
    var favorite: String? = null
    var voice: String? = null
    var catch_copy: String? = null
    var self_text: String? = null
    var actual_name: String? = null
    var kana: String? = null
    fun setCharaBasic(chara: Chara) {
        chara.charaId = unit_id / 100
        chara.unitId = unit_id
        chara.unitConversionId = unit_conversion_id
        chara.unitName = unit_name!!
        chara.prefabId = prefab_id
        chara.searchAreaWidth = search_area_width
        chara.atkType = atk_type
        chara.moveSpeed = move_speed
        chara.normalAtkCastTime = normal_atk_cast_time
        chara.actualName = actual_name!!
        chara.age = age!!
        chara.guildId = guild_id
        chara.guild = guild!!
        chara.race = race!!
        chara.height = height!!
        chara.weight = weight!!
        chara.birthMonth = birth_month!!
        chara.birthDay = birth_day!!
        chara.bloodType = blood_type!!
        chara.favorite = favorite!!
        chara.voice = voice!!
        chara.catchCopy = catch_copy!!
        chara.comment = comment
        chara.kana = kana!!
        chara.selfText = if (self_text == null) "" else self_text!!.replace("\\\\n".toRegex(), "\n")

        //需要处理的字串
        val formatter = DateTimeFormatter.ofPattern("yyyy/M/d H:m[:s]")
        chara.startTime = LocalDateTime.parse(start_time, formatter)
        chara.iconUrl = String.format(Locale.US, Statics.ICON_URL, prefab_id + 30)
        chara.imageUrl = String.format(Locale.US, Statics.IMAGE_URL, prefab_id + 30)

        // The correct age of Kasumi should be 15
        if (unit_name!!.startsWith("カスミ") && voice == "水瀬いのり") {
            chara.age = "15"
        }
        // The correct age of Monika should be 17
        if (unit_name!!.startsWith("モニカ") && voice == "辻あゆみ") {
            chara.age = "17"
        }
        // 109301: ルゥ
        if (unit_id == 109301) {
            chara.age = "16"
        }
        if (search_area_width < 300) {
            chara.position = "1"
            chara.positionIcon = R.drawable.position_forward
        } else if (search_area_width < 600) {
            chara.position = "2"
            chara.positionIcon = R.drawable.position_middle
        } else {
            chara.position = "3"
            chara.positionIcon = R.drawable.position_rear
        }
    }
}