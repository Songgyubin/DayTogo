package gyul.songgyubin.daytogo.viewmodels

import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import gyul.songgyubin.daytogo.models.User
import gyul.songgyubin.daytogo.repositories.AuthRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import kotlin.math.log

class LoginViewModel : BaseViewModel() {

    private val authRepository by lazy { AuthRepository() }
    val clickSubject: PublishSubject<Unit> = PublishSubject.create()

    private val _isValidEmail = MutableLiveData<Boolean>(true)
    private val _isValidPassword = MutableLiveData<Boolean>(false)
    private val _errorMsg = MutableLiveData<String>()
    private val _authenticatedUser = MutableLiveData<User>()

    val isValidEmail: LiveData<Boolean> get() = _isValidEmail
    val isValidPassword: LiveData<Boolean> get() = _isValidPassword
    val authenticatedUser: LiveData<User> get() = _authenticatedUser
    val errorMsg: LiveData<String> get() = _errorMsg

    val inputEmail: MutableLiveData<String> = MutableLiveData()
    val inputPassword: MutableLiveData<String> = MutableLiveData()

    fun firebaseLoginEvent(view: View) {
        viewEvent(EVENT_FIREBASE_LOGIN)
    }

    fun kakaoLoginEvent(view: View) {
        viewEvent(EVENT_KAKAO_LOGIN)
    }

    fun firebaseLogin() {
        authRepository.firebaseLogin(inputEmail.value!!, inputPassword.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ authResult ->
                // DB 테이블 만들기
                authResult?.run {
                    val user = user?.let { User(uid = it.uid, email = inputEmail.value!!) }
                    _authenticatedUser.value = user
                    Log.d("TAG", "firebaseLogin: ${user?.email}")
                }
            }, { error ->
                _errorMsg.value = error.message
                Log.e("TAG", "firebaseLogin: ", error)
            }).addTo(disposable)
    }


    // check email validation
    fun onEmailTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isNotEmpty()) {
            _isValidEmail.value = Patterns.EMAIL_ADDRESS.matcher(s).matches()
        }
    }

    // check password validation
    fun onPasswordTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _isValidPassword.value = s.isNotEmpty()
    }

    companion object {
        const val EVENT_FIREBASE_LOGIN = 10000
        const val EVENT_KAKAO_LOGIN = 10001
    }
}