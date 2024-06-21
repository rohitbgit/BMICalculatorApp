package com.example.bmicalculator.Storage

import android.content.Context

private  const val PREF_NAME = "user_pref"
private  const val HEIGHT_KEY = "key_height"
private  const val WEIGHT_KEY= "key_weight"

fun saveHeightToStorage(context: Context,height : String){
    val sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
    with(sharedPref.edit()){
        putString(HEIGHT_KEY,height)
        apply()
    }
}
fun saveWeightToStorage(context: Context,height : String){
    val sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
    with(sharedPref.edit()){
        putString(WEIGHT_KEY,height)
        apply()
    }
}

fun loadHeightData(context: Context):String{
    val sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
    return sharedPref.getString(HEIGHT_KEY,"")?: ""
}

fun loadWeightData(context: Context):String{
    val sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
    return sharedPref.getString(WEIGHT_KEY,"")?: ""
}