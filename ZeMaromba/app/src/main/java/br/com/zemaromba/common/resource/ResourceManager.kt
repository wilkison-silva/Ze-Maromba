package br.com.zemaromba.common.resource

import android.content.Context
import androidx.annotation.StringRes

class ResourceManager(
    private val context: Context
) {
    fun getString(
        @StringRes resId: Int,
        vararg args: Any
    ): String {
        return context.getString(resId, *args)
    }

    fun getString(
        @StringRes resId: Int
    ): String {
        return context.getString(resId)
    }
}