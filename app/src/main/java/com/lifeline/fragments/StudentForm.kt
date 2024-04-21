package com.lifeline.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lifeline.R
import com.lifeline.databinding.FragmentStudentFormBinding
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lifeline.ApiUtils
import com.lifeline.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.math.BigInteger

class StudentForm : Fragment() {

    private lateinit var binding: FragmentStudentFormBinding
    private val viewModel by viewModels<StudentFormViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_form, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val token = SessionManager.fetchAuthToken(requireContext())
        val decodedToken = token?.let {  JSONObject(SessionManager.decodeToken(it)) }
        val id = decodedToken?.getLong("id")

        if(id != null) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                val studentData = ApiUtils.searchStudentId(BigInteger.valueOf(id), requireContext())
                val firstName = studentData?.full_name?.first_name?:""
                val lastName = studentData?.full_name?.last_name?:""

                binding.textViewUsername.text = "$firstName $lastName"

                binding.editTextFirstName.setText(firstName)
                binding.editTextLastName.setText(lastName)

                binding.editTextEmergencyFirstName.setText(studentData?.emergency_contact?.e_first_name)
                binding.editTextEmergencyLastName.setText(studentData?.emergency_contact?.e_last_name)
                binding.editTextEmergencyRelation.setText(studentData?.emergency_contact?.relation)
                binding.editTextEmergencyPhone.setText(studentData?.emergency_contact?.phone_number)

                binding.studentId.setText(studentData?.medical_info?.student_id.toString())
                binding.editTextDob.setText(studentData?.medical_info?.dob)
            }
        }
        else{
            // Pop the back stack if we don't have a defined id since we shouldn't be here if it's not
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = StudentForm()
    }
}