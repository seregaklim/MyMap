package ru.netology.nmedia.activity
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.collections.MarkerManager
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.model.cameraPosition
import com.google.maps.android.ktx.utils.collection.addMarker
import ru.netology.nmedia.R
import ru.netology.nmedia.extensions.icon
import androidx.fragment.app.viewModels
import com.google.maps.android.ktx.awaitAnimateCamera
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.R.drawable
import ru.netology.nmedia.databinding.ActivityAppBinding.inflate
import ru.netology.nmedia.databinding.FragmentFeedBinding

import ru.netology.nmedia.dto.Maps
import ru.netology.nmedia.viewmodel.MapsViewModel


class FeedFragment : Fragment() {
    private lateinit var googleMap: GoogleMap
    private var mMap: GoogleMap? = null

    private val viewModel: MapsViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )


    @SuppressLint("MissingPermission")
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                googleMap.apply {
                    isMyLocationEnabled = true
                    uiSettings.isMyLocationButtonEnabled = true
                }
            } else {
                // TODO: show sorry dialog
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )



        viewModel.data.observe(viewLifecycleOwner) { map ->
            binding.apply {
                val markerManager = MarkerManager(googleMap)
                //МАРКЕРЫ
                val collection: MarkerManager.Collection =
                    markerManager.newCollection()





                map.forEach { maps ->
                    collection.apply {

                        addMarker {
                            position(maps.location)
                            title(maps.title)
                            when (maps.title) {

                                "Bike Rental" -> icon(
                                    getDrawable(
                                        requireContext(),
                                        R.drawable.bycycle2
                                    )!!
                                )

                                "Cafe" -> icon(getDrawable(requireContext(), R.drawable.cafe2)!!)

                                "Restaurant" -> icon(
                                    getDrawable(
                                        requireContext(),
                                        R.drawable.restoran2_foreground
                                    )!!
                                )
                                "Shop" -> icon(
                                    getDrawable(
                                        requireContext(),
                                        R.drawable.shop2_foreground
                                    )!!
                                )
                                "Hotel" -> icon(
                                    getDrawable(
                                        requireContext(),
                                        R.drawable.hotel2_foreground
                                    )!!
                                )
                                "Guest House" -> icon(
                                    getDrawable(
                                        requireContext(),
                                        R.drawable.guest_hous2
                                    )!!
                                )

                                else -> icon
                            }


                        }.apply {

                            tag = "${maps.content}" // Any


                            collection.setOnMarkerClickListener { marker ->
                                // TODO: work with marker

                                Toast.makeText(
                                    requireContext(),
                                    (marker.tag as String),
                                    Toast.LENGTH_LONG
                                ).show()
                                true
                            }

                        }
                    }
                }
            }
        }


        binding.textAddMap.visibility = View.INVISIBLE

        binding.menu.setOnClickListener {

            PopupMenu(it.context, it).apply {
                inflate(R.menu.options_map)


                //меню
                setOnMenuItemClickListener { item ->
                    val b = when (item.itemId) {
                        //добавить маркер
                        R.id.add -> {

                            binding.textAddMap.visibility = View.VISIBLE


                            val mMap = googleMap


                            mMap.setOnMapClickListener(object :
                                GoogleMap.OnMapClickListener {


                                override fun onMapClick(latlng: LatLng) {

                                    val location = LatLng(latlng.latitude, latlng.longitude)





                                    findNavController().navigate(R.id.action_feedFragment_to_fragmentNewMaps,
                                        Bundle().apply {

                                            putParcelable("location", location)

                                        }
                                    )

                                    mMap.setOnMapClickListener(null)
                                }

                            }

                            )

                            true
                        }
                        R.id.yourPoints -> {
                            findNavController().navigate(R.id.action_feedFragment_to_marksFragment,)

                            true
                        }

                        else -> false
                    }
                    b
                }
            }.show()


        }


        //  return inflater.inflate(R.layout.fragment_feed, container, false)
        return binding.root

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        //НАСТРОЙКА КАРТЫ
        lifecycle.coroutineScope.launchWhenCreated {
            googleMap = mapFragment.awaitMap().apply {
                isTrafficEnabled = true
                isBuildingsEnabled = true

                uiSettings.apply {
                    isZoomControlsEnabled = true
                    setAllGesturesEnabled(true)
                }
            }



            when {
                // 1. Проверяем есть ли уже права
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    googleMap.apply {
                        isMyLocationEnabled = true
                        uiSettings.isMyLocationButtonEnabled = true
                    }


                    //Для получения текущей позиции пользователя и подписки на ее обновление необходимо использование LocationServices,
                    // а именно Fused Location Provider API, который является частью большого набора Context & Location API:
                    val fusedLocationProviderClient = LocationServices
                        .getFusedLocationProviderClient(requireActivity())


                    fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                        println(it)
                        // fusedLocationClient.    getLastLocation()

                    }
                }
                // 2. Должны показать обоснование необходимости прав
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    // TODO: show rationale dialog
                }
                // 3. Запрашиваем права
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }


//            //Для управления камерой, а именно ее перемещениями,
//            // есть связка из:CameraUpdateFactory,CameraPosition.Builder'и animateCamera
//            val target = LatLng(55.751999, 37.617734)
//            val markerManager = MarkerManager(googleMap)
//
//            //МАРКЕРЫ
//           val collection: MarkerManager.Collection = markerManager.newCollection().apply {
//              addMarker {
//                    position(target)
//                    icon(getDrawable(requireContext(), drawable.ic_netology_48dp)!!)
//                    title("The Moscow Kremlin")
//               }.apply {
//                    tag = "Any additional data" // Any
//                }
//            }
//            collection.setOnMarkerClickListener { marker ->
//                // TODO: work with marker
//                Toast.makeText(
//                    requireContext(),
//                    (marker.tag as String),
//                    Toast.LENGTH_LONG
//                ).show()
//                true
//            }
//
//            googleMap.awaitAnimateCamera(
//                CameraUpdateFactory.newCameraPosition(
//                    cameraPosition {
//                        target(target)
//                        zoom(15F)
//                    }
//                ))
        }
    }

}










