package com.ws.worldcinema

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ws.worldcinema.databinding.ActivityLoginBinding
import com.ws.worldcinema.model.SignInResponse
import com.ws.worldcinema.model.UserSignIn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)
        binding!!.buttonLoginAction.setOnClickListener(View.OnClickListener {
            if (!isMailValid) {
                DialogManager.showErrorDialog(this@LoginActivity, "Ошибка ввода", "Некорректный email")
                return@OnClickListener
            }
            if (!isPasswordValid) {
                DialogManager.showErrorDialog(this@LoginActivity, "Ошибка ввода", "Необходимо ввести пароль")
                return@OnClickListener
            }
            val service = RetrofitSingleton().service
            val call = service!!.signIn(UserSignIn(binding!!.editTextTextEmailAddress.text.toString(),
                    binding!!.editTextTexPassword.text.toString()))
            call!!.enqueue(object : Callback<SignInResponse?> {
                override fun onResponse(call: Call<SignInResponse?>, response: Response<SignInResponse?>) {
                    if (response.body() != null) RetrofitSingleton.Companion.setToken(response.body()!!.token)
                    val intentMain = Intent(this@LoginActivity, MainActivity::class.java)
                }

                override fun onFailure(call: Call<SignInResponse?>, t: Throwable) {
                    DialogManager.showErrorDialog(this@LoginActivity, "Ошибка от сервера", t.localizedMessage)
                }
            })
        })
    }

    private val isMailValid: Boolean
        private get() {
            if (binding!!.editTextTextEmailAddress.text.toString().length != 0) return false
            val regExp = "[a-z0-9]+@[a-z0-9]+.[a-z]+"
            val pattern = Pattern.compile(regExp)
            val matcher = pattern.matcher(binding!!.editTextTextEmailAddress.text.toString())
            return !matcher.matches()
        }

    private val isPasswordValid: Boolean
        private get() = binding!!.editTextTexPassword.text.toString().length != 0
}