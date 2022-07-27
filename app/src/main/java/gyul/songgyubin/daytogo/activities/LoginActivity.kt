package gyul.songgyubin.daytogo.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.btnKakaoLogin.setOnClickListener { kakaoLogin() }
        binding.btnFirebaseLogin.setOnClickListener { firebaseLogin() }
        binding.viewmodel = viewModel
    }

    private fun firebaseLogin() {
        viewModel.run {
            if (!isValidEmail.value!!) showToast("이메일을 입력해주세요.")
            else if (!isValidPassword.value!!) showToast("패스워드를 입력해주세요.")
            else {
                RxFirebaseAuth.signInWithEmailAndPassword(
                    auth,
                    inputEmail.value!!,
                    inputPassword.value!!
                )
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { it.user != null }
                    .subscribe({ isSuccess ->
                        if (isSuccess) {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            showToast("아이디와 패스워드를 확인해주시길 바랍니다.")
                        }
                    }, { error ->
                        Log.e("TAG", "firebaseLogin: ", error)

                    }).addTo(disposable)
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
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
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