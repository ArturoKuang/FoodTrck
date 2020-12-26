package com.example.foodtrck.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.foodtrck.R

open class ToolbarFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = requireActivity().findViewById(R.id.toolbar)
        toolbarTitle = requireActivity().findViewById(R.id.toolbar_title)
    }

    fun setToolbar(title: String, backButtonVisible: Boolean) {
        displayToolbar()
        if (backButtonVisible) {
            toolbar.setNavigationIcon(R.drawable.ic_baseline_back_button)
            toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        } else {
            toolbar.navigationIcon = null
        }

        toolbarTitle.text = title
    }

    fun hideToolbar() {
        toolbar.visibility = View.GONE
    }

    fun displayToolbar() {
        toolbar.visibility = View.VISIBLE
    }
}
