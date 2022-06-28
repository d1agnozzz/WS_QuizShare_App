package com.ws.worldcinema

object ImageHelper {
    fun getImagePath(localPath: String?): String {
        return "http://cinema.areas.su/up/images/$localPath"
    }
}