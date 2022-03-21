package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.model.LatLng
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

                       val location = arguments?.getParcelable<LatLng>("location")
//                            ?.let { it -> viewModel.changeLocation(it) }

                        viewModel.changeContent(it.edit.text.toString())
                        viewModel.changeLocation(location)
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































