package com.lifeline

import java.math.BigInteger

data class RegistrationData(val accType:Int, val username:String, val email:String, val id: BigInteger, val password1:String, val password2:String, val agreedToTerms:Boolean=false)