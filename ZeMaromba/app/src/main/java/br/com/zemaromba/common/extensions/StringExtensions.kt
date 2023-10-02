package br.com.zemaromba.common.extensions


fun String.addRouterParameters(vararg args: String): String {
    var finalRoute = this
    args.forEach { arg ->
        finalRoute += "/$arg"
    }
    return finalRoute
}

fun String.isValidUrl(): Boolean {
    val urlPattern = "^(https?|http)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"
    return this.contains(urlPattern.toRegex())
}