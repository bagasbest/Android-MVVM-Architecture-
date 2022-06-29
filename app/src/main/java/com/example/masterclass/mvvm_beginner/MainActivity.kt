package com.example.masterclass.mvvm_beginner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.masterclass.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.refreshCharacter(54)
        viewModel.characterByIdLiveData.observe(this) { response ->
            if (response == null) {
                Toast.makeText(this, "Unsuccessful network call!", Toast.LENGTH_SHORT).show()
                return@observe
            }

            binding.apply {
                textView.text = response.name
                status.text = response.status
                origin.text = response.origin.name
                species.text = response.species
                gender.text = response.gender

                Glide.with(this@MainActivity)
                    .load(response.image)
                    .into(headerImageView)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}