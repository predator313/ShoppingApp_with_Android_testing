package com.aamirashraf.shoppingprojectwithandroidtesting.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aamirashraf.shoppingprojectwithandroidtesting.R
import com.aamirashraf.shoppingprojectwithandroidtesting.ui.viewmodels.ShoppingViewModel

class ImagePickFragment:Fragment(R.layout.fragment_image_pick) {
    lateinit var viewModel:ShoppingViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
    }
}