package gyul.songgyubin.daytogo.main.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.UiThread
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.base.view.BaseActivity
import gyul.songgyubin.daytogo.databinding.ActivityMainBinding
import gyul.songgyubin.daytogo.main.viewmodel.MainViewModel
import gyul.songgyubin.daytogo.repositories.MainRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), OnMapReadyCallback {
    private var backKeyPressedTime = 0L
    private lateinit var naverMap: NaverMap

    private val viewModel by viewModels<MainViewModel>(null, { viewModelFactory })
    private val repository by lazy { MainRepository() }
    private val viewModelFactory by lazy { MainViewModel.ViewModelFactory(repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNaverMap()
        loadSavedLocation()
        observeSavedLocation()
    }

    private fun initNaverMap() {
        val fm = supportFragmentManager
        val naverMapFragment = fm.findFragmentById(R.id.naver_map) as MapFragment
        naverMapFragment.getMapAsync(this)
    }

    // load User's place stored in db
    private fun loadSavedLocation() {
        viewModel.getSavedLocationList()
    }

    // observing saved Location
    // drawing marker with location's lat & lng
    // save locationInfo  to show information of saved location when clicked
    private fun observeSavedLocation() {
        viewModel.savedLocationList.observe(this) { stationList ->
            Observable.fromArray(*stationList.toTypedArray())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .filter {
                    it.latitude != 0.0 || it.longitude != 0.0
                }.map { location ->
                    viewModel.setSavedLocationInfo(location)
                    Marker().apply {
                        position = LatLng(location.latitude, location.longitude)
                        icon = OverlayImage.fromResource(R.drawable.check)
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { marker ->
                    marker.map = naverMap
                }.addTo(disposable)
        }
    }


    @UiThread
    override fun onMapReady(p0: NaverMap) {
        naverMap = p0

        p0.uiSettings.run {
            isScaleBarEnabled = false
            isZoomControlEnabled = false
            isLocationButtonEnabled = true
        }

        p0.setOnMapClickListener { pointF, latLng ->
            viewModel.selectLocation(latLng.latitude, latLng.longitude)
            Log.d(
                "TAG",
                "pointF: ${pointF.x} / ${pointF.y} \n latLng: ${latLng.latitude} / ${latLng.longitude}"
            )
        }
        p0.setOnSymbolClickListener { symbol ->
            viewModel.selectLocation(symbol.position.latitude, symbol.position.longitude)
            Log.d("TAG", "${symbol.position}")
            true
        }


    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            showShortToast(getString(R.string.notify_app_finish))
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