package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.FragmentEditMark.Companion.textArg

import ru.netology.nmedia.adapter.MarksAdapter
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.databinding.FragmentMyMarkBinding

import ru.netology.nmedia.dto.Maps
import ru.netology.nmedia.viewmodel.MapsViewModel

class MarksFragment  : Fragment() {


    private val viewModel: MapsViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMyMarkBinding.inflate(
            inflater,
            container,
            false
        )

        val adapter = MarksAdapter(object : OnInteractionListener {
            override fun onEdit(maps: Maps) {
                viewModel.edit(maps)

                findNavController().navigate(
                    R.id.action_marksFragment_to_fragmentEditMark,
                    Bundle().apply { textArg = maps.content })

            }


           override fun onMark(maps: Maps) {
                findNavController().navigate(
                    R.id.action_marksFragment_to_fragmentMyMarkMap,

                   // bundleOf("id" to maps.id)

                    Bundle().apply {

                        putParcelable("location2", maps.location)
                        putString("title", maps.title)
                        putString("content", maps.content)
                    }


                )

            }

            override fun onRemove(maps: Maps) {
                viewModel.removeById(maps.id)
            }


        })
        //разделитель постов
        binding.list.addItemDecoration(
            DividerItemDecoration(binding.list.context,
                DividerItemDecoration.VERTICAL)
        )

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner, { maps ->
            adapter.submitList(maps)
        })



        return binding.root
    }
}
