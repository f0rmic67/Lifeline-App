package com.lifeline.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lifeline.ApiUtils
import com.lifeline.R
import com.lifeline.RegistrationData
import com.lifeline.databinding.FragmentRegisterBinding
import com.lifeline.databinding.FragmentStudentFormBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigInteger


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegistrationViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner: Spinner = requireView().findViewById(R.id.accountTypeSpinner)
        val items = resources.getStringArray(R.array.registration_options)
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, items)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinner.adapter = adapter


        val backButton = requireView().findViewById<ImageButton>(R.id.registerBackButton)
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        val registerButton = requireView().findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            try {
                val accountType:Int = viewModel.spinnerPos + 1
                val id: BigInteger = viewModel.id.toBigInteger()
                val registrationData = RegistrationData(accountType, viewModel.username, viewModel.email,
                    id, viewModel.password1, viewModel.password2, viewModel.agreedToTerms)
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                    ApiUtils.register(registrationData)
                }
            }
            catch (e:java.lang.NumberFormatException){
                Log.e("Registration", "Must Enter a valid ID")
            }
            catch (e:Exception){
                Log.e("Registration", "Exception: $e")
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}