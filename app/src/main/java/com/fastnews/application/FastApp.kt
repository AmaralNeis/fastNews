package com.fastnews.application

import android.app.Application
import android.content.Context
import com.fastnews.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FastApp: Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        startKoin {
            androidLogger()
            androidContext(this@FastApp)
            modules(allModules)
        }
    }

    companion object {
       lateinit var context: Context
    }
}