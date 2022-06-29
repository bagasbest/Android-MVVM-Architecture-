package com.example.masterclass.mvvm_beginner.network

import com.example.masterclass.mvvm_beginner.network.data.GetCharacterByIdResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyService {

   @GET("character/{character-id}")
   suspend fun getCharacterById(
      @Path("character-id") characterId : Int
   ) : Response<GetCharacterByIdResponse>

}