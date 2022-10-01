package com.insanedev.quizshare.network


import com.insanedev.quizshare.common.AuthResult
import com.insanedev.quizshare.common.LoginResult
import com.insanedev.quizshare.common.RegisterResult
import com.insanedev.quizshare.network.models.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*


class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {

    override suspend fun tryLogin(email: String, password: String): LoginResult {
        try {
            val response = client.post {
                url(ApiRoutes.LOGIN)
                contentType(ContentType.Application.Json)
                setBody(LoginRequestRemote(email, password))
            }

            return when (response.status) {
                HttpStatusCode.BadRequest -> LoginResult.IncorrectCredentials
                HttpStatusCode.OK -> LoginResult.Ok(token = response.body<LoginResponseRemote>().token)
                else -> LoginResult.SomethingWentWrong
            }
        } catch (e: IOException) {
            return LoginResult.SomethingWentWrong
        }
    }

    override suspend fun tryRegister(
        email: String,
        password: String,
        name: String,
        secondName: String,
        patronymicName: String?
    ): RegisterResult {
        try {
        val response = client.post {
            url(ApiRoutes.REGISTER)
            contentType(ContentType.Application.Json)
            setBody(RegisterRequestRemote(email, name, secondName, patronymicName ?: "", password))
        }


        return when (response.status) {
                HttpStatusCode.BadRequest -> RegisterResult.EmailIsNotValid
                HttpStatusCode.Conflict -> RegisterResult.EmailAlreadyExists
                HttpStatusCode.OK -> RegisterResult.Ok(response.body<RegisterResponseRemote>().token)
                else -> RegisterResult.SomethingWentWrong
            }
        } catch (e: IOException) {
            return RegisterResult.SomethingWentWrong
        }
    }

    override suspend fun tryAuth(token: String): AuthResult {
        return try {
        val response = client.post {
            url(ApiRoutes.AUTH)
            contentType(ContentType.Application.Json)
            setBody(AuthRequestRemote(token))
        }


            when (response.status){
                HttpStatusCode.OK -> AuthResult.Ok(response.body<AuthResponseRemote>().email)
                else -> AuthResult.Err
            }
        } catch (e: IOException) {
            AuthResult.Err
        }
    }
}