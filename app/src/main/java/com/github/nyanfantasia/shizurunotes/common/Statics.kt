package com.github.nyanfantasia.shizurunotes.common

object Statics {
    //  API URL
    const val API_URL = "https://redive.estertion.win"
    const val TW_TRUTH_VERSION_API_URL = "https://github.com/KiruyaMomochi/RediveData/raw/master"
    const val TW_DB_API_URL = "https://wthee.xyz"

    //  database string for use
    @JvmField
    var DB_FILE_NAME_COMPRESSED = "redive_jp.db.br"
    @JvmField
    var DB_FILE_NAME = "redive_jp.db"
    var LATEST_VERSION_URL = "$API_URL/last_version_jp.json"
    var DB_FILE_URL = "$API_URL/db/$DB_FILE_NAME_COMPRESSED"

    //  JP database
    const val DB_FILE_NAME_COMPRESSED_JP = "redive_jp.db.br"
    const val DB_FILE_NAME_JP = "redive_jp.db"
    const val LATEST_VERSION_URL_JP = "$API_URL/last_version_jp.json"
    const val DB_FILE_URL_JP = "$API_URL/db/$DB_FILE_NAME_COMPRESSED_JP"

    //  TW database
    const val DB_FILE_NAME_COMPRESSED_TW = "redive_tw.db.br"
    const val DB_FILE_NAME_TW = "redive_tw.db"
    const val LATEST_VERSION_URL_TW = "$TW_TRUTH_VERSION_API_URL/config.json"
    const val DB_FILE_URL_TW = "$TW_DB_API_URL/db/$DB_FILE_NAME_COMPRESSED_TW"

    //  CN database
    const val DB_FILE_NAME_COMPRESSED_CN = "redive_cn.db.br"
    const val DB_FILE_NAME_CN = "redive_cn.db"
    const val LATEST_VERSION_URL_CN = "$API_URL/last_version_cn.json"
    const val DB_FILE_URL_CN = "$API_URL/db/$DB_FILE_NAME_COMPRESSED_CN"

    //  Resource URL
    const val IMAGE_URL = "$API_URL/card/full/%d.webp@w800"
    const val ICON_URL = "$API_URL/icon/unit/%d.webp"
    const val SHADOW_ICON_URL = "$API_URL/icon/unit_shadow/%d.webp"
    const val SKILL_ICON_URL = "$API_URL/icon/skill/%d.webp"
    const val EQUIPMENT_ICON_URL = "$API_URL/icon/equipment/%d.webp"
    const val ITEM_ICON_URL = "$API_URL/icon/item/%d.webp"
    const val UNKNOWN_ICON = "$API_URL/icon/equipment/999999.webp"

    //  App URL
    private const val APP_RAW = "https://raw.githubusercontent.com/TragicLifeHu/ShizuruNotes/master"
    const val APP_UPDATE_LOG = "$APP_RAW/update_log.json"
    const val APP_PACKAGE =
        "https://github.com/TragicLifeHu/ShizuruNotes/releases/latest/download/shizurunotes-release.apk"
    const val APK_NAME = "shizurunotes-release.apk"
}