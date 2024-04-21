package com.lifeline.fragments
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel

class StudentFormViewModel : ViewModel() {
    val heartProblemsChecked = ObservableBoolean()
    val pacemakerChecked = ObservableBoolean()
    val diabetesChecked = ObservableBoolean()
    val highBloodPressureChecked = ObservableBoolean()
    val strokeChecked = ObservableBoolean()
    val asthmaChecked = ObservableBoolean()
    val seizuresChecked = ObservableBoolean()
    val cancerChecked = ObservableBoolean()
    val allergiesChecked = ObservableBoolean()
    val otherChecked = ObservableBoolean()
}
