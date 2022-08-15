package gyul.songgyubin.daytogo.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gyul.songgyubin.daytogo.auth.viewmodel.AuthViewModel
import gyul.songgyubin.daytogo.base.viewmodel.BaseViewModel
import gyul.songgyubin.daytogo.repositories.AuthRepository
import gyul.songgyubin.daytogo.repositories.MainRepository

class MainViewModel(repository: MainRepository) :BaseViewModel(){










    class ViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}