package com.example.retrofitexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitexample.databinding.FragmentDetailsBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: DetailsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewModelFactory = DetailsViewModelFactory(this.requireActivity().application)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(DetailsViewModel::class.java)

        binding.viewModel = viewModel
        Picasso.get()
            .load(viewModel.picture)
            .resize(250, 250)
            .into(binding.photo)

        binding.addUserFab.setOnClickListener {
            viewModel.addFriend()
            Snackbar.make(
                binding.addUserFab,
                "FRIENDSHIP REQUEST HAS BEEN SENT",
                Snackbar.LENGTH_LONG
            ).show()
        }


        return view
    }

}