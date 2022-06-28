package com.example.masterclass.mvvm_beginner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.masterclass.databinding.ActivityMainBinding
import com.example.masterclass.mvvm_beginner.network.RickAndMortyService
import com.example.masterclass.mvvm_beginner.network.data.GetCharacterByIdResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val rickAndMortyService : RickAndMortyService = retrofit.create(RickAndMortyService::class.java)
        rickAndMortyService.getCharacterById(44)
            .enqueue(object : Callback<GetCharacterByIdResponse>{
                override fun onResponse(call: Call<GetCharacterByIdResponse>, response: Response<GetCharacterByIdResponse>) {
                    Log.i("MainActivity", response.toString())
                    
                    if(!response.isSuccessful) {
                        Toast.makeText(this@MainActivity, "Unsuccessful network call!", Toast.LENGTH_SHORT).show()
                        return
                    }

                    val body = response.body()!!

                    binding.apply {
                        textView.text = body.name
                        status.text = body.status
                        origin.text = body.origin.name
                        species.text = body.species
                        gender.text = body.gender

                        Glide.with(this@MainActivity)
                            .load(body.image)
                            .into(headerImageView)

                    }
                }

                override fun onFailure(call: Call<GetCharacterByIdResponse>, t: Throwable) {
                    Log.i("MainActivity", t.message ?: "Null Message")
                }

            })



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}