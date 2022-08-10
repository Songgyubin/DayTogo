package gyul.songgyubin.daytogo.login.viewmodel

import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import gyul.songgyubin.daytogo.base.viewmodel.BaseViewModel
import gyul.songgyubin.daytogo.models.User
import gyul.songgyubin.daytogo.repositories.AuthRepository
import io.reactivex.rxkotlin.addTo

class LoginViewModel(private val authRepository: AuthRepository) : BaseViewModel() {
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    private val _isValidEmail = MutableLiveData<Boolean>(true)
    private val _errorMsg = MutableLiveData<String>()
    private val _authenticatedUser = MutableLiveData<User>()

    val isValidEmail: LiveData<Boolean> get() = _isValidEmail
    val authenticatedUser: LiveData<User> get() = _authenticatedUser

    var inputEmail: String = ""
    var inputPassword: String = ""


    fun firebaseLogin(inputEmail: String, inputPassword: String) {
        authRepository.firebaseLogin(auth, inputEmail, inputPassword)
            .subscribe({ authResult ->
                // DB 테이블 만들기
                authResult?.run {
                    val user = user?.let { User(uid = it.uid, email = inputEmail) }
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


    fun onClickFirebaseLoginEvent(view: View) {
        viewEvent(EVENT_FIREBASE_LOGIN)
    }
    fun onClickKakaoLoginEvent(view: View) {
        viewEvent(EVENT_KAKAO_LOGIN)
    }



    class ViewModelFactory(private val repository: AuthRepository)
        : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        const val EVENT_FIREBASE_LOGIN = 10000
        const val EVENT_KAKAO_LOGIN = 10001
    }
}