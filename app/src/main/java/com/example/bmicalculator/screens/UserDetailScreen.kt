package com.example.bmicalculator.screens

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bmicalculator.AppComponent.HeightWeightInputField
import com.example.bmicalculator.R
import com.example.bmicalculator.Storage.saveHeightToStorage
import com.example.bmicalculator.Storage.saveWeightToStorage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun UserDetailScreen(navController: NavController) {
    val userName = remember { mutableStateOf("") }
    val dob = remember { mutableStateOf("") }
    val height = remember { mutableStateOf("") }
    val weight = remember { mutableStateOf("") }
    val heightUnit = remember { mutableStateOf("cm") }
    val weightUnit = remember { mutableStateOf("kg") }
    val errorMessage = remember { mutableStateOf("") }

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEBAF)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "USER DETAILS:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = userName.value,
                onValueChange = {
                    userName.value = it
                },
                label = { Text(text = "Name") }
            )
            
            DatePickerField(value = dob.value) {date->
                dob.value = date
            }

            HeightWeightInputField(
                label = "Height",
                value = height.value,
                unit = heightUnit,
                onUnitChange = {
                    heightUnit.value = it
                },
                onValueChange = {
                    height.value = it
                })
            HeightWeightInputField(
                label = "Weight",
                value = weight.value,
                unit = weightUnit,
                onUnitChange = {
                    weightUnit.value = it
                },
                onValueChange = {
                    weight.value = it
                })

            Spacer(modifier = Modifier.height(10.dp))
            Button(modifier = Modifier.width(200.dp), onClick = {
//                if (validateInput(
//                        userName.value,
//                        height.value,
//                        weight.value,
//                        errorMessage
//                    )
//                ) {
//                    navController.navigate("BMIDetailScreen/${height.value}/${weight.value}")
//                }
                saveHeightToStorage(context,height.value)
                saveWeightToStorage(context,weight.value)
                navController.navigate("BMIDetailScreen/${height.value}/${weight.value}")
            }) {
                Text(text = "Calculate BMI", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(modifier = Modifier.width(200.dp),onClick = {
                navController.navigate("UpdateUserDetails")
            }) {
                Text("Update Details")
            }
            SignOutButton(context = LocalContext.current, navController = navController)
            if (errorMessage.value.isNotEmpty()) {
                Text(text = errorMessage.value, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun DatePickerField(value: String, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    if (value.isNotEmpty()) {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        calendar.time = sdf.parse(value)!!
    }

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    OutlinedTextField(
        value = value,
        onValueChange = { },
        label = { Text(text = "D.O.B (dd/MM/yyyy)") },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = {
                DatePickerDialog(
                    context,
                    { _, selectedYear, selectedMonth, selectedDay ->
                        val selectedDate = "${"%02d".format(selectedDay)}/${"%02d".format(selectedMonth + 1)}/$selectedYear"
                        onDateSelected(selectedDate)
                    },
                    year, month, day
                ).show()
            }) {
                Icon(Icons.Default.DateRange, contentDescription = "Select Date")
            }
        },
    )
}

@Composable
fun SignOutButton(context: Context, navController: NavController) {
    Button(modifier = Modifier.width(200.dp), onClick = {
        SignOut(context) { success ->
            if (success) {
                navController.navigate("SignInScreen") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            } else {
                Toast.makeText(context, "SignOut Failed", Toast.LENGTH_SHORT).show()
            }

        }
    }) {
        Text(text = "SignOut", fontSize = 18.sp)
    }
}

fun validateInput(
    name: String,
    height: String,
    weight: String,
    errorMessage: MutableState<String>
): Boolean {
    if (name.isBlank()) {
        errorMessage.value = "Name cannot be empty"
        return false
    }

    val heightValue = height.toDoubleOrNull()
    if (heightValue == null || heightValue <= 0) {
        errorMessage.value = "Height must be a valid number"
        return false
    }

    val weightValue = weight.toDoubleOrNull()
    if (weightValue == null || weightValue <= 0) {
        errorMessage.value = "Weight must be a valid number"
        return false
    }

    errorMessage.value = ""
    return true
}



fun cmToMeter(cm: Double): Double {
    return cm / 100
}

fun meterToCm(m: Double): Double {
    return m * 100
}

fun kgToPound(kg: Double): Double {
    return kg * 2.20462
}

fun poundToKg(lb: Double): Double {
    return lb / 2.20462
}


private fun SignOut(context: Context, onSignOutComplete: (Boolean) -> Unit) {
    val googleSignInClient: GoogleSignInClient
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    googleSignInClient = GoogleSignIn.getClient(context, gso)
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    mAuth.signOut()
    googleSignInClient.signOut().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            onSignOutComplete(true)
        } else {
            onSignOutComplete(false)
        }

    }
}
