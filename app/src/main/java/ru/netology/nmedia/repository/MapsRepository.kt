package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import ru.netology.nmedia.dto.Maps


interface MapsRepository{
    fun getAll(): LiveData<List<Maps>>
    fun save(maps: Maps)
    fun removeById(id: Long)

}
