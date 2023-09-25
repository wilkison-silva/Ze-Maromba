package br.com.zemaromba.presentation.core_ui.navigation

abstract class BaseRouter {
    abstract val routePattern: String
    abstract fun buildRoute(vararg args: String): String
}