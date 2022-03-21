package ru.netology.nmedia.viewmodel
import android.app.Application
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Maps
import ru.netology.nmedia.repository.MapsRepository
import ru.netology.nmedia.repository.MapsRepositoryImpl


 private val  empty =Maps(
     id = 0,
     content = "",
     icon ="",
     location =  LatLng(0.2,0.2)
 )


class MapsViewModel (application: Application) : AndroidViewModel(application){
    // упрощённый вариант
    private val repository: MapsRepository = MapsRepositoryImpl(
        AppDb.getInstance(context = application).mapsDao()
    )


    val data = repository.getAll()
    val edited = MutableLiveData(empty)


    fun save() {
        edited.value?.let {
            repository.save(it)

        }
        edited.value = empty
    }



    fun edit(maps: Maps) {

        edited.value = maps
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }



    fun changeLocation(location: LatLng?) {
        edited.value = location?.let { edited.value?.copy(location = it) }

    
    }

//    fun changeIcon(icon: String) {
//       edited.value = edited.value?.copy(icon = "dom")
//    }


}

