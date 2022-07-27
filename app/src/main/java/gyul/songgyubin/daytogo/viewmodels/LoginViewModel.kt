package gyul.songgyubin.daytogo.viewmodels

import android.app.Application
import android.os.Build.VERSION_CODES.P
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.regex.Pattern
import kotlin.math.log

class LoginViewModel(application: Application) : BaseViewModel(application) {

    private val _isLoginSuccess = MutableLiveData<Boolean>(false)
    private val _isValidEmail = MutableLiveData<Boolean>(false)
    private val _isValidPassword = MutableLiveData<Boolean>(false)

    val isValidEmail: LiveData<Boolean> get() = _isValidEmail
    val isValidPassword: LiveData<Boolean> get() = _isValidPassword
    val isLoginSuccess: LiveData<Boolean> get() = _isLoginSuccess

    val inputEmail: MutableLiveData<String> = MutableLiveData()
    val inputPassword: MutableLiveData<String> = MutableLiveData()


    // check email validation
    fun onEmailTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _isValidEmail.value = Patterns.EMAIL_ADDRESS.matcher(s).matches()
    }
    // check password validation
    fun onPasswordTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _isValidPassword.value =
            Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", s)
    }
}