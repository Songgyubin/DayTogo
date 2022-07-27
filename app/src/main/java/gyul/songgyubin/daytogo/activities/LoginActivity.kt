package gyul.songgyubin.daytogo.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.rx
import durdinapps.rxfirebase2.RxFirebaseAuth
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.databinding.ActivityLoginBinding
import gyul.songgyubin.daytogo.viewmodels.LoginViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.btnKakaoLogin.setOnClickListener { kakaoLogin() }
        binding.btnFirebaseLogin.setOnClickListener { firebaseLogin() }
        binding.viewmodel = viewModel

        checkEmailValidation()


    }

    private fun checkEmailValidation() {
        binding.tvWarningEmail.run {
            viewModel.isValidEmail.observe(this@LoginActivity) { isValidEmail ->
                if (isValidEmail) visibility = View.GONE
                else visibility = View.VISIBLE
            }
        }
    }

    private fun firebaseLogin() {

        viewModel.run {
            firebaseLogin()
            if (isValidEmail.value!! && isValidPassword.value!!) {
                isLoginSuccess.observe(this@LoginActivity) { isSuccess ->
                    if (isSuccess) {
                        startMainActivity()
                    } else {
                        showToast("이메일과 패스워드를 확인해주세요.")
                    }
                }
            } else {
                showToast("이메일과 패스워드를 입력해주세요.")
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
                    startMainActivity()
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

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}