package com.example.testandroidgz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testandroidgz.R
import com.example.testandroidgz.databinding.ItemDetailImageBinding
import com.example.testandroidgz.model.local.LocalImage
import com.example.testandroidgz.util.BitmapUtils
import com.example.testandroidgz.util.applicationScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailItemAdapter: RecyclerView.Adapter<DetailItemAdapter.ViewHolder>() {
    private var listImage = mutableListOf<LocalImage>()

    inner class ViewHolder(private val binding: ItemDetailImageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(image: LocalImage) {
            itemView.context?.let { context ->
                context.applicationScope.launch(Dispatchers.Main) {
                    val bitmap = BitmapUtils.getThumbnail(context, image.id, image.url, false)
                    if (bitmap != null) {
                        binding.ivDetail.setImageBitmap(bitmap)
                    } else {
                        binding.ivDetail.setImageResource(R.drawable.placeholder_image)
                    }
                }
            }
        }
    }

    fun getData(): List<LocalImage> {
        return listImage
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDetailImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = listImage[position]
        holder.bind(image)
    }

    fun setData(newData: List<LocalImage>) {
        listImage.clear()
        listImage.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = listImage.size

    fun removeImage(position: Int, onLastItemRemoved: () -> Unit) {
        if (listImage.size > 0) {
            listImage.removeAt(position)
            notifyItemRemoved(position)
            if (listImage.size == 0) {
                onLastItemRemoved()
            }
        }
    }
}