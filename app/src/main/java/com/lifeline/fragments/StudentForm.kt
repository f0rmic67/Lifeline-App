package com.lifeline.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.lifeline.R
import com.lifeline.databinding.FragmentStudentFormBinding
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
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
        val searchButton: Button = requireView().findViewById(R.id.button_search)
        searchButton.setOnClickListener {
            navigateToFragment(ScannerFragment.newInstance())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = StudentForm()
    }
}