package br.com.zemaromba.common.extensions

fun Float?.orZero(): Float {
    return this ?: 0f
}