package com.example.masterclass.mvvm_beginner.network

import com.example.masterclass.mvvm_beginner.network.data.GetCharacterByIdResponse
import retrofit2.Response

class ApiClient(
    private val rickAndMortyService: RickAndMortyService
) {

    suspend fun getCharacterById(characterId : Int) : Response<GetCharacterByIdResponse> {
        return rickAndMortyService.getCharacterById(characterId)
    }

}