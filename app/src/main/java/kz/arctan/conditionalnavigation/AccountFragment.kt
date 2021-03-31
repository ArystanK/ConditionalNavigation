package kz.arctan.conditionalnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kz.arctan.conditionalnavigation.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAuthenticationState()
    }

    private fun observeAuthenticationState() {
        viewModel.authenticationState.observe(viewLifecycleOwner, {
            if (it == LoginViewModel.AuthenticationState.AUTHENTICATED) {
                val hello = String.format(
                    resources.getString(
                        R.string.welcome_message,
                        FirebaseAuth.getInstance().currentUser?.displayName
                    )
                )

                binding.welcomeTextView.text = hello
                binding.logoutButton.setOnClickListener {
                    AuthUI.getInstance().signOut(requireContext())
                    findNavController().popBackStack()
                }
            }
        })
    }
}