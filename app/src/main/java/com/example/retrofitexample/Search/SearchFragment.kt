package com.example.retrofitexample.Search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.retrofitexample.Friends.FriendsViewModel
import com.example.retrofitexample.Friends.FriendsViewModelFactory
import com.example.retrofitexample.Home.HomeFragmentDirections
import com.example.retrofitexample.Home.UserListAdapter
import com.example.retrofitexample.databinding.FragmentSearchBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: SearchViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewModelFactory = SearchViewModelFactory(this.requireActivity().application)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(SearchViewModel::class.java)

        binding.searhButton.setOnClickListener {
            if(!binding.inputName.text.isNullOrEmpty()){
                viewModel.searchUser(binding.inputName.text.toString())
            }

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isUserExistChannel.collect {
                if(it){
                    val adapter = LocalUserListAdapter { onNavigate ->
                        view.findNavController()
                            .navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment())
                    }
                    binding.foundedUsers.adapter = adapter
                    adapter.submitList(viewModel.foundedUsersList)
                }
                else{
                    Snackbar.make(
                        view,
                        "CANNOT FIND THE USER!",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

}