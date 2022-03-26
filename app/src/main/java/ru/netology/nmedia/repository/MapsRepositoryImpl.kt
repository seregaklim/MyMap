package ru.netology.nmedia.repository

import androidx.lifecycle.Transformations
import ru.netology.nmedia.dao.MapsDao
import ru.netology.nmedia.dto.Maps
import ru.netology.nmedia.entity.MapsEntity

class MapsRepositoryImpl(
    private val dao: MapsDao,
) : MapsRepository {

    override fun getAll() = Transformations.map(dao.getAll()) { list ->
        list.map {
            Maps(it.id,it.title, it.content,it.location)
        }
    }

    override fun save(maps: Maps) {
        dao.save(MapsEntity.fromDto(maps))
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }



}