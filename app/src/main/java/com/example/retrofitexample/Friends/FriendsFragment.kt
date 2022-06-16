package com.example.retrofitexample.Friends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitexample.Home.HomeFragmentDirections
import com.example.retrofitexample.Home.UserListAdapter
import com.example.retrofitexample.R
import com.example.retrofitexample.databinding.FragmentFriendsBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect


class FriendsFragment : Fragment() {
    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: FriendsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewModelFactory = FriendsViewModelFactory(this.requireActivity().application)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(FriendsViewModel::class.java)

        val adapter = FriendsListAdapter ({ onNavigate ->
            view.findNavController().navigate(R.id.action_friendsFragment_to_detailsFragment)
        }
            ,{ email ->
                viewModel.findUser(email)
            }
        )

        binding.friends.adapter = adapter
        viewModel.friendsList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isUserExistChannel.collect {
                if(it){
                    Snackbar.make(
                        view,
                        "DELETED FROM FRIENDS LIST!",
                        Snackbar.LENGTH_LONG
                    ).show()
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