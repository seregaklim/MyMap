package ru.netology.nmedia.dto

import com.google.android.gms.maps.model.LatLng


data class Maps(
    val id:Long,
    val content:String,
    val icon:String,
    val location: LatLng
    )

