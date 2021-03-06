package com.koleychik.feature_start.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kaleichyk.utils.navigation.NavigationSystem
import com.koleychik.feature_start.StartFeatureNavigation
import com.koleychik.feature_start.databinding.FragmentStartBinding
import com.koleychik.feature_start.di.StartFeatureComponentHolder
import com.koleychik.feature_start.ui.viewModel.StartViewModel
import com.koleychik.feature_start.ui.viewModel.StartViewModelFactory
import com.koleychik.models.states.DataState
import javax.inject.Inject

class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    @Inject
    internal lateinit var viewModelFactory: StartViewModelFactory

    @Inject
    internal lateinit var navigation: StartFeatureNavigation

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[StartViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        NavigationSystem.onStartFeature?.let { start -> start(this) }
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        StartFeatureComponentHolder.getComponent().inject(this)
        subscribe()
        return binding.root
    }

    private fun subscribe() {
        viewModel.userState.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.WaitingForStart -> viewModel.checkUser()
                is DataState.Result<*> -> navigation.fromStartFragmentToMainScreen()
                is DataState.Error -> navigation.fromStartFragmentToAuthorization()
                else -> {
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        StartFeatureComponentHolder.reset()
    }

}