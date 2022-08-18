package gyul.songgyubin.daytogo.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.presentation.base.view.BaseFragment
import gyul.songgyubin.daytogo.domain.models.LocationInfo
import gyul.songgyubin.daytogo.data.repository.location.LocationRepositoryImpl
import gyul.songgyubin.daytogo.databinding.FragmentLocationInfoBinding
import gyul.songgyubin.daytogo.domain.usecases.AddLocationInfoUseCase
import gyul.songgyubin.daytogo.domain.usecases.GetRemoteSavedLocationInfoUseCase
import gyul.songgyubin.daytogo.presentation.viewmodel.LocationViewModel
import gyul.songgyubin.daytogo.utils.LocationEditMode
import gyul.songgyubin.daytogo.utils.toLatLng

//TODO: two way binidng 으로 편집모드, title,description 수정
class LocationInfoFragment :
    BaseFragment<FragmentLocationInfoBinding>(R.layout.fragment_location_info) {
    private val viewModel by activityViewModels<LocationViewModel>(null) { viewModelFactory }
    private val repository by lazy { LocationRepositoryImpl() }
    private val viewModelFactory by lazy {
        LocationViewModel.ViewModelFactory(
            AddLocationInfoUseCase(repository),
            GetRemoteSavedLocationInfoUseCase(repository)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeSelectedLocation()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setView()
        super.onViewCreated(view, savedInstanceState)
    }

    fun saveLocation(view: View) {
        with(viewModel) {
            selectedLocationId.value?.let {
                val selectedLocationId = selectedLocationId.value!!
                val latLng = selectedLocationId.toLatLng()
                val locationInfo = LocationInfo().also {
                    it.locationId = selectedLocationId
                    it.title = binding.edLocationTitle.text.toString()
                    it.description = binding.edLocationDescription.text.toString()
                    it.latitude = latLng.latitude
                    it.longitude = latLng.longitude
                }
                changeViewMode(LocationEditMode.SAVE)
                savedLocationDB(locationInfo)
            }
        }
    }

    fun editLocation(view: View) {
        changeViewMode(LocationEditMode.EDIT)
    }
    private fun observeSelectedLocation() {
        with(viewModel) {
            selectedLocationId.observe(viewLifecycleOwner) { locationId ->
                savedLocationInfo[locationId]?.run {
                    binding.edLocationTitle.setText(title)
                    binding.edLocationDescription.setText(description)
                }
            }
        }
    }
    private fun changeViewMode(mode: LocationEditMode) {
        with(binding) {
            when (mode) {
                LocationEditMode.EDIT -> {
                    ibEdit.visibility = View.GONE
                    ibSave.visibility = View.VISIBLE
                    edLocationTitle.isEnabled = true
                    edLocationDescription.isEnabled = true
                }
                LocationEditMode.SAVE -> {
                    ibEdit.visibility = View.VISIBLE
                    ibSave.visibility = View.GONE
                    edLocationTitle.isEnabled = false
                    edLocationDescription.isEnabled = false
                }
            }
        }
    }
    private fun setView() {
        binding.fragment = this@LocationInfoFragment
    }
}