package com.lifeline.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.lifeline.R

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val loginButton = requireView().findViewById<Button>(R.id.button_login)
        loginButton.setOnClickListener {
            navigateToFragment(LoginFragment.newInstance())
        }
        val registrationButton = requireView().findViewById<Button>(R.id.button_register)
        registrationButton.setOnClickListener {
            // Start a coroutine in onViewCreated
            navigateToFragment(RegisterFragment.newInstance())
        }
        val scanButton = requireView().findViewById<Button>(R.id.button_search_id)
        scanButton.setOnClickListener {
            navigateToFragment(ScannerFragment.newInstance())
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}