package com.example.bcreatingproyect.authentication.domain.matcher

interface EmailMatcher {
    fun isValid(email:String):Boolean
}