package com.lifeline

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SearchDataAdapter(private val context: Context, private val dataList: List<SearchData>) : BaseAdapter() {
    override fun getCount(): Int = dataList.size
    override fun getItem(position: Int): Any = dataList[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false)

        val item = getItem(position) as SearchData
        itemView.findViewById<TextView>(R.id.textViewSearchTime).text = item.search_time
        itemView.findViewById<TextView>(R.id.textViewId).text = item.student_id.toString()

        return itemView
    }
}
