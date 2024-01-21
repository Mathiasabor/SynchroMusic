package com.abor.synchromusic.Models

import androidx.navigation.NavHostController
import java.io.Serializable


class Person(val name : String, val firstname : String, val nav : NavHostController) : Serializable{
    var age = 10

    fun Navigate(destination : String)
    {
        nav.navigate(destination)
    }
}