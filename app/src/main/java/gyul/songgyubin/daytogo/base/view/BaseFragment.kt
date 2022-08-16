package gyul.songgyubin.daytogo.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<T : ViewDataBinding?>(@LayoutRes val layoutId: Int) :
    Fragment() {
    private var _binding: T? = null
    protected val binding: T get() = _binding!!
    private val disposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.lifecycleOwner = this

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        disposable.dispose()
        _binding = null
        super.onDestroyView()
    }

}