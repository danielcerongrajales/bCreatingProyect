package com.example.bcreatingproyect.authentication.domain.useCase

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ValidatePasswordUseCaseTest {
    private lateinit var validatePasswordUseCase: ValidatePasswordUseCase
    @Before
    fun setup(){
        validatePasswordUseCase= ValidatePasswordUseCase()
    }
    @Test
    fun `given low caracter password, return invalid`() {
        val input = "asd"
        val result= validatePasswordUseCase(input)
        assertEquals(PasswordResult.Invalid("La contraseña tiene que tener al menos 8 caracteres"),result)
    }
    @Test
    fun `given no lowercase caracter password, return invalid`() {
        val input = "ASSADASDASDASDASD"
        val result= validatePasswordUseCase(input)
        assertEquals(PasswordResult.Invalid("La contraseña tiene que tener al menos 1 caracter en minuscula"),result)
    }
    @Test
    fun `given no uppercase caracter password, return invalid`() {
        val input = "asdasdasd"
        val result= validatePasswordUseCase(input)
        assertEquals(PasswordResult.Invalid("La contraseña tiene que tener al menos 1 caracter en mayuscula"),result)
    }
    @Test
    fun `given no number caracter password, return invalid`() {
        val input = "asdasdasdA"
        val result= validatePasswordUseCase(input)
        assertEquals(PasswordResult.Invalid("La contraseña tiene que tener al menos 1 numero"),result)
    }
    @Test
    fun `given valid password, return valid`() {
        val input = "asdasdasdA1"
        val result= validatePasswordUseCase(input)
        assertEquals(PasswordResult.Valid,result)
    }
}