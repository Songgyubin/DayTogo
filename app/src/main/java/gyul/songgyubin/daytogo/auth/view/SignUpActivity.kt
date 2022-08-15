package gyul.songgyubin.daytogo.auth.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import durdinapps.rxfirebase2.RxFirebaseAuth.createUserWithEmailAndPassword
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.auth.viewmodel.AuthViewModel
import gyul.songgyubin.daytogo.base.view.BaseActivity
import gyul.songgyubin.daytogo.databinding.ActivitySignUpBinding
import gyul.songgyubin.daytogo.models.User
import gyul.songgyubin.daytogo.repositories.AuthRepository
import gyul.songgyubin.daytogo.utils.SingleClickEventFlag

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val authRepository by lazy { AuthRepository() }
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        ).get(AuthViewModel::class.java)
    }
    private val viewModelFactory by lazy { AuthViewModel.ViewModelFactory(authRepository) }

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
                    else -> {return@let}
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

    /**
     *  uid
     *   L user_email
     *   L saved_location_list (Location(lat,lng, title, description))
     *
     */
    private fun createDBWithUserEmail(user: User) {
        viewModel.createDB(user)
    }
}