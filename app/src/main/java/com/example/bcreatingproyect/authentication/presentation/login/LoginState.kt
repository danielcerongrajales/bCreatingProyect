package com.example.bcreatingproyect.authentication.presentation.login

data class LoginState(
    val email:String="",
    val password:String="",
    val emailError:String?=null,
    val paswordError:String?=null,
    val isLoggedIn:Boolean=false,
    val isLoading:Boolean=false
) {
}