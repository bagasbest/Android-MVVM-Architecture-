package com.example.masterclass.mvvm_beginner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.masterclass.databinding.ActivityMainBinding
import com.example.masterclass.mvvm_beginner.epoxy.CharacterDetailsEpoxyController

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    private val epoxyController = CharacterDetailsEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.characterByIdLiveData.observe(this) { response ->
          epoxyController.characterResponse = response
            if (response == null) {
                Toast.makeText(this, "Unsuccessful network call!", Toast.LENGTH_SHORT).show()
                return@observe
            }
        }

        viewModel.refreshCharacter(54)

        binding.apply {
            epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}