package com.melvin.ongandroid.view.home.fragments.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.android.material.snackbar.Snackbar
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentHomeBinding
import com.melvin.ongandroid.model.news.NewsModel
import com.melvin.ongandroid.model.slides.SlidesDataModel
import com.melvin.ongandroid.model.testimonials.DataModel
import com.melvin.ongandroid.view.home.adapters.news.NewsAdapter
import com.melvin.ongandroid.view.home.adapters.testimonials.TestimonialsAdapter
import com.melvin.ongandroid.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.melvin.ongandroid.view.home.adapters.welcome.WelcomeActivitiesAdapter
import javax.inject.Inject
import com.melvin.ongandroid.viewmodel.Errors
import com.melvin.ongandroid.viewmodel.Status


@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var firebaseAnalytics: FirebaseAnalytics
    
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
        getNews()
        lastNewsArrowClick()
        getSlides()
        setUpListeners()
        viewModel.apiStatus.observe(viewLifecycleOwner, Observer {
            if (it == Errors.ALL) {
                onLoadError(resources.getString(R.string.generalError)) { viewModel.refresh() }
            }
        })
    }

    private fun getNews() {
        viewModel.onLoadNews()
        viewModel.newsStatus.observe(viewLifecycleOwner) { status ->
            when (status!!) {
                Status.LOADING -> { }
                Status.SUCCESS -> {
                    viewModel.news.observe(viewLifecycleOwner, Observer {
                        initNewsRecyclerView(it)
                    })
                }
                Status.ERROR -> onLoadError(" ") {
                    viewModel.onLoadNews()
                }
                else -> { }
            }
        }
    }

    //This function start the testimonials query, an gives the response to the recyclerview
    private fun getTestimonials() {
        viewModel.onLoadTestimonials()
        viewModel.testimonialStatus.observe(viewLifecycleOwner) { status ->
            when (status!!) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    viewModel.testimonials.observe(viewLifecycleOwner, Observer {
                        initTestimonialRecyclerView(it)
                    })
                }
                Status.ERROR -> onLoadError(resources.getString(R.string.on_testimonials_loading_error)) {
                    viewModel.onLoadTestimonials()
                }
                Status.IDLE -> { }
            }
        }
    }

    //This function init the recyclerview with the query's response
    private fun initTestimonialRecyclerView(list: List<DataModel>) {
        binding.rvActivityTestimony.layoutManager = LinearLayoutManager(requireContext())
        binding.rvActivityTestimony.adapter = TestimonialsAdapter(list)
    }

    private fun initNewsRecyclerView(list: List<NewsModel>) {
        binding.rvLastNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLastNews.adapter = NewsAdapter(list.subList(0,5))
    }



    //This function listen the click on the testimonials see more button
    private fun testimonialsArrowClick() {
        binding.btnTestimonials.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("message", "Testimonials see more was pressed")
            firebaseAnalytics.logEvent("testimonies_see_more_pressed", bundle)

        }
    }
    //This function listen the click on the last news see more button
    private fun lastNewsArrowClick() {
        binding.btnLastNews.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("last_news", "Last News see more was pressed")
            firebaseAnalytics.logEvent("last_news_see_more_pressed", bundle)

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
        if (list.isNotEmpty()) {
            binding.rvWelcomeActivityView.adapter = WelcomeActivitiesAdapter(list)
            if (binding.rvWelcomeActivityView.onFlingListener == null){
                val snapHelper = LinearSnapHelper()
                snapHelper.attachToRecyclerView(binding.rvWelcomeActivityView)
            }
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
}

