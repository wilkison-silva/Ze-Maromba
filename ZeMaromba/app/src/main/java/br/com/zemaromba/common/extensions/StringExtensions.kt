package br.com.zemaromba.common.extensions


fun String.addRouterParameters(vararg args: String): String {
    var finalRoute = this
    args.forEach { arg ->
        finalRoute += "/$arg"
    }
    return finalRoute
}