package com.example.retrofitexample.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitexample.UserViewModel
import com.example.retrofitexample.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        binding.users.layoutManager = LinearLayoutManager(this.context)
        binding.users.addItemDecoration(
            DividerItemDecoration(
                this.context,
                DividerItemDecoration.VERTICAL
            )
        )
        viewModel.getUsers()



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isUserExistChannel.collect {
                if(it){
                    val adapter = UserListAdapter { onNavigate ->
                        view.findNavController()
                            .navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment())
                    }
                    binding.users.adapter = adapter
                    adapter.submitList(viewModel.users.results)
                }
            }
        }
    }

}