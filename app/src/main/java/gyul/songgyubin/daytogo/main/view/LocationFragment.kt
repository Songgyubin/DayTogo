package gyul.songgyubin.daytogo.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.base.view.BaseFragment
import gyul.songgyubin.daytogo.databinding.FragmentLocationBinding
import gyul.songgyubin.daytogo.main.viewmodel.MainViewModel
import gyul.songgyubin.daytogo.repositories.MainRepository

class LocationFragment : BaseFragment<FragmentLocationBinding>(R.layout.fragment_location) {
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        ).get(MainViewModel::class.java)
    }
    private val repository by lazy { MainRepository() }
    private val viewModelFactory by lazy { MainViewModel.ViewModelFactory(repository) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}