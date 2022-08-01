package com.melvin.ongandroid.util

import java.util.regex.Matcher
import java.util.regex.Pattern

class ValidatePassword {
    companion object{
        var pat: Pattern?= null
        var mat: Matcher?= null

        fun isPassword(email:String): Boolean{
            pat = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[~`!@#\$%^&*()_+={[}]-|\\:;\"'<,>.?/])(?=\\S+$).{4,15}$")
            mat = pat!!.matcher(email)
            return mat!!.find()
        }
    }

    /* Regex:
        ^                                   # start-of-string
        (?=.*[0-9])                         # a digit must occur at least once
        (?=.*[a-z])                         # a lower case letter must occur at least once
        (?=.*[A-Z])                         # an upper case letter must occur at least once
        (~`!@#$%^&*()_-+={[}]|\:;"'<,>.?/)  # a special character must occur at least once you can replace with your special characters
        (?=\\S+$)                           # no whitespace allowed in the entire string
        .{4,15}                             # between 4-15 characters
        $                                   # end-of-string
     */

}