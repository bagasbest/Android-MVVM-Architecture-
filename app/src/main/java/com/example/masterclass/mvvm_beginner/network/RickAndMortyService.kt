package com.example.masterclass.mvvm_beginner.network

import com.example.masterclass.mvvm_beginner.network.data.GetCharacterByIdResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyService {

   @GET("character/{character-id}")
   fun getCharacterById(
      @Path("character-id") characterId : Int
   ) : Call<GetCharacterByIdResponse>

}