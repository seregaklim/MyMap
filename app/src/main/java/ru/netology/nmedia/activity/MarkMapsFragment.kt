package ru.netology.nmedia.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
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
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.collections.MarkerManager
import com.google.maps.android.ktx.awaitAnimateCamera
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.model.cameraPosition
import com.google.maps.android.ktx.utils.collection.addMarker
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentMarkMapsBinding

import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.MapsViewModel


class MarkMapsFragment : Fragment() {


    companion object {
        var Bundle.textArg: String? by StringArg
    }


    private val viewModel: MapsViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )


    private var fragmentBinding:  FragmentMarkMapsBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save_add, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ok -> {
                fragmentBinding.let {

                    if (it != null) {
                        viewModel.changeContent(it.edit.text.toString())
                        arguments?.getParcelable<LatLng>("location")
                            ?.let { it1 -> viewModel.changeLocation(it1) }

                        viewModel.save()
                        AndroidUtils.hideKeyboard(requireView())
                        findNavController().navigateUp()

                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =FragmentMarkMapsBinding.inflate(
            inflater,
            container,
            false
        )
        fragmentBinding = binding

        arguments?.textArg
            ?.let(binding.edit::setText)

        binding.edit.requestFocus()

       //невидимый
        binding.edit.visibility = View.INVISIBLE

        arguments?.getParcelable<LatLng>("location")


        binding.button.setOnClickListener {
            binding.kategory.visibility = View.GONE
            binding.edit.visibility  = View.VISIBLE


        }




        return binding.root

    }


}































