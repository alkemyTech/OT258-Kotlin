package com.melvin.ongandroid.util

import androidx.core.text.HtmlCompat
import androidx.core.util.PatternsCompat
import java.util.regex.Matcher
import java.util.regex.Pattern

//fun to check if the email meets the conditions
fun String.checkMail() = PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()

//fun to check if the name meets the conditions
fun String.checkName() = this.isNotEmpty() && this.length >= 3

//fun to check if the message meets the conditions
fun String.checkMessage() = this.isNotEmpty() && this.length >= 10

//fun to check if the login password meets the conditions (minimum of character 8)
fun String.checkPasswordLogin(): Boolean {
    return this.length>=8
}

//fun to check if the password meets the conditions
fun String.checkPassword(): Boolean {
    var pat: Pattern? = null
    var mat: Matcher? = null

    pat = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[~`!@#\$%^&*()_+={[}]-|\\:;\"'<,>.?/])(?=\\S+$).{4,15}$")
    mat = pat!!.matcher(this)
    return mat!!.find()
}
fun String.deleteHTML(): String {
    return HtmlCompat.fromHtml(
        this,
        HtmlCompat.FROM_HTML_MODE_LEGACY
    ).toString()
}
/* Regex (checkPassword):
    ^                                   # start-of-string
    (?=.*[0-9])                         # a digit must occur at least once
    (?=.*[a-z])                         # a lower case letter must occur at least once
    (?=.*[A-Z])                         # an upper case letter must occur at least once
    (~`!@#$%^&*()_-+={[}]|\:;"'<,>.?/)  # a special character must occur at least once you can replace with your special characters
    (?=\\S+$)                           # no whitespace allowed in the entire string
    .{4,15}                             # between 4-15 characters
    $                                   # end-of-string
*/