package gyul.songgyubin.daytogo.repositories

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryTest {
    @Mock
    private lateinit var context: Context

    private lateinit var firebaseApp: FirebaseApp

    @Mock
    private lateinit var auth: FirebaseAuth

    private lateinit var authRepository: AuthRepository

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    var instantTaskExecutorRule =
        InstantTaskExecutorRule() // 백그라운드 작업들을 같은 스레드에서 실행하여 테스트 결과를 동기적으로 실행되게 해줌

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        authRepository = AuthRepository(context)
    }

    @Test
    fun testFirebaseLogin() {
        val email = "thd0427__@naver.com"
        val password = "rbqls9307@"

//        // 파이어베이스 로그인 객체 생성.... auth;
        authRepository.firebaseLogin(auth, email, password).test()
            .assertSubscribed()
            .assertNoErrors()
//            .assertValue { authResult -> authResult.user == null }

        authRepository.firebaseLogin(auth, email, password).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                assertThat(it.user!!.email, true).equals("thd0427__@naver.com")
            }

    }

}