package gyul.songgyubin.daytogo.main.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.rx
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.auth.viewmodel.AuthViewModel
import gyul.songgyubin.daytogo.base.view.BaseActivity
import gyul.songgyubin.daytogo.databinding.ActivityMainBinding
import gyul.songgyubin.daytogo.main.viewmodel.MainViewModel
import gyul.songgyubin.daytogo.repositories.AuthRepository
import gyul.songgyubin.daytogo.repositories.MainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), OnMapReadyCallback {
    private var backKeyPressedTime = 0L

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        ).get(MainViewModel::class.java)
    }
    private val repository by lazy { MainRepository() }
    private val viewModelFactory by lazy { MainViewModel.ViewModelFactory(repository) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    private fun initNaverMap() {

    }

    private fun setView() {

    }

    override fun onMapReady(p0: NaverMap) {
        p0.uiSettings.run {
            isScaleBarEnabled = false
            isZoomControlEnabled = false
            isLocationButtonEnabled = true
        }
        p0.setOnMapClickListener { pointF, latLng ->
            showShortToast("pointF: ${pointF.x} / ${pointF.y} \n latLng: ${latLng.latitude} / ${latLng.longitude}")

        }
        p0.setOnSymbolClickListener { symbol ->
            showShortToast(symbol.position.toString())
            true
        }


    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            showShortToast("한 번 더 누르면 종료됩니다.")
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish()
        }
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}