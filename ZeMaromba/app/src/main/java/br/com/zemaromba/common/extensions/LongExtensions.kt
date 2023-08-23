package br.com.zemaromba.common.extensions

fun Long?.orZero(): Long {
    return this ?: 0
}