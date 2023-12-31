package com.example.bcreatingproyect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.bcreatingproyect.navigation.NavigationHost
import com.example.bcreatingproyect.navigation.NavigationRoute
import com.example.bcreatingproyect.ui.theme.BCreatingProyectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewmodel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BCreatingProyectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navCOntroller= rememberNavController( )
                    NavigationHost(navHostController = navCOntroller, startDestination = getStartDestination(),logout = {
                        viewmodel.logout()
                    })
                }
            }
        }
    }
    private fun getStartDestination(): NavigationRoute {
        if (viewmodel.isLoggedIn) {
            return NavigationRoute.Home
        }
        return if (viewmodel.hasSeenOnboarding) {
            NavigationRoute.Login
        } else {
            NavigationRoute.Onboarding
        }
    }
}

