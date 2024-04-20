package com.lifeline

data class RegistrationData(val accType:Int, val username:String, val email:String, val id:Int, val password1:String, val password2:String, val agreedToTerms:Boolean=false)