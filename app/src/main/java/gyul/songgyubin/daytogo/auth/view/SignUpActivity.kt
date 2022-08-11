package gyul.songgyubin.daytogo.auth.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.auth.viewmodel.AuthViewModel
import gyul.songgyubin.daytogo.base.view.BaseActivity
import gyul.songgyubin.daytogo.databinding.ActivitySignUpBinding
import gyul.songgyubin.daytogo.repositories.AuthRepository

class SignUpActivity :BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up){
    private lateinit var viewModel: AuthViewModel
    private lateinit var viewModelFactory: AuthViewModel.ViewModelFactory
    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authRepository = AuthRepository(this)
        viewModelFactory = AuthViewModel.ViewModelFactory(authRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}