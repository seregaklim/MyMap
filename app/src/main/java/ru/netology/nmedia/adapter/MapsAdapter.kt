
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.collections.MarkerManager
import com.google.maps.android.ktx.awaitAnimateCamera
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.model.cameraPosition
import com.google.maps.android.ktx.utils.collection.addMarker
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Maps
import ru.netology.nmedia.extensions.icon

interface OnInteractionListener {
   fun onViewCreated(view: View, savedInstanceState: Bundle?){}




}

class MapsAdapter(
    private val onInteractionListener: OnInteractionListener,
) : ListAdapter<Maps, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var googleMap: GoogleMap




    fun bind(maps: Maps) {
//        lateinit var googleMap: GoogleMap
//        googleMap.setOnMapClickListener(object :
//            GoogleMap.OnMapClickListener {
//            override fun onMapClick(latlng: LatLng) {
//
//                val location =    maps.location
//
//                googleMap.addMarker(MarkerOptions().position(location as LatLng))
//
//
//                val markerManager = MarkerManager(googleMap)
//
//
//                val collection: MarkerManager.Collection = markerManager.newCollection().apply {
//                    addMarker {
//                        position(location)
////                        icon(
////                            AppCompatResources.getDrawable(
////                                requireContext(),
////                                R.drawable.ic_netology_48dp
////                            )!!)
//                        title("${maps.content}")
//                    }.apply {
//                        tag = "Any additional data" // Any
//                    }
//                }
////                collection.setOnMarkerClickListener { marker ->
////                    // TODO: work with marker
////                    Toast.makeText(
////                        requireContext()
////                        ,
////                        (marker.tag as String),
////                        Toast.LENGTH_LONG
////                    ).show()
////                    true
////                }
//
//
//            }
//
//        }
//
//        )
//
   }



}

class PostDiffCallback : DiffUtil.ItemCallback<Maps>() {
    override fun areItemsTheSame(oldItem: Maps, newItem: Maps): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Maps, newItem: Maps): Boolean {
        return oldItem == newItem
    }
}
