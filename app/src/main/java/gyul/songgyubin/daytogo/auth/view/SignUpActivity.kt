package gyul.songgyubin.daytogo.auth.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.auth.viewmodel.AuthViewModel
import gyul.songgyubin.daytogo.base.view.BaseActivity
import gyul.songgyubin.daytogo.databinding.ActivitySignUpBinding
import gyul.songgyubin.daytogo.main.view.MainActivity
import gyul.songgyubin.daytogo.repositories.AuthRepository

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private lateinit var viewModel: AuthViewModel
    private lateinit var viewModelFactory: AuthViewModel.ViewModelFactory
    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()
        setObserve()

    }
    private fun setView(){
        authRepository = AuthRepository(this)
        viewModelFactory = AuthViewModel.ViewModelFactory(authRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
    }

    private fun setObserve(){
        observeSingleClickEvent()
        observeCreateUser()
        observeEmailValidation()
    }

    private fun observeCreateUser(){
        viewModel.run {
            authenticatedUser.observe(this@SignUpActivity) { user ->
                startOtherActivity(this@SignUpActivity, MainActivity())
            }
            errorMsg.observe(this@SignUpActivity) { error ->
                showToast("회원가입 실패")
            }
        }
    }

    private fun observeSingleClickEvent() {
        viewModel.viewSingleEvent.observe(this@SignUpActivity) {
            it.getContentIfNotHandled().let { event ->
                when (event) {
                    AuthViewModel.SIGN_UP -> callCreateUserWithEmailAndPassword()
                }
            }
        }
    }
    private fun observeEmailValidation() {
        binding.tvWarningEmail.run {
            viewModel.isValidEmail.observe(this@SignUpActivity) { isValidEmail ->
                if (isValidEmail) visibility = View.GONE
                else visibility = View.VISIBLE
            }
        }
    }

    private fun callCreateUserWithEmailAndPassword() {
        viewModel.run {
            if (inputEmail.isNotEmpty() && inputPassword.isNotEmpty()) {
                createUserWithEmailAndPassword(inputEmail, inputPassword)
            } else {
                showToast("이메일과 패스워드를 입력해주세요")
            }
        }
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}