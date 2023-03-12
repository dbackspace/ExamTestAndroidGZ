package com.example.testandroidgz.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.testandroidgz.adapter.DetailItemAdapter
import com.example.testandroidgz.databinding.ActivityDetailBinding
import com.example.testandroidgz.model.local.LocalImage
import com.example.testandroidgz.util.Constant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailActivity : AppCompatActivity() {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private var listImage = emptyList<LocalImage>()
    private var currentPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        intent?.let {
            currentPosition = it.getIntExtra(Constant.BundleKey.CURRENT_POSITION, 0)
            val listImageString = it.getStringExtra(Constant.BundleKey.LIST_IMAGE)
            val listImage: MutableList<LocalImage> = Gson().fromJson(
                listImageString, object : TypeToken<MutableList<LocalImage>>() {}.type
            )
            this.listImage = listImage
        }
        val detailAdapter = DetailItemAdapter().apply {
            setData(listImage)
        }
        binding.viewpagerSlideShow.apply {
            adapter = detailAdapter
            setCurrentItem(currentPosition, false)
        }
        binding.fabDelete.setOnClickListener {
            val focusPosition = binding.viewpagerSlideShow.currentItem
            detailAdapter.removeImage(focusPosition) {
                val intent = Intent().apply {
                    putExtra(Constant.BundleKey.LIST_IMAGE, Gson().toJson(detailAdapter.getData()))
                    putExtra(Constant.BundleKey.CURRENT_POSITION, -1)
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent().apply {
                    putExtra(Constant.BundleKey.LIST_IMAGE, Gson().toJson(detailAdapter.getData()))
                    putExtra(
                        Constant.BundleKey.CURRENT_POSITION,
                        binding.viewpagerSlideShow.currentItem
                    )
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        })
    }
}