package com.lifeline.fragments
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel

class StudentHomeViewModel : ViewModel() {
    val heartProblemsChecked = ObservableBoolean()
    val pacemakerChecked = ObservableBoolean()
    val diabetesChecked = ObservableBoolean()
    val highBloodPressureChecked = ObservableBoolean()
    val strokeChecked = ObservableBoolean()
    val asthmaChecked = ObservableBoolean()
    val seizuresChecked = ObservableBoolean()
    val cancerChecked = ObservableBoolean()
    val otherChecked = ObservableBoolean()
}
