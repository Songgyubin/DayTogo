package gyul.songgyubin.daytogo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.rx
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.databinding.ActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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
                binding.test.text = user.kakaoAccount?.email
            }, { error ->
                Log.e("TAG", "사용자 정보 요청 실패", error)
            })
            .addTo(disposable)

    }
}