package com.example.masterclass.mvvm_beginner.epoxy

import com.airbnb.epoxy.EpoxyController
import com.example.masterclass.R
import com.example.masterclass.databinding.ModelCharacterDetailsHeaderBinding
import com.example.masterclass.databinding.ModelCharacterDetailsImageBinding
import com.example.masterclass.databinding.ModelChararcterDetailsDataPointBinding
import com.example.masterclass.mvvm_beginner.network.response.GetCharacterByIdResponse
import com.squareup.picasso.Picasso

class CharacterDetailsEpoxyController : EpoxyController() {

    private var  isLoading : Boolean = true
    set(value) {
        field = value
        if(field) {
            requestModelBuild()
        }
    }


    var characterResponse : GetCharacterByIdResponse? = null
    set(value) {
        field = value
        if(field != null) {
            isLoading = false
            requestModelBuild()
        }
    }



    override fun buildModels() {
        if(isLoading) {
            // show loading state
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        if(characterResponse == null) {
            // todo error state
            return
        }

        // add header model
        HeaderEpoxyModel(
            nameTxt = characterResponse!!.name,
            genderTxt = characterResponse!!.gender,
            statusTxt = characterResponse!!.status,
        ).id("headers").addTo(this)


        // add image model
        ImageEpoxyModel(
            imageHeader = characterResponse!!.image
        ).id("image").addTo(this)

        // add data point model(s)
        DataPointEpoxyModel(
            titleTxt = "Origin",
            descriptionTxt = characterResponse!!.origin.name
        ).id("data_point_1").addTo(this)

        DataPointEpoxyModel(
            titleTxt = "Species",
            descriptionTxt = characterResponse!!.species
        ).id("data_point_2").addTo(this)
    }

    data class HeaderEpoxyModel(
        val nameTxt: String,
        val genderTxt: String,
        val statusTxt: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header) {

        override fun ModelCharacterDetailsHeaderBinding.bind() {
           textView.text = nameTxt
            status.text = statusTxt
            gender.text = genderTxt
        }
    }

    data class ImageEpoxyModel(
        val imageHeader: String,
    ) : ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(R.layout.model_character_details_image) {
        override fun ModelCharacterDetailsImageBinding.bind() {
           Picasso.get().load(imageHeader).into(headerImageView)
        }


    }

    data class DataPointEpoxyModel(
        val titleTxt: String,
        val descriptionTxt: String,
    ) : ViewBindingKotlinModel<ModelChararcterDetailsDataPointBinding>(R.layout.model_chararcter_details_data_point) {
        override fun ModelChararcterDetailsDataPointBinding.bind() {
            title.text = titleTxt
            description.text = descriptionTxt
        }
    }
}