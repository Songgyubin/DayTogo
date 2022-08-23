package gyul.songgyubin.daytogo.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.UiThread
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import dagger.hilt.android.AndroidEntryPoint
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.base.view.BaseActivity
import gyul.songgyubin.data.repository.location.LocationRepositoryImpl
import gyul.songgyubin.daytogo.databinding.ActivityLocationBinding
import gyul.songgyubin.domain.usecase.AddLocationInfoUseCase
import gyul.songgyubin.domain.usecase.GetRemoteSavedLocationInfoUseCase
import gyul.songgyubin.daytogo.viewmodel.LocationViewModel

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers


@AndroidEntryPoint
class LocationActivity : BaseActivity<ActivityLocationBinding>(R.layout.activity_location), OnMapReadyCallback {
    private var backKeyPressedTime = 0L
    private lateinit var naverMap: NaverMap

    private val viewModel by viewModels<LocationViewModel>(null) { viewModelFactory }
    private val repository by lazy { LocationRepositoryImpl() }
    private val viewModelFactory by lazy { LocationViewModel.ViewModelFactory(
        AddLocationInfoUseCase(repository),
        GetRemoteSavedLocationInfoUseCase(repository)
    ) }

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
                .observeOn(Schedulers.computation())
                .filter {
                    // filtering empty title and description
                    it.title.isNotEmpty() || it.description.isNotEmpty()
                }.map { location ->
                    // 클릭 시 장소 정보를 보여주기 위해 locationId와 함께 locationInfo 저장
                    viewModel.setSavedLocationInfo(location)
                    // 저장된 위치에 마커 생성
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
        with(naverMap){
            uiSettings.run {
                isScaleBarEnabled = false
                isZoomControlEnabled = false
                isLocationButtonEnabled = true
            }
            setOnMapClickListener { pointF, latLng ->
                viewModel.selectLocation(latLng.latitude, latLng.longitude)
            }
            setOnSymbolClickListener { symbol ->
                viewModel.selectLocation(symbol.position.latitude, symbol.position.longitude)
                true
            }
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