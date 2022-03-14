package ru.netology.nmedia.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.collections.MarkerManager
import com.google.maps.android.ktx.awaitAnimateCamera
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.model.cameraPosition
import com.google.maps.android.ktx.utils.collection.addMarker
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.R
import ru.netology.nmedia.R.id
import ru.netology.nmedia.R.id.ok
import ru.netology.nmedia.databinding.FragmentMarkMapsBinding
import ru.netology.nmedia.extensions.icon
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.MapsViewModel


class MarkMapsFragment : Fragment() {
    private lateinit var googleMap: GoogleMap
    companion object {
        var Bundle.textArg: String? by StringArg
    }



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


    private var fragmentBinding: FragmentMarkMapsBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ru.netology.nmedia.databinding.FragmentMarkMapsBinding.inflate(
            inflater,
            container,
            false
        )


        arguments?.textArg
            ?.let(binding.edit::setText)

        binding.edit.requestFocus()


       // невидимый
        binding.edit.visibility = View.INVISIBLE
        binding.ok.visibility = View.INVISIBLE

        arguments?.getParcelable<LatLng>("latlng")


        binding.button.setOnClickListener {
            binding.kategory.visibility = View.GONE
            binding.edit.visibility = View.VISIBLE
            binding.ok.visibility = View.VISIBLE

            binding.ok.setOnClickListener {
                val markerManager = MarkerManager(googleMap)
                AndroidUtils.hideKeyboard(requireView())



        val collection: MarkerManager.Collection = markerManager.newCollection().apply {

                    val location = arguments?.getParcelable<LatLng>("location")

                    if (location != null) {
                        viewModel.changeLocation(location)
                    }
                    viewModel.changeContent(binding.edit.text.toString())

                    addMarker {
                        if (location != null) {
                            position(location)


                            icon(
                                AppCompatResources.getDrawable(
                                    requireContext(),
                                    R.drawable.ic_netology_48dp
                                )!!
                            )
                        }

                        title("${binding.edit.text.toString()}")
                    }.apply {
                        tag = "Any additional data" // Any
                    }

                }

             findNavController().navigateUp()
            }
        }

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


            arguments?.getParcelable<LatLng>("location")

            val location= arguments?.getParcelable<LatLng>("location")



            //МАРКЕРЫ
            //Для управления камерой, а именно ее перемещениями,
            // есть связка из:CameraUpdateFactory,CameraPosition.Builder'и animateCamera

            val markerManager = MarkerManager(googleMap)

            //МАРКЕРЫ
            val collection: MarkerManager.Collection = markerManager.newCollection().apply {
                addMarker {
                    if (location != null) {
                        position(location)
                    }

                    title("")
                }.apply {
                    tag = "Any additional data" // Any
                }
            }
            collection.setOnMarkerClickListener { marker ->
                // TODO: work with marker
                Toast.makeText(
                    requireContext(),
                    (marker.tag as String),
                    Toast.LENGTH_LONG
                ).show()
                true
            }

            googleMap.awaitAnimateCamera(
                CameraUpdateFactory.newCameraPosition(
                    cameraPosition {
                        if (location != null) {
                            target(location)
                        }
                        zoom(15F)
                    }
                ))
        }
    }
}































