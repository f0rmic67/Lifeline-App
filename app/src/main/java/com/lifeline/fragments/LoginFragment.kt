package com.lifeline.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lifeline.ApiUtils
import com.lifeline.LoginData
import com.lifeline.R
import com.lifeline.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = requireView().findViewById<ImageButton>(R.id.loginBackButton)
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        val loginButton = requireView().findViewById<Button>(R.id.button_perform_login)
        loginButton.setOnClickListener {
            val loginData = LoginData(viewModel.username, viewModel.password)
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                val serverResponse = ApiUtils.login(loginData, requireContext())
                Toast.makeText(context, serverResponse?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}