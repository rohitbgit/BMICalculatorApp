package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bmicalculator.screens.BMIDetailScreen
import com.example.bmicalculator.screens.SignInScreen
import com.example.bmicalculator.screens.UserDetailScreen
import com.example.bmicalculator.screens.UserSettingsScreen
import com.example.bmicalculator.ui.theme.BMICalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BMICalculatorTheme {
               AppNavigator()
            }
        }
    }
}
@Composable
fun AppNavigator(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "SignInScreen"){
        composable(route = "SignInScreen"){
            SignInScreen {sucess->
                if(sucess){
                    navController.navigate("UserDetailScreen")
                }
            }
        }
        composable(route = "UserDetailScreen"){
            UserDetailScreen(navController)
        }
        composable(route = "BMIDetailScreen/{height}/{weight}"){
            val height = it.arguments?.getString("height")?.toDoubleOrNull()?:0.0
            val weight = it.arguments?.getString("weight")?.toDoubleOrNull()?:0.0
            val heightUnit =it.arguments?.getString("heightUnit") ?: "cm"
            val weightUnit = it.arguments?.getString("weightUnit") ?: "kg"
            BMIDetailScreen(height = height, weight = weight,heightUnit= heightUnit,weightUnit =weightUnit)
        }
        composable(route = "UpdateUserDetails"){
           UserSettingsScreen(navController = navController)
        }
    }
}
