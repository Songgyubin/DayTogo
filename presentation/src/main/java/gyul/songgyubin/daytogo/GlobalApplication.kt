package gyul.songgyubin.daytogo

import android.app.Application
import com.naver.maps.map.NaverMapSdk
import dagger.hilt.android.HiltAndroidApp
import gyul.songgyubin.daytogo.BuildConfig

@HiltAndroidApp
class GlobalApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NaverMapClientId)
    }
}