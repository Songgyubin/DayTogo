package gyul.songgyubin.daytogo.base.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes val layoutId: Int) :
    AppCompatActivity() {
    protected lateinit var binding: T
    protected val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
    }

    protected fun showLongToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    protected fun showShortToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    protected fun startOtherActivity(fromActivityContext: Context, toActivity: AppCompatActivity) {
        val intent = Intent(fromActivityContext, toActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}