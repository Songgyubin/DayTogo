package gyul.songgyubin.daytogo.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.base.view.BaseFragment
import gyul.songgyubin.daytogo.databinding.FragmentLocationInfoBinding
import gyul.songgyubin.daytogo.main.viewmodel.MainViewModel
import gyul.songgyubin.daytogo.models.LocationInfo
import gyul.songgyubin.daytogo.repositories.MainRepository
import gyul.songgyubin.daytogo.utils.toLatLng

class LocationInfoFragment :
    BaseFragment<FragmentLocationInfoBinding>(R.layout.fragment_location_info) {
    private val viewModel by activityViewModels<MainViewModel>(null, { viewModelFactory })
    private val repository by lazy { MainRepository() }
    private val viewModelFactory by lazy { MainViewModel.ViewModelFactory(repository) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeSelectedLocation()
        return super.onCreateView(inflater, container, savedInstanceState)
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

    private fun setView() {
        binding.fragment = this@LocationInfoFragment
    }

    fun finishEditLocation(view: View) {
        with(viewModel) {
            if (selectedLocationId.value != null) {
                val selectedLocationId = selectedLocationId.value!!
                val latLng = selectedLocationId.toLatLng()
                with(binding) {
                    val locationInfo = LocationInfo().also {
                        it.locationId = selectedLocationId
                        it.title = edLocationTitle.text.toString()
                        it.description = edLocationDescription.text.toString()
                        it.latitude = latLng.latitude
                        it.longitude = latLng.longitude
                    }
                    ibEdit.visibility = View.VISIBLE
                    ibSave.visibility = View.GONE
                    edLocationTitle.isEnabled = false
                    edLocationDescription.isEnabled = false
                    savedLocationDB(locationInfo)
                }
            } else {
                showShortToast(getString(R.string.retry))
            }
        }
    }

fun editLocation(view: View) {
    binding.ibEdit.visibility = View.GONE
    binding.ibSave.visibility = View.VISIBLE
    binding.edLocationTitle.isEnabled = true
    binding.edLocationDescription.isEnabled = true
}

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    setView()
    super.onViewCreated(view, savedInstanceState)
}


}