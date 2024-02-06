package gyul.songgyubin.daytogo.auth.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.auth.viewmodel.AuthViewModel
import gyul.songgyubin.daytogo.base.view.BaseActivity
import gyul.songgyubin.daytogo.databinding.ActivitySignUpBinding
import gyul.songgyubin.domain.auth.model.UserEntity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
        setOnClickListener()

        collect()
    }

    /**
     * 초기 세팅
     */
    private fun setUp() {
        binding.viewmodel = viewModel
    }

    /**
     * 온클릭 리스너 세팅
     */
    private fun setOnClickListener() {
        setSignUpClick()
    }

    /**
     * 회원가입 버튼 클릭 리스너 세팅
     */
    private fun setSignUpClick() {
        binding.btnSignUp.setOnClickListener {
            createUserWithEmailAndPassword()
        }
    }

    /**
     * collect
     */
    private fun collect() {
        lifecycleScope.launchWhenStarted {
            launch { collectCreatedUser() }
            launch { collectLoginErrorMsg() }
        }
    }

    /**
     * collect 생성된 유저 정보
     */
    private suspend fun collectCreatedUser() {
        viewModel.run {
            authenticatedUser.collect { user ->
                if (user.uid.isNullOrBlank()) {
                    return@collect
                }
                insertDBWithUserEmail(user)
                startOtherActivity(this@SignUpActivity, SignInActivity())
            }
        }
    }

    /**
     * collect 로그인 에러 메시지
     */
    private suspend fun collectLoginErrorMsg() {
        viewModel.loginErrorMsg.collect {
            showLongToast(getString(R.string.fail_sign_up))
        }
    }

    /**
     * 회원가입
     */
    private fun createUserWithEmailAndPassword() {
        viewModel.run {
            if (inputEmail.isNotEmpty() && inputPassword.isNotEmpty()) {
                createUser(inputEmail, inputPassword)
            } else {
                showLongToast(getString(R.string.enter_email_password))
            }
        }
    }

    /**
     * 유저 email DB 생성
     */
    private fun insertDBWithUserEmail(user: UserEntity) {
        viewModel.insertUserInfoDB(user)
    }
}