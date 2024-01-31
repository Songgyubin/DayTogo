package gyul.songgyubin.daytogo.auth.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.base.view.BaseFragment
import gyul.songgyubin.domain.location.model.LocationEntity
import gyul.songgyubin.daytogo.databinding.FragmentLocationInfoBinding
import gyul.songgyubin.daytogo.location.viewmodel.LocationViewModel
import gyul.songgyubin.daytogo.utils.toLatLng



@AndroidEntryPoint
class LocationInfoFragment :
    BaseFragment<FragmentLocationInfoBinding>(R.layout.fragment_location_info) {
    private val viewModel: LocationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        findSelectedLocationInfo()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setView()
        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * 선택(클릭)된 장소의 정보와 사용자가 입력한 정보로
     * 새롭게 LocationInfo를 만들어
     * DB에 저장한다.
     */
    fun saveLocation(view: View) {
        with(viewModel) {
            selectedLocationId.value?.let {
                val selectedLocationId = selectedLocationId.value!!
                val latLng = selectedLocationId.toLatLng()
                val locationEntity = LocationEntity(
                    locationId = selectedLocationId,
                    title = binding.edLocationTitle.text.toString(),
                    description = binding.edLocationDescription.text.toString(),
                    latitude = latLng.latitude,
                    longitude = latLng.longitude
                )
                savedLocationDB(locationEntity)
            }
        }
    }


    /**
     * setNaverMapOnClickListener로 생성한 locationId로
     * DB에서 꺼내와 저장한 savedLocationInfo를 가져옴
     * savedLocationId : HashMap <LoactionId, LocationInfo>
     */
    private fun findSelectedLocationInfo() {
        with(viewModel) {
            selectedLocationId.observe(viewLifecycleOwner) { locationId ->
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