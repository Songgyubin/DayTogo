package gyul.songgyubin.daytogo.viewmodels

import android.app.Application
import android.content.Intent
import android.os.Build.VERSION_CODES.P
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import durdinapps.rxfirebase2.RxFirebaseAuth
import gyul.songgyubin.daytogo.activities.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.util.regex.Pattern
import kotlin.math.log

class LoginViewModel() : BaseViewModel() {
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private val _isLoginSuccess = MutableLiveData<Boolean>()
    private val _isValidEmail = MutableLiveData<Boolean>(true)
    private val _isValidPassword = MutableLiveData<Boolean>(false)
    private val _errorMsg = MutableLiveData<String>()

    val isValidEmail: LiveData<Boolean> get() = _isValidEmail
    val isValidPassword: LiveData<Boolean> get() = _isValidPassword
    val isLoginSuccess: LiveData<Boolean> get() = _isLoginSuccess
    val errorMsg: LiveData<String> get() = _errorMsg


    val inputEmail: MutableLiveData<String> = MutableLiveData("")
    val inputPassword: MutableLiveData<String> = MutableLiveData("")


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

    fun firebaseLogin() {
        RxFirebaseAuth.signInWithEmailAndPassword(
            auth,
            inputEmail.value!!,
            inputPassword.value!!
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.user != null }
            .subscribe({ isSuccess ->
                if (isSuccess) {
                    _isLoginSuccess.value = true
                } else {
                    _isLoginSuccess.value = false
                }
            }, { error ->
                _errorMsg.value = error.message
                Log.e("TAG", "firebaseLogin: ", error)

            }).addTo(disposable)
    }


}