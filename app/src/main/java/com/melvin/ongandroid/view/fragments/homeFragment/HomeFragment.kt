package com.melvin.ongandroid.view.fragments.homeFragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.material.snackbar.Snackbar
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentHomeBinding
import com.melvin.ongandroid.model.news.NewsList
import com.melvin.ongandroid.model.news.NewsRepository
import com.melvin.ongandroid.model.slides.SlidesDataModel
import com.melvin.ongandroid.model.testimonials.DataModel
import com.melvin.ongandroid.services.di.NetworkModule
import com.melvin.ongandroid.uitel.LoadingDialog
import com.melvin.ongandroid.view.adapters.news.NewsAdapter
import com.melvin.ongandroid.view.adapters.testimonials.TestimonialsAdapter
import com.melvin.ongandroid.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.melvin.ongandroid.view.adapters.welcome.WelcomeActivitiesAdapter
import com.melvin.ongandroid.viewmodel.Errors
import com.melvin.ongandroid.viewmodel.NewsViewModel
import com.melvin.ongandroid.viewmodel.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        initComponent()


    }

    private fun initComponent() {
        getTestimonials()
        testimonialsArrowClick()
        getSlides()
        setUpListeners()
        viewModel.apiStatus.observe(viewLifecycleOwner, Observer {
            if (it == Errors.ALL) {
                onLoadError(resources.getString(R.string.generalError)) { viewModel.refresh() }
            }
        })
    }

    //This function start the testimonials query, an gives the response to the recyclerview
    private fun getTestimonials() {
        viewModel.onLoadTestimonials()
        viewModel.testimonials.observe(viewLifecycleOwner, Observer {
            initTestimonialRecyclerView(it)
        })
        viewModel.testimonialStatus.observe(viewLifecycleOwner) {
            when (it) {
                Status.LOADING -> {}
                Status.SUCCESS -> {}
                Status.ERROR -> onLoadError(resources.getString(R.string.on_testimonials_loading_error)) {
                        viewModel.onLoadTestimonials()
                }
            }
        }
    }

    //This function init the recyclerview with the query's response
    private fun initTestimonialRecyclerView(list: List<DataModel>) {
        binding.rvActivityTestimony.layoutManager = LinearLayoutManager(requireContext())
        binding.rvActivityTestimony.adapter = TestimonialsAdapter(list)
    }

    private fun testimonialsArrowClick(){
        binding.btnTestimonials.setOnClickListener{
        }
    }

    // Start and request the slides list to be used with the recycler view
    // Also, hide views depending on the state of the call
    private fun getSlides() {
        viewModel.onCreateSlides()
        viewModel.slidesCallFailed.observe(viewLifecycleOwner, Observer { failed ->
            if (failed) {
                binding.rvWelcomeActivityView.visibility = View.GONE
                binding.llErrorSlidesCall.visibility = View.VISIBLE
            } else {
                binding.rvWelcomeActivityView.visibility = View.VISIBLE
                binding.llErrorSlidesCall.visibility = View.GONE
                viewModel.slidesModel.observe(viewLifecycleOwner, Observer {
                    initWelcomeRecyclerView(it)
                })
            }
        }
        )
    }

    // Init the recyclerview with the query's response
    private fun initWelcomeRecyclerView(list: List<SlidesDataModel>) {
        //helper to snap cards in the center of the screen
        val snapHelper = LinearSnapHelper()
        if (list.isNotEmpty()) {
            binding.rvWelcomeActivityView.adapter = WelcomeActivitiesAdapter(list)
            snapHelper.attachToRecyclerView(binding.rvWelcomeActivityView)
        }
    }

    private fun onLoadError(message: String, retryCB: () -> Unit) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(resources.getString(R.string.retry)) { retryCB() }
            .show()
    }



// This function allows us to set up listeners
    private fun setUpListeners() {
        binding.btnRetrySlidesCall.setOnClickListener {
            viewModel.onCreateSlides()
        }
    }

                            // Error News

    private fun tryNews(viewModel: NewsViewModel, binding: FragmentHomeBinding) {
        val newsFragment = viewModel.newsList.value

        if(newsFragment == null ) {
            error()
        } else {
//             updateNews()
        }

    }

    fun error() {
        Toast.makeText(this, " Error!!, Ha ocurrido un error obteniendo la información...", Toast.LENGTH_SHORT).show()
    }

    private fun updateNews(newsRepository: MutableList<NewsRepository>, newsList: List<NewsRepository>,viewModel: NewsViewModel) {
        binding.btnReintentar.setOnClickListener(View.OnClickListener {
            newsRepository.clear()
            newsRepository.addAll(newsList)
        })
    }


}

