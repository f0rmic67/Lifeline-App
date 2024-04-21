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
import com.lifeline.StudentEmergencyContact
import com.lifeline.StudentFullName
import com.lifeline.StudentInfo
import com.lifeline.StudentMedicalInfo
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

                binding.checkBoxHeartProblems.isChecked = studentData?.medical_info?.heart_problems == 1
                binding.checkBoxPacemaker.isChecked = studentData?.medical_info?.pacemaker == 1
                binding.checkBoxDiabetes.isChecked = studentData?.medical_info?.diabetes == 1
                binding.checkBoxHighBloodPressure.isChecked = studentData?.medical_info?.high_bp == 1
                binding.checkboxStroke.isChecked = studentData?.medical_info?.stroke == 1
                binding.checkboxAsthma.isChecked = studentData?.medical_info?.asthma_copd == 1
                binding.checkboxSeizures.isChecked = studentData?.medical_info?.seizures == 1
                binding.checkboxCancer.isChecked = studentData?.medical_info?.cancer == 1
                binding.checkBoxAllergies.isChecked = studentData?.medical_info?.allergies == 1
                binding.checkboxOther.isChecked = studentData?.medical_info?.other == 1

                binding.editTextHeartProblems.setText(studentData?.medical_info?.heart_problems_medications)
                binding.editTextPacemaker.setText(studentData?.medical_info?.pacemaker_medications)
                binding.editTextDiabetes.setText(studentData?.medical_info?.diabetes_medications)
                binding.editTextHighBloodPressure.setText(studentData?.medical_info?.high_bp_medications)
                binding.editTextStroke.setText(studentData?.medical_info?.stroke_medications)
                binding.editTextAsthma.setText(studentData?.medical_info?.asthma_copd_medications)
                binding.editTextSeizures.setText(studentData?.medical_info?.seizures_medications)
                binding.editTextCancer.setText(studentData?.medical_info?.cancer_medications)
                binding.editTextAllergies.setText(studentData?.medical_info?.allergies_medications)
                binding.editTextOther.setText(studentData?.medical_info?.other_medications)
            }
        }
        else{
            // Pop the back stack if we don't have a defined id since we shouldn't be here if it's not
            parentFragmentManager.popBackStack()
        }


        binding.studentFormBackButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.buttonSave.setOnClickListener {
            val studentId = try {
                BigInteger.valueOf(binding.studentId.text.toString().toLong())
            }
            catch (e:Exception){
                null
            }
            if (studentId != null) {
                val fullName = StudentFullName(
                    binding.editTextFirstName.text.toString(),
                    binding.editTextLastName.text.toString()
                )
                val emergencyContact = StudentEmergencyContact(
                    binding.editTextEmergencyFirstName.text.toString(),
                    binding.editTextEmergencyLastName.text.toString(),
                    binding.editTextEmergencyRelation.text.toString(),
                    binding.editTextEmergencyPhone.text.toString(),
                )

                val medicalInfo = StudentMedicalInfo(
                    studentId,
                    studentId,
                    binding.editTextDob.text.toString(),
                    if (binding.checkBoxHeartProblems.isChecked) 1 else 0,
                    binding.editTextHeartProblems.text.toString(),
                    if (binding.checkBoxPacemaker.isChecked) 1 else 0,
                    binding.editTextPacemaker.text.toString(),
                    if (binding.checkBoxDiabetes.isChecked) 1 else 0,
                    binding.editTextDiabetes.text.toString(),
                    if (binding.checkBoxHighBloodPressure.isChecked) 1 else 0,
                    binding.editTextHighBloodPressure.text.toString(),
                    if (binding.checkboxStroke.isChecked) 1 else 0,
                    binding.editTextStroke.text.toString(),
                    if (binding.checkboxAsthma.isChecked) 1 else 0,
                    binding.editTextAsthma.text.toString(),
                    if (binding.checkboxSeizures.isChecked) 1 else 0,
                    binding.editTextSeizures.text.toString(),
                    if (binding.checkboxCancer.isChecked) 1 else 0,
                    binding.editTextCancer.text.toString(),
                    if (binding.checkBoxAllergies.isChecked) 1 else 0,
                    binding.editTextAllergies.text.toString(),
                    if (binding.checkboxOther.isChecked) 1 else 0,
                    binding.editTextOther.text.toString()
                )

                val studentInfo = StudentInfo(fullName, emergencyContact, medicalInfo)
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                    ApiUtils.updateStudentInfo(studentInfo, requireContext())
                }
            }
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = StudentForm()
    }
}