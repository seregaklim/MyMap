package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.FragmentNewMaps.Companion.textArg

class AppActivity : AppCompatActivity(R.layout.activity_app) {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text?.isNotBlank() != true) {
                return@let
            }

            intent.removeExtra(Intent.EXTRA_TEXT)
            findNavController(R.id.feedFragment)
                .navigate(
                    R.id.action_feedFragment_to_fragmentNewMaps,
                    Bundle().apply {
                        textArg = text
                    }
                )

            intent.removeExtra(Intent.EXTRA_TEXT)
            findNavController(R.id.feedFragment)
                .navigate(
                    R.id.action_feedFragment_to_marksFragment,
                    Bundle().apply {
                        textArg = text
                    }
                )

            intent.removeExtra(Intent.EXTRA_TEXT)
            findNavController(R.id.marksFragment)
                .navigate(
                    R.id. action_marksFragment_to_fragmentEditMark  ,
                    Bundle().apply {
                        textArg = text
                    }
                )

            intent.removeExtra(Intent.EXTRA_TEXT)
            findNavController(R.id.marksFragment)
                .navigate(
                    R.id. action_marksFragment_to_fragmentMyMarkMap ,
                    Bundle().apply {
                        textArg = text
                    }
                )


        }
    }
}