package com.example.masterclass.mvvm_beginner

import com.example.masterclass.mvvm_beginner.network.NetworkLayer
import com.example.masterclass.mvvm_beginner.network.response.GetCharacterByIdResponse

class SharedRepository {

    suspend fun getCharacterById(characterId : Int) : GetCharacterByIdResponse? {
        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if(request.failed) {
            return null
        }

        if (!request.isSuccessful) {
            return null
        }

        return request.body
    }

}