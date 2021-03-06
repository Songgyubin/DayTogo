package gyul.songgyubin.daytogo.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel() : ViewModel() {
    protected val disposable = CompositeDisposable()

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    protected fun showProgress(){
        _isLoading.value = true
    }
    protected fun hideProgress(){
        _isLoading.value = false
    }






    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}