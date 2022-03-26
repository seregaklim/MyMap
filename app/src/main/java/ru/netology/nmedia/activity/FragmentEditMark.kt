package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentEditMarkBinding
import ru.netology.nmedia.databinding.FragmentNewMapsBinding
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.MapsViewModel


class FragmentEditMark : Fragment() {

    private val viewModel: MapsViewModel by viewModels(
        ownerProducer = ::requireParentFragment,
    )

    companion object {
        var Bundle.textArg: String? by StringArg
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ru.netology.nmedia.databinding.FragmentEditMarkBinding.inflate(
            inflater,
            container,
            false
        )


        arguments?.textArg
            ?.let(binding.edit::setText)


        binding.ok.setOnClickListener {
            viewModel.changeContent(binding.edit.text.toString())
            viewModel.save()

            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }



        return binding.root
    }
}