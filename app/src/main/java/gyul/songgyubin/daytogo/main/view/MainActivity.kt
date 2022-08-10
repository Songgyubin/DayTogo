package gyul.songgyubin.daytogo.main.view

import android.os.Bundle
import android.util.Log
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.rx
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.base.view.BaseActivity
import gyul.songgyubin.daytogo.databinding.ActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private fun initNaverMap(){

    }

    private fun getKaKaoUser(){
        // 사용자 정보 요청 (기본)
        UserApiClient.rx.me()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                Log.i(
                    "TAG", "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}"
                )
            }, { error ->
                Log.e("TAG", "사용자 정보 요청 실패", error)
            })
            .addTo(disposable)
    }

    override fun onMapReady(p0: NaverMap) {
        p0.uiSettings.run {
            isScaleBarEnabled = false
            isZoomControlEnabled = false
            isLocationButtonEnabled = true

        }

    }

}