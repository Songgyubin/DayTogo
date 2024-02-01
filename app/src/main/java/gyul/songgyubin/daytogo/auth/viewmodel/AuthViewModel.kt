package gyul.songgyubin.daytogo.auth.viewmodel

import android.util.Patterns
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gyul.songgyubin.daytogo.utils.SingleClickEventFlag
import gyul.songgyubin.domain.auth.model.UserEntity
import gyul.songgyubin.domain.auth.usecase.FirebaseCreateUserUseCase
import gyul.songgyubin.domain.auth.usecase.FirebaseLoginUseCase
import gyul.songgyubin.domain.auth.usecase.SaveUserInfoDbUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseLoginUseCase: FirebaseLoginUseCase,
    private val firebaseCreateUserUseCase: FirebaseCreateUserUseCase,
    private val firebaseCreateUserInfoDbUseCase: SaveUserInfoDbUseCase
) : ViewModel() {

    private val _isValidEmail = MutableLiveData<Boolean>(true)
    private val _loginErrorMsg = MutableLiveData<String>()
    private val _dbErrorMsg = MutableLiveData<String>()

    private val _authenticatedUser = MutableLiveData<UserEntity>()

    val isValidEmail: LiveData<Boolean> get() = _isValidEmail
    val authenticatedUser: LiveData<UserEntity> get() = _authenticatedUser
    val loginErrorMsg: LiveData<String> get() = _loginErrorMsg
    val dbErrorMsg: LiveData<String> get() = _dbErrorMsg

    // two way binding
    var inputEmail: String = ""
    var inputPassword: String = ""

    fun firebaseLogin(inputEmail: String, inputPassword: String) {
        firebaseLoginUseCase(inputEmail, inputPassword)

    }

    // sign up And firebase DB create
    // firebase DB root element is userEmail
    fun createUser(inputEmail: String, inputPassword: String) {
        firebaseCreateUserUseCase(inputEmail, inputPassword)
            .onEach {
            }.launchIn(viewModelScope)
    }

    fun createUserInfoDB(user: UserEntity) {
        firebaseCreateUserInfoDbUseCase(user)
            .onEach { }
            .launchIn(viewModelScope)
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

    fun signUpSingleClickEvent(view: View) {
        viewEvent(SingleClickEventFlag.SIGN_UP)
    }

}