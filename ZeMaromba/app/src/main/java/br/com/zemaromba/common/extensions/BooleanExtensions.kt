package br.com.zemaromba.common.extensions

fun Boolean?.orFalse(): Boolean {
    return this ?: false
}