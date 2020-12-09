package com.example.foodtrck.ui

import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.foodtrck.R

open class ToolbarFragment : Fragment() {

    fun setToolbar(title: String, backButtonVisible: Boolean) {
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)

        if (backButtonVisible) {
            toolbar.setNavigationIcon(R.drawable.ic_baseline_back_button)
            toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        } else {
            toolbar.navigationIcon = null
        }

        toolbar.title = title
    }
}