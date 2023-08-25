package com.example.bcreatingproyect.authentication.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bcreatingproyect.R
import com.example.bcreatingproyect.authentication.presentation.login.components.loginForm.LoginForm
import com.example.bcreatingproyect.core.presentation.HabitTitle


@Composable
fun  LoginScreen(onLogin: () -> Unit,
                 onSignUp: () -> Unit,
                 viewModel: LoginViewModel= hiltViewModel()) {
    val state=viewModel.state
    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            onLogin()
        }
    }



    Box(modifier = Modifier.fillMaxSize()){
        Image(painter = painterResource(id = R.drawable.loginbackground), contentDescription ="" ,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .graphicsLayer(
                    scaleX = 1.27f,
                    scaleY = 1.27f
                ))
        Spacer(modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.background
                    )
                )
            ))
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Spacer(modifier = Modifier)
            Spacer(modifier = Modifier)
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                HabitTitle(title = "welcome to")
                HabitTitle(title = "monumental habits")
            }
            LoginForm(loginState = state, onEvent ={
                viewModel.onEvent(it)
            },onSignUp)
//            LoginForm(state,viewModel::onEvent)
        }
    }
}