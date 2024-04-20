package com.lifeline.fragments
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel

class RegistrationViewModel : ViewModel() {
    var spinnerPos:Int = -1
    var username:String = ""
    var email:String = ""
    var id:String = ""
    var password1:String = ""
    var password2:String = ""
    var agreedToTerms:Boolean=false
}
