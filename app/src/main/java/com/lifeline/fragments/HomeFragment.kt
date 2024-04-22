package com.lifeline.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.lifeline.ApiUtils
import com.lifeline.R
import com.lifeline.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class HomeFragment : Fragment() {

    private lateinit var logoutButton:Button
    private lateinit var loginButton :Button
    private lateinit var registrationButton:Button
    private lateinit var updateInfoButton:Button
    private lateinit var buttonHistory:Button
    private lateinit var scanButton:Button
    private lateinit var uploadEmsInfo:Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logoutButton = requireView().findViewById(R.id.button_logout)
        loginButton = requireView().findViewById(R.id.button_login)
        registrationButton = requireView().findViewById(R.id.button_register)
        updateInfoButton = requireView().findViewById(R.id.button_update_info)
        buttonHistory = requireView().findViewById(R.id.button_history)
        scanButton = requireView().findViewById(R.id.button_search_id)
        uploadEmsInfo = requireView().findViewById(R.id.button_ems_info)

        updateUI()

        logoutButton.setOnClickListener {
            SessionManager.clearAuthToken(requireContext())
            updateUI()
        }
        loginButton.setOnClickListener {
            navigateToFragment(LoginFragment.newInstance())
        }
        registrationButton.setOnClickListener {
            // Start a coroutine in onViewCreated
            navigateToFragment(RegisterFragment.newInstance())
        }

        updateInfoButton.setOnClickListener {
            // Start a coroutine in onViewCreated
            navigateToFragment(StudentForm.newInstance())
        }


        buttonHistory.setOnClickListener {
            // Start a coroutine in onViewCreated
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                val serverResponse = ApiUtils.getRecentSearches(requireContext())
                if(serverResponse == null){
                    Toast.makeText(context, "No response from server", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context, "$serverResponse", Toast.LENGTH_LONG).show()
                    Log.d("History", "$serverResponse")
                }
            }
        }





        scanButton.setOnClickListener {
            navigateToFragment(ScannerFragment.newInstance())
        }

    }

    private fun updateUI(){
        val token = SessionManager.fetchAuthToken(requireContext())
        val decodedToken = token?.let {  JSONObject(SessionManager.decodeToken(it)) }
        val accountType = decodedToken?.getInt("accType")

        logoutButton.visibility = if(accountType != null)  View.VISIBLE else View.GONE
        loginButton.visibility = if(accountType == null)  View.VISIBLE else View.GONE
        registrationButton.visibility = if(accountType == null)  View.VISIBLE else View.GONE
        updateInfoButton.visibility = if(accountType == 1)  View.VISIBLE else View.GONE
        buttonHistory.visibility = if(accountType == 2 || accountType == 3)  View.VISIBLE else View.GONE
        uploadEmsInfo.visibility = if(accountType == 3)  View.VISIBLE else View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}