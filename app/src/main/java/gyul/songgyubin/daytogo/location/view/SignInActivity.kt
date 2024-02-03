package gyul.songgyubin.daytogo.location.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.auth.view.LocationActivity
import gyul.songgyubin.daytogo.auth.viewmodel.AuthViewModel
import gyul.songgyubin.daytogo.base.view.BaseActivity
import gyul.songgyubin.daytogo.databinding.ActivitySignInBinding
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
        setOnClickListener()

        collect()
    }

    /**
     * collect
     */
    private fun collect() {
        lifecycleScope.launchWhenStarted {
            launch { collectAuthenticatedUser() }
            launch { collectLoginErrorMsg() }
        }
    }

    /**
     * 클릭 리스너 세팅
     */
    private fun setOnClickListener() {
        setLoginClick()
    }

    /**
     * 파이어베이스 로그인
     */
    private fun setLoginClick() {
        binding.btnFirebaseLogin.setOnClickListener {
            callFirebaseLoginIfValidUserInfo()
        }
    }

    /**
     * 초기 세팅
     */
    private fun setUp() {
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

    /**
     * 인증된 유저 로그인 완료 여부 collect
     */
    private suspend fun collectAuthenticatedUser() {
        viewModel.authenticatedUser.collect {
            startOtherActivity(this@SignInActivity, LocationActivity())
            finish()
        }
    }

    /**
     * 로그인 에러 메시지 collect
     */
    private suspend fun collectLoginErrorMsg() {
        viewModel.loginErrorMsg.collect {
            showShortToast(getString(R.string.check_email_password))
        }
    }

    fun startSignUp(view: View) {
        startOtherActivity(this@SignInActivity, SignUpActivity())
    }
}