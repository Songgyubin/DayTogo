package gyul.songgyubin.daytogo.auth.viewmodel

import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import gyul.songgyubin.daytogo.base.viewmodel.BaseViewModel
import gyul.songgyubin.daytogo.models.User
import gyul.songgyubin.daytogo.repositories.AuthRepository
import gyul.songgyubin.daytogo.utils.SingleClickEventFlag
import io.reactivex.rxkotlin.addTo
import java.lang.Exception

class AuthViewModel(private val authRepository: AuthRepository) : BaseViewModel() {
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private val dbReference by lazy { Firebase.database.reference }

    private val _isValidEmail = MutableLiveData<Boolean>(true)
    private val _loginErrorMsg = MutableLiveData<String>()
    private val _dbErrorMsg = MutableLiveData<String>()

    private val _authenticatedUser = MutableLiveData<User>()

    val isValidEmail: LiveData<Boolean> get() = _isValidEmail
    val authenticatedUser: LiveData<User> get() = _authenticatedUser
    val loginErrorMsg: LiveData<String> get() = _loginErrorMsg
    val dbErrorMsg: LiveData<String> get() = _dbErrorMsg

    // two way binding
    var inputEmail: String = ""
    var inputPassword: String = ""

    fun firebaseLogin(inputEmail: String, inputPassword: String) {
        authRepository.firebaseLogin(auth, inputEmail, inputPassword)
            .subscribe({ user ->
                _authenticatedUser.value = user
            }, { error ->
                _loginErrorMsg.value = error.message
                Log.e("TAG", "firebaseLogin: ", error)
            }).addTo(disposable)
    }

    // sign up And firebase DB create
    // firebase DB root element is userEmail
    fun createUser(inputEmail: String, inputPassword: String) {
        authRepository.createUser(auth, inputEmail, inputPassword)
            .subscribe({ user ->
                _authenticatedUser.value = user
            },
                { error ->
                    _loginErrorMsg.value = error.message
                    Log.e("TAG", "createUserWithEmailAndPassword: ", error)
                }
            ).addTo(disposable)
    }

    fun createDB(user: User) {
        authRepository.createDB(dbReference, user)
            .subscribe {
                try {
                    Log.d("TAG", "createDB: ")
                } catch (e: Exception) {
                    Log.e("TAG", "createDB: ", e)
                }

            }.addTo(disposable)
    }


    // check email validation
    fun onEmailTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isNotEmpty()) {
            _isValidEmail.value = Patterns.EMAIL_ADDRESS.matcher(s).matches()
        }
    }

    // two way binding
    fun firebaseLoginSingleClickEvent(view: View) {
        viewEvent(SingleClickEventFlag.EVENT_FIREBASE_LOGIN)
    }

    fun kakaoLoginSingleClickEvent(view: View) {
        viewEvent(SingleClickEventFlag.EVENT_KAKAO_LOGIN)
    }

    fun signUpSingleClickEvent(view: View) {
        viewEvent(SingleClickEventFlag.SIGN_UP)
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


}