package gyul.songgyubin.daytogo.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gyul.songgyubin.daytogo.utils.SingleClickEvent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel() : ViewModel() {
    protected val disposable = CompositeDisposable()

    private val _viewSingleEvent = MutableLiveData<SingleClickEvent<Int>>()
    val viewSingleEvent: LiveData<SingleClickEvent<Int>> get() = _viewSingleEvent

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    protected fun viewEvent(content: Int) {
        _viewSingleEvent.value = SingleClickEvent(content)
    }

    protected fun showProgress() {
        _isLoading.value = true
    }

    protected fun hideProgress() {
        _isLoading.value = false
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}