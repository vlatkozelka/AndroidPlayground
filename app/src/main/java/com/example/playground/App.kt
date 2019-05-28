package com.example.playground

import android.app.Application
import android.content.Context
import android.util.Log
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import org.notests.sharedsequence.api.ErrorReporting
import java.io.IOException
import java.net.SocketException

/**
 * Created by Ali on 5/29/2019.
 */
class App : Application() {
    companion object {
        lateinit var context: Context
    }

    var errorsDisposable: Disposable? = null

    override fun onCreate() {
        super.onCreate()
        context = this
        setupRxIgnoreUnderivableException()
        setupRxDriverErrorLogs()
    }


    private fun setupRxIgnoreUnderivableException() {
        RxJavaPlugins.setErrorHandler { e ->
            var cause: Throwable? = e
            if (e is UndeliverableException) {
                cause = e.cause
            }
            if (e is IOException || e is SocketException) {
                // fine, irrelevant network problem or API that throws on cancellation
            }
            if (e is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
            }
            if (e is NullPointerException || e is IllegalArgumentException) {
                // that's likely a bug in the application
                Thread.currentThread().uncaughtExceptionHandler
                    .uncaughtException(Thread.currentThread(), e)
            }
            if (e is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().uncaughtExceptionHandler
                    .uncaughtException(Thread.currentThread(), e)
            }
            Log.d("RxError", "Undeliverable exception received, not sure what to do $cause")
        }
    }


    private fun setupRxDriverErrorLogs() {
        errorsDisposable?.dispose()
        errorsDisposable = ErrorReporting.exceptions().subscribe { throwable ->
            //see joinToString source code
            val stackTrace = throwable.stackTrace.joinToString("\n", "", "", -1, "...", null)
            Log.wtf("DriverError", "errorReporting: $stackTrace")
        }
    }

}