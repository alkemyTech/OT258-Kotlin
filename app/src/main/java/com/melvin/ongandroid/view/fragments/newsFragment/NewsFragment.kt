package com.melvin.ongandroid.view.fragments.newsFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.melvin.ongandroid.databinding.FragmentNewsBinding
import com.melvin.ongandroid.model.news.NewsModel
import com.melvin.ongandroid.view.adapters.news.NewsAdapter
import com.melvin.ongandroid.view.adapters.staff.StaffAdapter
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


                            //    NEWS

//    This function evaluate the status value, if fails, it shows a retry button. If is successful, it shows the recyclerview.
    private fun getNews() {
        viewModel.onCreateStaff()
        viewModel.newsStatus.observe(viewLifecycleOwner, Observer { status ->
            when (status!!) {
                Status.LOADING -> {
                    binding.llErrorNews.visibility = View.GONE
                    binding.NewsProgressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.newsRecyclerView.visibility = View.VISIBLE
                    binding.llErrorNews.visibility = View.GONE
                    binding.NewsProgressBar.visibility = View.GONE
                    viewModel.news.observe(viewLifecycleOwner, Observer {
                        initRecyclerView(it)
                    })
                }
                Status.ERROR -> {
                    binding.newsRecyclerView.visibility = View.GONE
                    binding.NewsProgressBar.visibility = View.GONE
                    binding.llErrorNews.visibility = View.VISIBLE
                    setUpListeners()
                }

            }
        })
    }

    private fun initRecyclerView(list: List<NewsModel>) {
        binding.newsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.newsRecyclerView.adapter = NewsAdapter(list)

    }

    // This function allows us to set up listeners
    private fun setUpListeners() {
        binding.btnRetryNews.setOnClickListener {
            getNews()
        }
    }

}