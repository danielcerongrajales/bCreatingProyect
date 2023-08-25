package com.example.bcreatingproyect.navigation

sealed class NavigationRoute(val route:String) {
    object Onboarding:NavigationRoute("onboarding")
    object Login : NavigationRoute("login")
    object SignUp : NavigationRoute("signup")
    object Home : NavigationRoute("home")
    object Detail : NavigationRoute("detail")
    object Settings : NavigationRoute("settings")

}
