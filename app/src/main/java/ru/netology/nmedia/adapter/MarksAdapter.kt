package ru.netology.nmedia.adapter

import android.provider.Settings.Global.getString
import android.system.Os.remove
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R

import ru.netology.nmedia.R.drawable.*
import ru.netology.nmedia.R.string
import ru.netology.nmedia.R.string.*
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Maps



interface OnInteractionListener {

    fun onEdit(maps: Maps) {}
    fun onRemove(maps: Maps) {}
    fun onMark(maps: Maps){}
}

class MarksAdapter(
    private val onInteractionListener: OnInteractionListener,
) : ListAdapter<Maps, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val maps = getItem(position)
        holder.bind(maps)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(maps: Maps) {
        binding.apply {


            content.text=maps.content.toString()
            title.text= maps.title


            when (maps.title) {

                "Bike Rental" ->avatar.setImageResource(bycycle2)
                "Cafe" -> avatar.setImageResource(cafe2)
                "Restaurant" ->avatar.setImageResource(restoran2_foreground)
                "Shop" ->avatar.setImageResource(shop2_foreground)
                "Hotel" ->avatar.setImageResource( hotel2_foreground)
                "Guest House" ->avatar.setImageResource( guest_hous2)
                "Stop" ->avatar.setImageResource(bus_stop2)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(ru.netology.nmedia.R.menu.options_mark)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(maps)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(maps)
                                true
                            }

                            R.id.mark -> {
                                onInteractionListener.onMark(maps)
                                true
                            }



                            else -> false
                        }
                    }
                }.show()
            }

        }
    }


}


class PostDiffCallback : DiffUtil.ItemCallback<Maps>() {
    override fun areItemsTheSame(oldItem: Maps, newItem: Maps): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Maps, newItem: Maps): Boolean {
        return oldItem == newItem
    }
}