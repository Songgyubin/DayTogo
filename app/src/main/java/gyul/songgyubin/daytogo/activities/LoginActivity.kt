package gyul.songgyubin.daytogo.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.rx
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.databinding.ActivityLoginBinding
import gyul.songgyubin.daytogo.viewmodels.LoginViewModel
import gyul.songgyubin.daytogo.viewmodels.LoginViewModel.Companion.EVENT_FIREBASE_LOGIN
import gyul.songgyubin.daytogo.viewmodels.LoginViewModel.Companion.EVENT_KAKAO_LOGIN
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewmodel = viewModel

        observeLoginViewModel()
        setUi()

    }

    private fun setUi() {
        viewModel.viewEvent.observe(this@LoginActivity) {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    EVENT_FIREBASE_LOGIN -> callFirebaseLoginIfValidUserInfo()
                    EVENT_KAKAO_LOGIN -> kakaoLogin()
                }
            }
        }
    }

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
                    startMainActivity(this@LoginActivity, MainActivity())
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


    // observing viewModel data
    private fun observeLoginViewModel(){
        observeEmailValidation()
        observeFirebaseLogin()
    }

    private fun observeEmailValidation() {
        binding.tvWarningEmail.run {
            viewModel.isValidEmail.observe(this@LoginActivity) { isValidEmail ->
                if (isValidEmail) visibility = View.GONE
                else visibility = View.VISIBLE
            }
        }
    }

    private fun observeFirebaseLogin() {
        viewModel.run {
            authenticatedUser.observe(this@LoginActivity) { user ->
                if (user != null) {
                    startMainActivity(this@LoginActivity, MainActivity())
                } else {
                    showToast("이메일과 패스워드를 확인해주세요.")
                }
            }
        }
    }
    private fun callFirebaseLoginIfValidUserInfo(){
        if (viewModel.inputEmail.value?.isNotEmpty() == true && viewModel.inputPassword.value?.isNotEmpty() == true) {
            viewModel.firebaseLogin()
        }else {
            showToast("이메일과 패스워드를 입력해주세요")
        }
    }


    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}