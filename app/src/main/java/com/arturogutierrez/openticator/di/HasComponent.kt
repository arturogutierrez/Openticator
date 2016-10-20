package com.arturogutierrez.openticator.di

interface HasComponent<out T> {
    val component: T
}
