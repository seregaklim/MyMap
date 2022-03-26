package ru.netology.nmedia.dto

import com.google.android.gms.maps.model.LatLng


data class Maps(
    val id:Long,
    val title:String,
    val content:String,
    val location: LatLng
    )

