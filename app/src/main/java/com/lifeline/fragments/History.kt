package com.lifeline.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import com.lifeline.R
import androidx.lifecycle.lifecycleScope
import com.lifeline.ApiUtils
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

        // Start a coroutine in onViewCreated
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            val serverResponse = ApiUtils.getRecentSearches(requireContext())
            if(serverResponse == null){
                Toast.makeText(context, "No response from server", Toast.LENGTH_SHORT).show()
            }
            else{
                Log.d("History", "$serverResponse")
                val listView: ListView = view.findViewById(R.id.history_list)
                val adapter = SearchDataAdapter(requireContext(), serverResponse)
                listView.adapter = adapter

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = History()
    }
}