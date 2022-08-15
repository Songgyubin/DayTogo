package gyul.songgyubin.daytogo.auth.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.auth.viewmodel.AuthViewModel
import gyul.songgyubin.daytogo.base.view.BaseActivity
import gyul.songgyubin.daytogo.databinding.ActivitySignInBinding
import gyul.songgyubin.daytogo.main.view.MainActivity
import gyul.songgyubin.daytogo.repositories.AuthRepository
import gyul.songgyubin.daytogo.utils.SingleClickEventFlag

class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        ).get(AuthViewModel::class.java)
    }
    private val viewModelFactory by lazy { AuthViewModel.ViewModelFactory(authRepository) }
    private val authRepository by lazy { AuthRepository() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setView()
        setObserveLoginViewModel()
    }

    private fun setView() {
        binding.viewmodel = viewModel
        binding.activity = this
    }

    // if email and password are empty
    // go to sign up
    private fun callFirebaseLoginIfValidUserInfo() {
        viewModel.run {
            if (inputEmail.isNotEmpty() && inputPassword.isNotEmpty()) {
                firebaseLogin(inputEmail, inputPassword)
            } else if (inputEmail.isEmpty() && inputPassword.isEmpty()) {
                startOtherActivity(this@SignInActivity, SignUpActivity())
            } else {
                showLongToast(getString(R.string.enter_email_password))
            }
        }
    }

    // observing viewModel data
    private fun setObserveLoginViewModel() {
        observeSingleClickEvent()
        observeEmailValidation()
        observeFirebaseLogin()

    }

    private fun observeSingleClickEvent() {
        viewModel.viewSingleEvent.observe(this@SignInActivity) {
            it.getContentIfNotHandled().let { event ->
                when (event) {
                    SingleClickEventFlag.EVENT_FIREBASE_LOGIN -> callFirebaseLoginIfValidUserInfo()
                    else -> {
                        return@let
                    }
                }
            }
        }
    }

    private fun observeEmailValidation() {
        binding.tvWarningEmail.run {
            viewModel.isValidEmail.observe(this@SignInActivity) { isValidEmail ->
                if (isValidEmail) visibility = View.GONE
                else visibility = View.VISIBLE
            }
        }
    }

    private fun observeFirebaseLogin() {
        viewModel.run {
            authenticatedUser.observe(this@SignInActivity) { user ->
                startOtherActivity(this@SignInActivity, MainActivity())
                finish()
            }
            loginErrorMsg.observe(this@SignInActivity) { error ->
                showShortToast(getString(R.string.check_email_password))
            }
        }
    }

    // two way binding
    fun startSignUp(view: View) {
        startOtherActivity(this@SignInActivity, SignUpActivity())
    }

}