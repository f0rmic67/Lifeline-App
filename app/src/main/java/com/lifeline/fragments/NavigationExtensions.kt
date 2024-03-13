package com.lifeline.fragments

import androidx.fragment.app.Fragment
import com.lifeline.R
fun Fragment.navigateToFragment(fragment: Fragment) {
    parentFragmentManager.beginTransaction()
        .replace(R.id.container, fragment)
        .addToBackStack(fragment::class.java.simpleName)
        .commit()
}
