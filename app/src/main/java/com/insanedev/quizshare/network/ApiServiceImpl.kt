package com.insanedev.quizshare.network


import com.insanedev.quizshare.common.LoginResult
import com.insanedev.quizshare.common.RegisterResult
import com.insanedev.quizshare.network.models.LoginReceiveRemote
import com.insanedev.quizshare.network.models.RegisterReceiveRemote
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*


class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {

    override suspend fun tryLogin(email: String, password: String): LoginResult {
        val response = client.post {
            url(ApiRoutes.LOGIN)
            contentType(ContentType.Application.Json)
            setBody(LoginReceiveRemote(email, password))
        }
        val token = response.body<String>()

        return when (response.status) {
            HttpStatusCode.BadRequest -> LoginResult.IncorrectCredentials
            HttpStatusCode.OK -> LoginResult.Ok(token)
            else -> LoginResult.SomethingWentWrong
        }
    }

    override suspend fun tryRegister(
        email: String,
        password: String,
        name: String,
        secondName: String,
        patronymicName: String?
    ): RegisterResult {
        val response = client.post {
            url(ApiRoutes.REGISTER)
            contentType(ContentType.Application.Json)
            setBody(RegisterReceiveRemote(email, name, secondName, patronymicName ?: "", password))
        }
        val token = response.body<String>()


        return try{ when (response.status) {
            HttpStatusCode.BadRequest -> RegisterResult.EmailIsNotValid
            HttpStatusCode.Conflict -> RegisterResult.EmailAlreadyExists
            HttpStatusCode.OK -> RegisterResult.Ok(token)
            else -> RegisterResult.SomethingWentWrong
        }
        } catch (e: Exception) {
            RegisterResult.SomethingWentWrong
        }
//
//        return try {
//            RegisterResult.Ok(token)
//        } catch (ex: RedirectResponseException) {
//            // 3xx - responses
//            println("Error: ${ex.response.status.description}")
//            RegisterResult.SomethingWentWrong
//        } catch (ex: ClientRequestException) {
//            // 4xx - responses
//            println("Error: ${ex.response.status.description}")
//            RegisterResult.SomethingWentWrong
//        } catch (ex: ServerResponseException) {
//            // 5xx - response
//            println("Error: ${ex.response.status.description}")
//            RegisterResult.SomethingWentWrong
//        }
    }
}