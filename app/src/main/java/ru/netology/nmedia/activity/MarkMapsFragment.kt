package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.StringArg


class MarkMapsFragment : Fragment() {

    companion object {
        var Bundle.textArg: String? by StringArg
    }



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




        return binding.root
    }
}