package com.example.masterclass.mvvm_beginner

import com.example.masterclass.mvvm_beginner.network.NetworkLayer
import com.example.masterclass.mvvm_beginner.network.data.GetCharacterByIdResponse

class SharedRepository {

    suspend fun getCharacterById(characterId : Int) : GetCharacterByIdResponse? {
        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if(request.isSuccessful) {
            return request.body()
        }

        return null
    }

}