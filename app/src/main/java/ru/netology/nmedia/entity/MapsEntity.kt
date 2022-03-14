package ru.netology.nmedia.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import ru.netology.nmedia.dto.Maps


@Entity
data class MapsEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val content:String,
    val icon:String,
    @Embedded
    val location:  LatLng ,

    ) {
    fun toDto() = Maps(id,content, icon,location)

    companion object {
        fun fromDto(dto: Maps) =
            MapsEntity(dto.id, dto.content,dto.icon,dto.location)

    }
}