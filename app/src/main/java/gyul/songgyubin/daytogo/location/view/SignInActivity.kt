package gyul.songgyubin.daytogo.location.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.auth.view.LocationActivity
import gyul.songgyubin.daytogo.base.view.BaseActivity
import gyul.songgyubin.daytogo.databinding.ActivitySignInBinding
import gyul.songgyubin.daytogo.auth.viewmodel.AuthViewModel


@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()


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

    private fun observeFirebaseLogin() {
        viewModel.run {
            authenticatedUser.observe(this@SignInActivity) { user ->
                startOtherActivity(this@SignInActivity, LocationActivity())
                finish()
            }
            loginErrorMsg.observe(this@SignInActivity) { error ->
                showShortToast(getString(R.string.check_email_password))
            }
        }
    }

    fun startSignUp(view: View) {
        startOtherActivity(this@SignInActivity, SignUpActivity())
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}