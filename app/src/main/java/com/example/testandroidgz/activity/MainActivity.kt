package com.example.testandroidgz.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagingData
import com.example.testandroidgz.R
import com.example.testandroidgz.adapter.SearchImageAdapter
import com.example.testandroidgz.databinding.ActivityMainBinding
import com.example.testandroidgz.model.local.LocalImage
import com.example.testandroidgz.util.Constant
import com.example.testandroidgz.util.updateValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: MainViewModel by viewModels()
    private var currentFocusPosition: Int = -1

    private val updateStateImageContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            result?.let {
                if (it.resultCode == RESULT_OK) {
                    val intent = it.data
                    val listImageString = intent?.getStringExtra(Constant.BundleKey.LIST_IMAGE)
                    val listImage: MutableList<LocalImage> = Gson().fromJson(
                        listImageString, object : TypeToken<MutableList<LocalImage>>() {}.type
                    )
                    currentFocusPosition =
                        intent?.getIntExtra(Constant.BundleKey.CURRENT_POSITION, -1) ?: -1
                    viewModel.getSearchResult().updateValue(PagingData.from(listImage))
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = SearchImageAdapter().apply {
            onClickListener = { position ->
                val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra(Constant.BundleKey.CURRENT_POSITION, position)
                    putExtra(
                        Constant.BundleKey.LIST_IMAGE,
                        Gson().toJson((binding.rvSearch.adapter as SearchImageAdapter).snapshot().items)
                    )
                }
                updateStateImageContract.launch(intent)
            }
        }
        binding.rvSearch.adapter = adapter

        viewModel.getSearchResult().observe(this) { result ->
            adapter.submitData(lifecycle, result)
        }

        binding.svSearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!TextUtils.isEmpty(query) && query!!.length >= 2) {
                    viewModel.search(query)
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.input_at_least_2_characters),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}