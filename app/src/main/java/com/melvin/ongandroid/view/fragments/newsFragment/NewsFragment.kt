package com.melvin.ongandroid.view.fragments.newsFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.melvin.ongandroid.databinding.FragmentNewsBinding
import com.melvin.ongandroid.model.news.NewsModel
import com.melvin.ongandroid.view.adapters.news.NewsAdapter
import com.melvin.ongandroid.viewmodel.Status
import com.melvin.ongandroid.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initNewsRecyclerView()
        viewModel.onLoadNews()
        viewModel.news.observe(viewLifecycleOwner) {
            refreshNewsRecyclerView(it)
        }
        viewModel.newsStatus.observe(viewLifecycleOwner) {
            when (it) {
                Status.LOADING -> {}//TODO: show spinner and hide recyclerview
                Status.SUCCESS -> {}//TODO: hide spinner and show recyclerview
                Status.ERROR -> {} //TODO: show error snack bar
            }
        }
    }

    private fun initNewsRecyclerView() {
        binding.newsRecyclerView.adapter = NewsAdapter(emptyList())
    }

    private fun refreshNewsRecyclerView(news: List<NewsModel>) {
        binding.newsRecyclerView.adapter = NewsAdapter(news)
    }
}