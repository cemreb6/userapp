package com.example.retrofitexample.Profile

import android.app.Activity
import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.retrofitexample.R
import com.example.retrofitexample.databinding.FragmentProfileBinding
import com.example.retrofitexample.local.UserDatabase
import kotlinx.coroutines.flow.collect


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: ProfileViewModel

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewModelFactory =
            ProfileViewModelFactory(UserDatabase.getInstance(this.context).userDao)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(ProfileViewModel::class.java)

        registerLauncher()



        binding.choosePicButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this.requireActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                } else {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            } else {
                val intentGallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentGallery)
            }
//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1)


        }
        binding.showFriendsListButton.setOnClickListener {
            viewModel.update()
            view.findNavController().navigate(R.id.action_profileFragment_to_friendsFragment)
        }
        return view
    }

    private fun registerLauncher() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val intentFromResult = result.data
                    if (intentFromResult != null) {
                        imageUri = intentFromResult.data
                        imageUri?.let {
                            binding.userPic.setImageURI(it)
                        }
                        if (imageUri != null)
                            viewModel.imageUri = imageUri?.let { it.toString() }!!

                    }
                }
            }
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    val intentGallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(intentGallery)
                } else {
                    Toast.makeText(this.context, "PERMISSION NEEDED", Toast.LENGTH_LONG)
                }

            }
    }


    override fun onStop() {
        super.onStop()
        viewModel.update()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            requestCode == 1 && resultCode == Activity.RESULT_OK -> {
                imageUri = data!!.data
                binding.userPic.setImageURI(imageUri)
                if (imageUri != null)
                    viewModel.imageUri = imageUri?.let { it.toString() }!!
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isProfileExistChannel.collect {
                if (it) {
                    if (viewModel.imageUri != null) {

                        binding.userPic.setImageURI(Uri.parse(viewModel.imageUri))
                        imageUri = Uri.parse(viewModel.imageUri)
                    }
                }
            }
        }
    }
}