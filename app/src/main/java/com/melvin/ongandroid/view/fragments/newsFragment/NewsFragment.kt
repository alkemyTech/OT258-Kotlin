package com.melvin.ongandroid.view.fragments.newsFragment

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentHomeBinding
import com.melvin.ongandroid.databinding.FragmentNewsBinding
import com.melvin.ongandroid.model.news.NewsModel
import com.melvin.ongandroid.view.adapters.news.NewsAdapter
import com.melvin.ongandroid.view.adapters.welcome.WelcomeActivitiesAdapter
import com.melvin.ongandroid.viewmodel.ViewModel


class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initNewsRecyclerView()
      }

    private fun initNewsRecyclerView() {
        val adapter = NewsAdapter(
            listOf(
                NewsModel(
                    id = 1,
                    name = "Nombre de la novedad",
                    content = "test content",
                    imageUrl = "https://ongapi.alkemy.org/storage/BuZwMg8jzV.jpg"
                ),
                NewsModel(
                    id = 2,
                    name = "Nombre de la novedad",
                    content = "test content",
                    imageUrl = "http://ongapi.alkemy.org/storage/YBCbsci6Qz.jpg"
                )
            )
        )
        binding.newsRecyclerView.adapter = adapter
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        binding.newsRecyclerView.layoutParams.width = screenWidth
        //helper to snap cards in the center of the screen
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.newsRecyclerView)
    }
}