package com.example.bcreatingproyect.home


import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.example.bcreatingproyect.MainActivity
import com.example.bcreatingproyect.home.data.repository.FakeHomeRepository
import com.example.bcreatingproyect.home.domain.detail.usecase.DetailUseCases
import com.example.bcreatingproyect.home.domain.detail.usecase.GetHabitByIdUseCase
import com.example.bcreatingproyect.home.domain.detail.usecase.InsertHabitUseCase
import com.example.bcreatingproyect.home.domain.home.usecase.CompleteHabitUseCase
import com.example.bcreatingproyect.home.domain.home.usecase.GetHabitsForDateUseCase
import com.example.bcreatingproyect.home.domain.home.usecase.HomeUseCases
import com.example.bcreatingproyect.home.domain.home.usecase.SyncHabitUseCase
import com.example.bcreatingproyect.home.domain.repository.HomeRepository
import com.example.bcreatingproyect.home.presentation.detail.DetailScreen
import com.example.bcreatingproyect.home.presentation.detail.DetailViewModel
import com.example.bcreatingproyect.home.presentation.home.HomeScreen
import com.example.bcreatingproyect.home.presentation.home.HomeViewModel
import com.example.bcreatingproyect.navigation.NavigationRoute
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@HiltAndroidTest
class CreateHabitE2E {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()
    private lateinit var homeRepository: HomeRepository
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var navController: NavHostController

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val config = Configuration.Builder().setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor()).build()
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
        homeRepository = FakeHomeRepository()
        val useCases = HomeUseCases(
            completeHabitUseCase = CompleteHabitUseCase(homeRepository),
            getHabitsForDateUseCase = GetHabitsForDateUseCase(homeRepository),
            syncHabitUseCase = SyncHabitUseCase(homeRepository)
        )
        homeViewModel = HomeViewModel(useCases)
        val detailUseCases = DetailUseCases(
            getHabitByIdUseCase = GetHabitByIdUseCase(homeRepository),
            insertHabitUseCase = InsertHabitUseCase(homeRepository)
        )
        detailViewModel = DetailViewModel(SavedStateHandle(), detailUseCases)
        composeRule.activity.setContent {
            navController = rememberNavController()
            NavHost(navController = navController, startDestination = NavigationRoute.Home.route) {
                composable(NavigationRoute.Home.route) {
                    HomeScreen(onNewHabit = {
                        navController.navigate(NavigationRoute.Detail.route)
                    }, onSettings = {
                        navController.navigate(NavigationRoute.Settings.route)
                    }, onEditHabit = {
                        navController.navigate(NavigationRoute.Detail.route + "?habitId=$it")
                    },
                        viewModel = homeViewModel
                    )
                }

                composable(
                    NavigationRoute.Detail.route + "?habitId={habitId}",
                    arguments = listOf(
                        navArgument("habitId") {
                            type = NavType.StringType
                            nullable = true
                            defaultValue = null
                        }
                    )
                ) {
                    DetailScreen(onBack = {
                        navController.popBackStack()
                    }, onSave = {
                        navController.popBackStack()
                    },
                        viewModel = detailViewModel
                    )
                }
            }
        }
    }

    @Test
    fun createHabit(){
//        composeRule.onNodeWithText("Home").assertIsDisplayed()
//        Thread.sleep(50000)
        val habitToCreate="vamos al gym "
        composeRule.onNodeWithText("Home").assertIsDisplayed()
        composeRule.onNodeWithText(habitToCreate).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Create Habit").performClick()
        assert(navController.currentDestination?.route?.startsWith(NavigationRoute.Detail.route)==true)
        composeRule.onNodeWithText("New Habit").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Enter habit name").performClick().performTextInput(habitToCreate)
        val today =LocalDate.now().dayOfWeek
        composeRule.onNodeWithContentDescription(today.name).performClick()
        composeRule.onNodeWithContentDescription("Enter habit name").performImeAction()
        composeRule.onNodeWithText("Home").assertIsDisplayed()
//        assert(navController.currentDestination?.route?.startsWith(NavigationRoute.Home.route)==true)
        composeRule.onNodeWithText(habitToCreate).assertIsDisplayed()

        Thread.sleep(3000)
    }

}