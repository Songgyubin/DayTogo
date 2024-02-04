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
        setSignInClick()
        setSignUpClick()
    }

    /**
     * 파이어베이스 회원가입
     */
    private fun setSignUpClick() {
        startOtherActivity(this@SignInActivity, SignUpActivity())
    }

    /**
     * 초기 세팅
     */
    private fun setUp() {
        binding.viewmodel = viewModel
        binding.activity = this
    }
    /**
     * 파이어베이스 로그인 클릭 이벤트
     */
    private fun setSignInClick() {
        binding.btnFirebaseLogin.setOnClickListener {
            validateAndProcessFirebaseLogin()
        }
    }

    /**
     * 유효한 사용자 정보가 있는지 검증하고, 조건에 따라 적절한 동작을 수행합니다.
     * - 이메일과 비밀번호가 모두 입력된 경우: 파이어베이스 로그인을 진행합니다.
     * - 이메일과 비밀번호가 모두 입력되지 않은 경우: 회원가입 화면으로 전환합니다.
     * - 이메일 또는 비밀번호 중 하나만 입력된 경우: 에러 메시지를 표시합니다.
     */
    private fun validateAndProcessFirebaseLogin() {
        with(viewModel) {
            when {
                inputEmail.isNotEmpty() && inputPassword.isNotEmpty() ->
                    firebaseLogin(inputEmail, inputPassword)
                inputEmail.isEmpty() && inputPassword.isEmpty() ->
                    startOtherActivity(this@SignInActivity, SignUpActivity())
                else ->
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
}