package br.com.zemaromba.common.extensions

fun Int?.orZero(): Int {
    return this ?: 0
}