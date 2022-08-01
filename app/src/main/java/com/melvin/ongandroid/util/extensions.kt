package com.melvin.ongandroid.util

import androidx.core.util.PatternsCompat

//fun to check if the email meets the conditions
fun String.checkMail() = PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()

//fun to check if the name meets the conditions
fun String.checkName() = this.isNotEmpty() && this.length >= 3 && this.all { it.isLetter() }

//fun to check if the message meets the conditions
fun String.checkMessage() = this.isNotEmpty() && this.length >= 10