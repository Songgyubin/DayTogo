package gyul.songgyubin.daytogo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gyul.songgyubin.daytogo.utils.Event
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel() : ViewModel() {
    protected val disposable = CompositeDisposable()

    private val _viewEvent = MutableLiveData<Event<Any>>()
    val viewEvent: LiveData<Event<Any>> get() = _viewEvent

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading


    protected fun viewEvent(content: Any){
        _viewEvent.value = Event(content)
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