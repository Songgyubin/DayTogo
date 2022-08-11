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
import gyul.songgyubin.daytogo.databinding.ActivityAuthBinding
import gyul.songgyubin.daytogo.repositories.AuthRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

class AuthActivity : BaseActivity<ActivityAuthBinding>(R.layout.activity_auth) {
    private lateinit var viewModel: AuthViewModel
    private lateinit var viewModelFactory: AuthViewModel.ViewModelFactory
    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setView()
        setObserveLoginViewModel()

    }

    private fun setView() {
        authRepository = AuthRepository(this)
        viewModelFactory = AuthViewModel.ViewModelFactory(authRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
    }

    private fun callFirebaseLoginIfValidUserInfo() {
        viewModel.run {
            if (inputEmail.isNotEmpty() && inputPassword.isNotEmpty()) {
                firebaseLogin(inputEmail, inputPassword)
            } else {
                showToast("이메일과 패스워드를 입력해주세요")
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
        viewModel.viewSingleEvent.observe(this@AuthActivity) {
            it.getContentIfNotHandled().let { event ->
                when (event) {
                    EVENT_FIREBASE_LOGIN -> callFirebaseLoginIfValidUserInfo()
//                    EVENT_KAKAO_LOGIN -> kakaoLogin()
                }
            }
        }
    }

    private fun observeEmailValidation() {
        binding.tvWarningEmail.run {
            viewModel.isValidEmail.observe(this@AuthActivity) { isValidEmail ->
                if (isValidEmail) visibility = View.GONE
                else visibility = View.VISIBLE
            }
        }
    }

    private fun observeFirebaseLogin() {
        viewModel.run {
            authenticatedUser.observe(this@AuthActivity) { user ->
                startMainActivity(this@AuthActivity, MainActivity())
            }
            errorMsg.observe(this@AuthActivity){ error->
                showToast(error)
            }
        }
    }

    // TODO: check return and move to AuthRepository
    private fun kakaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.rx.loginWithKakaoTalk(this)
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext { error ->
                    Log.e("TAG", "kakaoLogin:${error.message} ")
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        Single.error(error)
                    } else {
                        UserApiClient.rx.loginWithKakaoAccount(this)
                    }
                }.observeOn(AndroidSchedulers.mainThread())
                .subscribe({ token ->
                    startMainActivity(this@AuthActivity, MainActivity())
                }, { error ->
                    Log.e("TAG", "로그인 실패", error)
                }).addTo(disposable)
        } else {
            UserApiClient.rx.loginWithKakaoAccount(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ token ->
                    Log.i("TAG", "로그인 성공 ${token.accessToken} ")
                }, { error ->
                    Log.e("TAG", "로그인 실패: ", error)
                }).addTo(disposable)
        }
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

}