package gyul.songgyubin.daytogo.auth.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.base.view.BaseFragment
import gyul.songgyubin.daytogo.databinding.FragmentLocationInfoBinding
import gyul.songgyubin.daytogo.location.viewmodel.LocationViewModel
import gyul.songgyubin.daytogo.utils.toLatLng
import gyul.songgyubin.domain.location.model.LocationRequest

@AndroidEntryPoint
class LocationInfoFragment :
    BaseFragment<FragmentLocationInfoBinding>(R.layout.fragment_location_info) {
    private val viewModel: LocationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()

        collect()
    }

    /**
     * collect
     */
    private fun collect() {
        lifecycleScope.launchWhenStarted {
            collectSelectedLocationInfo()
        }
    }

    /**
     * 선택(클릭)된 장소의 정보와 사용자가 입력한 정보로
     * 새롭게 LocationInfo를 만들어
     * DB에 저장한다.
     */
    fun saveLocation(view: View) {
        with(viewModel) {
            selectedLocationId.value.let {
                if (it.isBlank()) {
                    return
                }
                val selectedLocationId = selectedLocationId.value
                val latLng = selectedLocationId.toLatLng()
                val locationRequest = LocationRequest(
                    locationId = selectedLocationId,
                    title = binding.edLocationTitle.text.toString(),
                    description = binding.edLocationDescription.text.toString(),
                    lat = latLng.latitude,
                    lon = latLng.longitude
                )
                savedLocationDB(locationRequest)
            }
        }
    }


    /**
     * setNaverMapOnClickListener로 생성한 locationId로
     * DB에서 꺼내와 저장한 savedLocationInfo를 가져옴
     * savedLocationId : HashMap <LoactionId, LocationInfo>
     */
    private suspend fun collectSelectedLocationInfo() {
        with(viewModel) {
            selectedLocationId.collect { locationId ->
                savedLocationEntity[locationId]?.run {
                    binding.edLocationTitle.setText(title)
                    binding.edLocationDescription.setText(description)
                }
            }
        }
    }

    private fun setView() {
        binding.fragment = this@LocationInfoFragment
        binding.viewModel = viewModel
    }
}