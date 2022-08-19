package com.insanedev.quizshare.common

interface EventHandler<E> {
    fun obtainEvent(event: E)
}