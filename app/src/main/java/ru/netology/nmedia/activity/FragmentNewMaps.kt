package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.model.LatLng
import ru.netology.nmedia.R
import ru.netology.nmedia.R.drawable.ic_netology_48dp
import ru.netology.nmedia.databinding.FragmentNewMapsBinding

import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.MapsViewModel

class FragmentNewMaps : Fragment() {


    companion object {
        var Bundle.textArg: String? by StringArg
    }


    private val viewModel: MapsViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )


    private var fragmentBinding: FragmentNewMapsBinding? = null


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
        val binding =FragmentNewMapsBinding.inflate(
            inflater,
            container,
            false
        )
        fragmentBinding = binding

        arguments?.textArg
            ?.let(binding.edit::setText)

        binding.edit.requestFocus()
        //скрыть
        binding.edit.visibility  = View.GONE

        arguments?.getParcelable<LatLng>("location")




        binding.buttonBikeRental.setOnClickListener {
            binding.kategory.visibility = View.GONE
            binding.edit.visibility  = View.VISIBLE

            viewModel.changeTitle("Bike Rental")

            val location = arguments?.getParcelable<LatLng>("location")
            viewModel.changeLocation(location)
        }


        binding.buttonCafe.setOnClickListener {
            binding.kategory.visibility = View.GONE
            binding.edit.visibility  = View.VISIBLE

            viewModel.changeTitle("Cafe")

            val location = arguments?.getParcelable<LatLng>("location")
            viewModel.changeLocation(location)
        }


        binding.buttonRestaurant.setOnClickListener {
            binding.kategory.visibility = View.GONE
            binding.edit.visibility  = View.VISIBLE

            viewModel.changeTitle("Restaurant")

            val location = arguments?.getParcelable<LatLng>("location")
            viewModel.changeLocation(location)
        }

        binding.buttonShop.setOnClickListener {
            binding.kategory.visibility = View.GONE
            binding.edit.visibility  = View.VISIBLE

            viewModel.changeTitle("Shop")

            val location = arguments?.getParcelable<LatLng>("location")
            viewModel.changeLocation(location)
        }

        binding.buttonHotel.setOnClickListener {
            binding.kategory.visibility = View.GONE
            binding.edit.visibility  = View.VISIBLE


            viewModel.changeTitle("Hotel")

            val location = arguments?.getParcelable<LatLng>("location")
            viewModel.changeLocation(location)
        }

        binding.buttonGuestHouse.setOnClickListener {
            binding.kategory.visibility = View.GONE
            binding.edit.visibility  = View.VISIBLE

            viewModel.changeTitle("Guest House")

            val location = arguments?.getParcelable<LatLng>("location")
            viewModel.changeLocation(location)
        }

        
        return binding.root

    }


}































