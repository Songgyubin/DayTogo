package gyul.songgyubin.daytogo.auth.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gyul.songgyubin.daytogo.auth.model.UserUiModel
import gyul.songgyubin.daytogo.auth.model.mapper.toUiModel
import gyul.songgyubin.domain.auth.model.UserRequest
import gyul.songgyubin.domain.auth.usecase.FirebaseCreateUserUseCase
import gyul.songgyubin.domain.auth.usecase.FirebaseLoginUseCase
import gyul.songgyubin.domain.auth.usecase.SaveUserInfoDbUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseLoginUseCase: FirebaseLoginUseCase,
    private val firebaseCreateUserUseCase: FirebaseCreateUserUseCase,
    private val firebaseCreateUserInfoDbUseCase: SaveUserInfoDbUseCase
) : ViewModel() {

    private val _isValidEmail = MutableStateFlow(true)
    val isValidEmail: StateFlow<Boolean> get() = _isValidEmail

    private val _loginErrorMsg = MutableSharedFlow<String>()
    val loginErrorMsg: SharedFlow<String> get() = _loginErrorMsg.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _dbErrorMsg = MutableSharedFlow<String>()
    val dbErrorMsg: SharedFlow<String> get() = _dbErrorMsg.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _authenticatedUser = MutableStateFlow(UserUiModel())
    val authenticatedUser: StateFlow<UserUiModel> = _authenticatedUser.asStateFlow()

    private val _authenticatedUserRequest = MutableStateFlow(UserRequest(uid = null, email = null))
    val authenticatedUserRequest: StateFlow<UserRequest> = _authenticatedUserRequest.asStateFlow()

    var inputEmail: String = ""
    var inputPassword: String = ""

    /**
     * 파이어베이스 로그인
     */
    fun firebaseLogin(inputEmail: String, inputPassword: String) {
        firebaseLoginUseCase(inputEmail, inputPassword)
            .map { it.toUiModel() }
            .onEach {
                if (it.uid.isNotBlank()) {
                    _authenticatedUser.value = it
                }
            }
            .launchIn(viewModelScope)
    }

    /**
     * 유저 정보 생성
     */
    fun createUser(inputEmail: String, inputPassword: String) {
        firebaseCreateUserUseCase(inputEmail, inputPassword)
            .onEach {
                if (!it.uid.isNullOrBlank()) {
                    _authenticatedUser.value = it.toUiModel()
                    _authenticatedUserRequest.value = UserRequest(it.uid, it.email)
                }
            }
            .launchIn(viewModelScope)
    }

    /**
     * DB에 유저 정보 삽입
     */
    fun insertUserInfoDB(user: UserRequest) {
        firebaseCreateUserInfoDbUseCase(user)
            .onEach {
                if (it.isFailure) {
                    _dbErrorMsg.emit(it.exceptionOrNull()?.localizedMessage.orEmpty())
                }
            }
            .launchIn(viewModelScope)
    }

    // check email validation
    fun onEmailTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isNotEmpty()) {
            _isValidEmail.value = Patterns.EMAIL_ADDRESS.matcher(s).matches()
        }
    }
}