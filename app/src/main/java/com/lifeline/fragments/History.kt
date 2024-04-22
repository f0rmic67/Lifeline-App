package com.lifeline.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.lifeline.ApiUtils
import com.lifeline.R
import com.lifeline.SearchDataAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class History : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = requireView().findViewById<ImageButton>(R.id.historyBackButton)
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        // Start a coroutine in onViewCreated
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            val searchDataList = ApiUtils.getRecentSearches(requireContext())
            if(searchDataList == null){
                Toast.makeText(context, "No response from server", Toast.LENGTH_SHORT).show()
            }
            else{
                Log.d("History", "$searchDataList")
                val listView: ListView = view.findViewById(R.id.history_list)
                val adapter = SearchDataAdapter(requireContext(), searchDataList)
                listView.adapter = adapter

                listView.onItemClickListener = OnItemClickListener { _, _, position, _ ->
                    navigateToFragment(ViewStudentInfo.newInstance(searchDataList[position].student_id.toLong()))
                }

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = History()
    }
}