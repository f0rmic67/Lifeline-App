package com.lifeline.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lifeline.R
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lifeline.ApiUtils
import com.lifeline.databinding.FragmentViewStudentInfoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigInteger

private const val ARG_STUDENT_ID = "student_info"
class ViewStudentInfo : Fragment() {

    private var studentId: Long? = null
    private lateinit var binding: FragmentViewStudentInfoBinding
    private val viewModel by viewModels<ViewStudentInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            studentId = it.getLong(ARG_STUDENT_ID)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_student_info, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(studentId != null) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                val studentData = ApiUtils.searchStudentId(BigInteger.valueOf(studentId!!), requireContext())
                if(studentData?.full_name == null && studentData?.emergency_contact == null && studentData?.medical_info == null){
                    Toast.makeText(context, "ID Not Valid", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }

                val firstName = studentData?.full_name?.first_name?:""
                val lastName = studentData?.full_name?.last_name?:""

                binding.textViewUsernameViewInfo.text = "$firstName $lastName"

                binding.textViewFirstName.text = firstName
                binding.textViewLastName.text = lastName

                binding.textViewEmergencyFirstName.text = studentData?.emergency_contact?.e_first_name
                binding.textViewEmergencyLastName.text = studentData?.emergency_contact?.e_last_name
                binding.textViewEmergencyRelation.text = studentData?.emergency_contact?.relation
                binding.textViewEmergencyPhone.text = studentData?.emergency_contact?.phone_number

                if(studentData?.medical_info != null) {
                    binding.titleMedicalInfo.visibility = View.VISIBLE

                    viewModel.heartProblemsChecked.set(studentData.medical_info.heart_problems == 1)
                    viewModel.pacemakerChecked.set(studentData.medical_info.pacemaker == 1)
                    viewModel.diabetesChecked.set(studentData.medical_info.diabetes == 1)
                    viewModel.highBloodPressureChecked.set(studentData.medical_info.high_bp == 1)
                    viewModel.strokeChecked.set(studentData.medical_info.stroke == 1)
                    viewModel.asthmaChecked.set(studentData.medical_info.asthma_copd == 1)
                    viewModel.seizuresChecked.set(studentData.medical_info.seizures == 1)
                    viewModel.cancerChecked.set(studentData.medical_info.cancer == 1)
                    viewModel.allergiesChecked.set(studentData.medical_info.allergies == 1)
                    viewModel.otherChecked.set(studentData.medical_info.other == 1)

                    binding.textViewStudentId.text = studentData.medical_info.student_id.toString()
                    binding.textViewDob.text = studentData.medical_info.dob

                    binding.textViewHeartProblems.text = studentData.medical_info.heart_problems_medications
                    binding.textViewPacemaker.text = studentData.medical_info.pacemaker_medications
                    binding.textViewDiabetes.text = studentData.medical_info.diabetes_medications
                    binding.textViewHighBp.text = studentData.medical_info.high_bp_medications
                    binding.textViewStroke.text = studentData.medical_info.stroke_medications
                    binding.textViewAsthma.text = studentData.medical_info.asthma_copd_medications
                    binding.textViewSeizures.text = studentData.medical_info.seizures_medications
                    binding.textViewCancer.text = studentData.medical_info.cancer_medications
                    binding.textViewAllergies.text = studentData.medical_info.allergies_medications
                    binding.textViewOther.text = studentData.medical_info.other_medications
                }

            }
        }
        else{
            // Pop the back stack if we don't have a defined id since we shouldn't be here if it's not
            parentFragmentManager.popBackStack()
        }

        binding.buttonExit.setOnClickListener {
            parentFragmentManager.popBackStack() // THIS MAKES IT SO THAT YOU CAN'T CALL
            parentFragmentManager.popBackStack()
        }
        binding.viewInfoBackButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(studentId:Long):ViewStudentInfo{
            return ViewStudentInfo().apply {
                arguments = Bundle().apply {
                    putLong(ARG_STUDENT_ID, studentId)
                }
            }
        }
    }
}