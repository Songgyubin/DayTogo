package gyul.songgyubin.daytogo.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<B : ViewDataBinding>(@LayoutRes val layoutId: Int):AppCompatActivity() {
    lateinit var binding :B
    protected val disposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,layoutId)
        binding.lifecycleOwner = this
    }
    protected fun showToast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
    protected fun startMainActivity(fromActivityContext: Context, toActivity:AppCompatActivity) {
        val intent = Intent(fromActivityContext, toActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}