package kz.arctan.conditionalnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kz.arctan.conditionalnavigation.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentMainBinding

    fun observeAuthenticationState() {
        viewModel.authenticationState.observe(viewLifecycleOwner, {
            when (it) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    binding.button.setOnClickListener {
                        findNavController().navigate(R.id.accountFragment)
                    }
                }
                else -> {
                    binding.button.setOnClickListener {
                        findNavController().navigate(R.id.loginFragment)
                    }
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAuthenticationState()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }
}