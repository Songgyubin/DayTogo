package gyul.songgyubin.daytogo.location.view

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.auth.viewmodel.AuthViewModel
import gyul.songgyubin.daytogo.base.view.BaseActivity
import gyul.songgyubin.daytogo.databinding.ActivitySignUpBinding
import gyul.songgyubin.domain.auth.model.UserEntity

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()
        setObserve()
    }

    private fun setView() {
        binding.viewmodel = viewModel
    }

    private fun setObserve() {
        observeCreateUser()
    }

    // create db after create user
    private fun observeCreateUser() {
        viewModel.run {
//            authenticatedUser.observe(this@SignUpActivity) { user ->
//                createDBWithUserEmail(user)
//                startOtherActivity(this@SignUpActivity, SignInActivity())
//            }
//            loginErrorMsg.observe(this@SignUpActivity) { error ->
//                showLongToast(getString(R.string.fail_sign_up))
//            }
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

    private fun createDBWithUserEmail(user: UserEntity) {
        viewModel.createUserInfoDB(user)
    }
}