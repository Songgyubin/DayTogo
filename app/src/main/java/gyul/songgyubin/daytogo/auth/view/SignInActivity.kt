package gyul.songgyubin.daytogo.auth.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.rx
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.base.view.BaseActivity
import gyul.songgyubin.daytogo.main.view.MainActivity
import gyul.songgyubin.daytogo.auth.viewmodel.AuthViewModel
import gyul.songgyubin.daytogo.auth.viewmodel.AuthViewModel.Companion.EVENT_FIREBASE_LOGIN
import gyul.songgyubin.daytogo.databinding.ActivitySignInBinding
import gyul.songgyubin.daytogo.repositories.AuthRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    private lateinit var viewModel: AuthViewModel
    private lateinit var viewModelFactory: AuthViewModel.ViewModelFactory
    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setView()
        setObserveLoginViewModel()
    }

    private fun setView() {
        authRepository = AuthRepository()
        viewModelFactory = AuthViewModel.ViewModelFactory(authRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        binding.activity = this
    }

    private fun callFirebaseLoginIfValidUserInfo() {
        viewModel.run {
            if (inputEmail.isNotEmpty() && inputPassword.isNotEmpty()) {
                firebaseLogin(inputEmail, inputPassword)
            } else if (inputEmail.isEmpty() && inputPassword.isEmpty()) {
                Log.d("TAG", "callFirebaseLoginIfValidUserInfo:email: $inputEmail ")
                Log.d("TAG", "callFirebaseLoginIfValidUserInfo:password: $inputPassword ")
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
                    EVENT_FIREBASE_LOGIN -> callFirebaseLoginIfValidUserInfo()
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

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

}