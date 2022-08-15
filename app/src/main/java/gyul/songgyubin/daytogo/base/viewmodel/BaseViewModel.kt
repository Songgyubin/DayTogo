package gyul.songgyubin.daytogo.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gyul.songgyubin.daytogo.utils.SingleClickEvent
import gyul.songgyubin.daytogo.utils.SingleClickEventFlag
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel() : ViewModel() {
    protected val disposable = CompositeDisposable()

    private val _viewSingleEvent = MutableLiveData<SingleClickEvent<SingleClickEventFlag>>()
    val viewSingleEvent: LiveData<SingleClickEvent<SingleClickEventFlag>> get() = _viewSingleEvent

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    protected fun viewEvent(content: SingleClickEventFlag) {
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