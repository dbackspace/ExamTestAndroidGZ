package com.example.testandroidgz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testandroidgz.R
import com.example.testandroidgz.databinding.ItemSearchResultBinding
import com.example.testandroidgz.model.local.LocalImage
import com.example.testandroidgz.util.BitmapUtils
import com.example.testandroidgz.util.applicationScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchImageAdapter :
    PagingDataAdapter<LocalImage, SearchImageAdapter.ItemViewHolder>(ImageComparator) {

    var onClickListener: (Int) -> Unit = {}

    inner class ItemViewHolder(
        private val binding: ItemSearchResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: LocalImage, position: Int) {
            itemView.context?.let { context ->
                context.applicationScope.launch(Dispatchers.Main) {
                    val bitmap = BitmapUtils.getThumbnail(context, image.id, image.thumbnail, true)
                    if (bitmap != null) {
                        binding.ivPicture.setImageBitmap(bitmap)
                    } else {
                        binding.ivPicture.setImageResource(R.drawable.placeholder_image)
                    }
                }
            }
            binding.tvAuthor.text = image.photographerName
            itemView.setOnClickListener { onClickListener(position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val image = getItem(position)
        holder.bind(image!!, position)
    }

    object ImageComparator : DiffUtil.ItemCallback<LocalImage>() {
        override fun areItemsTheSame(oldItem: LocalImage, newItem: LocalImage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LocalImage, newItem: LocalImage): Boolean {
            return oldItem == newItem
        }
    }
}