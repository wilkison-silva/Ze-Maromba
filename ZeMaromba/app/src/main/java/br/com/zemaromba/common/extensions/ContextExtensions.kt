package br.com.zemaromba.common.extensions

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException


fun Context.convertJsonFileToString(fileName: String): String? {
    val jsonString: String
    try {
        jsonString = this.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

inline fun <reified T> Context.parseJsonStringToClassObject(jsonFileString: String): T {

    val objectType = object : TypeToken<T>() {}.type

    return Gson().fromJson(jsonFileString, objectType)
}

fun Context.isDatabaseCreated(databaseName: String): Boolean {
    val databaseFile = this.getDatabasePath(databaseName)
    return databaseFile.exists()
}