package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.MarkMapsFragment.Companion.textArg

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
                    R.id.action_feedFragment_to_markMapsFragment,
                    Bundle().apply {
                        textArg = text
                    }
                )
        }
    }
}