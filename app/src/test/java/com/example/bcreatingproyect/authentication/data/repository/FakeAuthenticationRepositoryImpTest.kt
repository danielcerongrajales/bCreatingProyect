package com.example.bcreatingproyect.authentication.data.repository

import com.example.bcreatingproyect.authentication.domain.repository.AuthenticationRepository
import org.junit.Assert.*

class FakeAuthenticationRepositoryImpTest:AuthenticationRepository{
    var fakeError=false
    val fakeErrorMessage = "There was a server error!"
    override suspend fun login(email: String, password: String): Result<Unit> {
        return if(fakeError){
            Result.failure(Exception(fakeErrorMessage))
        }else{
            Result.success(Unit)

        }
    }

    override suspend fun signup(email: String, password: String): Result<Unit> {
        return if(fakeError){
            Result.failure(Exception())
        }else{
            Result.success(Unit)

        }

    }

    override fun getUserId(): String? {
       return  return if(fakeError){
           null
       }else{
           "USER_ID"

       }
    }

    override suspend fun logout() {

    }

}