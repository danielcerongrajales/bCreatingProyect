package com.example.bcreatingproyect.authentication.data.matcher

import android.util.Patterns
import com.example.bcreatingproyect.authentication.domain.matcher.EmailMatcher

class EmailMatcherImp:EmailMatcher {
    override fun isValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}