package com.melvin.ongandroid.model.login

import retrofit2.Response
import java.io.Serializable
import java.lang.Exception


data class GenericLogin <out T> (val status: Status, val data: T?, val message: String?) : Serializable {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?) : GenericLogin<T> {
            return GenericLogin(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(message: String?, data: T? = null) : GenericLogin<T> {
            return GenericLogin(
                Status.ERROR,
                data,
                message
            )
        }

        fun <T> loading(data: T? = null) : GenericLogin<T> {
            return GenericLogin(
                Status.LOADING,
                data,
                null
            )
        }

    }

    fun isSuccessful() = status == Status.SUCCESS
    fun isError() = status == Status.ERROR
    fun isLoading() = status == Status.LOADING

}

abstract class Result {

    suspend fun <T> getResult(call: suspend () -> Response<LoginModel<T>>) : GenericLogin<T> {

        try {
            val response = call()
            val body = response.body()?.data
                return GenericLogin.success(body)
        }
        catch (e: Exception) {
            return GenericLogin.error(e.message?: "Generic error")
        }
    }
}


