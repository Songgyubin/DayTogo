package gyul.songgyubin.daytogo.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.viewmodel.AuthViewModel
import gyul.songgyubin.daytogo.base.view.BaseActivity
import gyul.songgyubin.daytogo.databinding.ActivitySignUpBinding
import gyul.songgyubin.domain.model.User
import gyul.songgyubin.data.repository.auth.AuthRepositoryImpl
import gyul.songgyubin.domain.usecase.FirebaseCreateUserInfoDbUseCase
import gyul.songgyubin.domain.usecase.FirebaseCreateUserUseCase
import gyul.songgyubin.domain.usecase.FirebaseLoginUseCase
import gyul.songgyubin.daytogo.utils.SingleClickEventFlag
import javax.inject.Inject

//TODO: two way binding으로 view 단 코드 감축
@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    @Inject
    lateinit var authRepository :AuthRepositoryImpl

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        ).get(AuthViewModel::class.java)
    }
    private val viewModelFactory by lazy {
        AuthViewModel.ViewModelFactory(
            FirebaseLoginUseCase(authRepository),
            FirebaseCreateUserUseCase(authRepository),
            FirebaseCreateUserInfoDbUseCase(authRepository)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()
        setObserve()
    }

    private fun setView() {
        binding.viewmodel = viewModel
    }

    private fun setObserve() {
        observeSingleClickEvent()
        observeCreateUser()
        observeEmailValidation()
    }

    // create db after create user
    private fun observeCreateUser() {
        viewModel.run {
            authenticatedUser.observe(this@SignUpActivity) { user ->
                createDBWithUserEmail(user)
                startOtherActivity(this@SignUpActivity, SignInActivity())
            }
            loginErrorMsg.observe(this@SignUpActivity) { error ->
                showLongToast(getString(R.string.fail_sign_up))
            }
        }
    }

    private fun observeSingleClickEvent() {
        viewModel.viewSingleEvent.observe(this@SignUpActivity) {
            it.getContentIfNotHandled().let { event ->
                when (event) {
                    SingleClickEventFlag.SIGN_UP -> callCreateUserWithEmailAndPassword()
                    else -> {
                        return@let
                    }
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
                createUser(inputEmail, inputPassword)
            } else {
                showLongToast(getString(R.string.enter_email_password))
            }
        }
    }

    private fun createDBWithUserEmail(user: User) {
        viewModel.createUserInfoDB(user)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}