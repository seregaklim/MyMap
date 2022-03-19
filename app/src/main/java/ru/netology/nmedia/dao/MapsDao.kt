package ru.netology.nmedia.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert

import androidx.room.Query
import ru.netology.nmedia.entity.MapsEntity


@Dao
interface MapsDao {
    @Query("SELECT * FROM MapsEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<MapsEntity>>

    @Insert
    fun insert(post: MapsEntity)

    @Query("UPDATE MapsEntity SET content = :content WHERE id = :id")
    fun updateContentById(id: Long, content: String)

    fun save(post: MapsEntity) =
        if (post.id == 0L) insert(post) else updateContentById(post.id, post.content)



    @Query("DELETE FROM MapsEntity WHERE id = :id")
    fun removeById(id: Long)
}