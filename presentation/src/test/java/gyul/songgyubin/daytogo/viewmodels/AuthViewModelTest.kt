package gyul.songgyubin.daytogo.viewmodels

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import gyul.songgyubin.daytogo.R
import gyul.songgyubin.daytogo.auth.viewmodel.AuthViewModel
import gyul.songgyubin.daytogo.repositories.AuthRepository
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)

class AuthViewModelTest {
    private lateinit var viewModel: AuthViewModel
    private lateinit var authRepository: AuthRepository

    @Mock
    private lateinit var auth: FirebaseAuth
    @Mock
    private lateinit var context: Context

    @Before
    fun setUp() {
        authRepository = AuthRepository(context)

        viewModel = AuthViewModel(authRepository)
    }

    @Test
    fun readStringFromContext() {
        Mockito.`when`(context.getString(R.string.app_name)).thenReturn("FAKE_STRING")
        val result = context.getString(R.string.app_name)
        assertThat(result, true).equals("FAKE_STRING")
    }

    @Test
    fun testFirebaseLogin() {
//        viewModel.testFirebaseLogin("thd0427__@naver.com", "rbqls9307@").test().assertSubscribed()
//            .assertValue { authResult -> authResult.user != null }
//            .assertComplete()
//            .assertNoErrors()




    }
}