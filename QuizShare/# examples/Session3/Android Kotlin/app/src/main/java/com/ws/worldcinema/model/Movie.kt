package com.ws.worldcinema.model

import android.graphics.Color
import java.util.*

class Movie {
    var movieId = 0
    var name: String? = null
    var description: String? = null
    var age: String? = null
    var images: ArrayList<String>? = null
    var poster: String? = null

    fun getAgeInFormat(): String {
        return "$age+"
    }

    val ageColor: Int
        get() {
            when (age) {
                "0" -> {
                    return Color.WHITE
                }
                "6" -> {
                    return Color.parseColor("#FAD5C9")
                }
                "12" -> {
                    return Color.parseColor("#F4A992")
                }
                "16" -> {
                    return Color.parseColor("#F26E45")
                }
                "18" -> {
                    return Color.parseColor("#EF3A01")
                }
            }
            return Color.WHITE
        }

    companion object {
        const val MOVIE_ID_KEY = "movie_id_key"
    }
}