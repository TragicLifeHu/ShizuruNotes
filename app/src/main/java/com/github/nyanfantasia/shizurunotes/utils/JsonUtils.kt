package com.github.nyanfantasia.shizurunotes.utils

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

object JsonUtils {
    private var gson: Gson? = null
    private fun initOrCheck() {
        if (gson == null) {
            gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
        }
    }

    /**
     * Json object to Java object
     * @param json
     * @param type Example.class
     * @return
     */
    fun <T> getBeanFromJson(json: String?, type: Type?): T {
        initOrCheck()
        return gson!!.fromJson(json, type)
    }

    /**
     * Json array to Java array (List)
     * @param json
     * @param type Class of array elements
     * @return
     */
    fun <T> getListFromJson(json: String?, type: Type?): List<T> {
        var json1 = json
        initOrCheck()
        if (TextUtils.isEmpty(json)) json1 = "[]"
        val jsonObjects = gson!!.fromJson<List<JsonObject>>(
            json1,
            object : TypeToken<List<JsonObject?>?>() {}.type
        )
        val lists: MutableList<T> = ArrayList()
        for (jsonObject in jsonObjects) {
            lists.add(gson!!.fromJson(jsonObject, type))
        }
        return lists
    }

    fun <T> getArrayFromJson(json: String?, type: TypeToken<*>): List<T> {
        initOrCheck()
        return gson!!.fromJson(json, type.type)
    }

    /**
     * Java object to Json
     * @param bean object instance
     * @return
     */
    fun <T> getJsonFromBean(bean: T): String {
        initOrCheck()
        return gson!!.toJson(bean)
    }

    /**
     * Java List to Json array
     * @param list List instance
     * @return
     */
    fun <T> getJsonFromList(list: List<T>?): String {
        initOrCheck()
        return gson!!.toJson(list)
    }
}