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

class LocationInfoFragment : BaseFragment<FragmentLocationInfoBinding>(R.layout.fragment_location_info) {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}