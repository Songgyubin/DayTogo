package gyul.songgyubin.daytogo.location.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.UiThread
import androidx.lifecycle.lifecycleScope
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import dagger.hilt.android.AndroidEntryPoint
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.base.view.BaseActivity
import gyul.songgyubin.daytogo.databinding.ActivityLocationBinding
import gyul.songgyubin.daytogo.location.viewmodel.LocationViewModel
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LocationActivity : BaseActivity<ActivityLocationBinding>(R.layout.activity_location), OnMapReadyCallback {
    private var backKeyPressedTime = 0L
    private lateinit var naverMap: NaverMap

    private val viewModel: LocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNaverMap()

        collect()
        loadData()
    }

    /**
     * 데이터 load
     */
    private fun loadData() {
        loadSavedLocation()
    }

    /**
     * collect
     */
    private fun collect() {
        lifecycleScope.launchWhenStarted {
            launch { collectSavedLocation() }
        }
    }

    /**
     * 위치의 위도 및 경도 저장 위치 정보를 사용하여 저장된 위치 그리기
     */
    private suspend fun collectSavedLocation() {
        with(viewModel) {
            savedLocationList.collect { locationList ->
                locationList.filter { it.title.isNullOrBlank() || it.description.isNullOrBlank() }
                    .forEach { location ->
                        setSavedLocationInfo(location)
                        Marker().apply {
                            position = LatLng(location.lat ?: 0.0, location.lon ?: 0.0)
                            icon = OverlayImage.fromResource(R.drawable.check)
                            map = naverMap
                        }
                    }
            }
        }
    }

    /**
     * 맵 클릭 시 해당 장소의 위도,경도를 저장
     */
    private fun setNaverMapOnClickListener() {
        with(naverMap) {
            setOnMapClickListener { pointF, latLng ->
                viewModel.createLocationId(latLng.latitude, latLng.longitude)
            }
            setOnSymbolClickListener { symbol ->
                viewModel.createLocationId(symbol.position.latitude, symbol.position.longitude)
                true
            }
        }
    }

    /**
     * naver Map 초기화
     */
    private fun initNaverMap() {
        val fm = supportFragmentManager
        val naverMapFragment = fm.findFragmentById(R.id.naver_map) as MapFragment
        naverMapFragment.getMapAsync(this)
    }

    /**
     * 저장된 위치 정보 데이터 로드
     */
    private fun loadSavedLocation() {
        viewModel.fetchSavedLocationList()
    }

    // NaverMap 세팅
    @UiThread
    override fun onMapReady(p0: NaverMap) {
        naverMap = p0
        setNaverMapOnClickListener()
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
}