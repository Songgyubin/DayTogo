package gyul.songgyubin.daytogo.auth.viewmodel

import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.analytics.FirebaseAnalytics.Event.SIGN_UP
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import gyul.songgyubin.daytogo.base.viewmodel.BaseViewModel
import gyul.songgyubin.daytogo.models.User
import gyul.songgyubin.daytogo.repositories.AuthRepository
import io.reactivex.rxkotlin.addTo

class AuthViewModel(private val authRepository: AuthRepository) : BaseViewModel() {
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    private val _isValidEmail = MutableLiveData<Boolean>(true)
    private val _errorMsg = MutableLiveData<String>()
    private val _authenticatedUser = MutableLiveData<User>()

    val isValidEmail: LiveData<Boolean> get() = _isValidEmail
    val authenticatedUser: LiveData<User> get() = _authenticatedUser
    val errorMsg: LiveData<String> get() = _errorMsg

    // two way binding
    var inputEmail: String = ""
    var inputPassword: String = ""

    fun firebaseLogin(inputEmail: String, inputPassword: String) {
        authRepository.firebaseLogin(auth, inputEmail, inputPassword)
            .subscribe({ user ->
                _authenticatedUser.value = user
            }, { error ->
                _errorMsg.value = error.message
                Log.e("TAG", "firebaseLogin: ", error)
            }).addTo(disposable)
    }

    fun createUserWithEmailAndPassword(inputEmail: String, inputPassword: String) {
        authRepository.createUserWithEmailAndPassword(auth, inputEmail, inputPassword)
            .subscribe({ user ->
                _authenticatedUser.value = user
            },
                { error ->
                    _errorMsg.value = error.message
                    Log.e("TAG", "createUserWithEmailAndPassword: ",error )
                }
            ).addTo(disposable)
    }


    // check email validation
    fun onEmailTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isNotEmpty()) {
            _isValidEmail.value = Patterns.EMAIL_ADDRESS.matcher(s).matches()
        }
    }

    // two way binding
    fun firebaseLoginSingleClickEvent(view: View) {
        viewEvent(EVENT_FIREBASE_LOGIN)
    }

    fun kakaoLoginSingleClickEvent(view: View) {
        viewEvent(EVENT_KAKAO_LOGIN)
    }
    fun signUpSingleClickEvent(view:View){
        viewEvent(SIGN_UP)
    }


    class ViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AuthViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        const val EVENT_FIREBASE_LOGIN = 10000
        const val EVENT_KAKAO_LOGIN = 10001
        const val SIGN_UP = 10002
    }
}