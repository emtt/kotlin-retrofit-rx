package com.c20.restinkotlin

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.c20.restinkotlin.rest.RepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (checkInternetConnection()) {
            val repository = RepositoryProvider.provideSearchRepository()
            compositeDisposable.add(
                    repository.searchUsers("Lagos", "Java")
                            .observeOn(AndroidSchedulers.mainThread())
                            //observation on the main thread
                            //Now our subscriber!
                            .subscribeOn(Schedulers.io())
                            .subscribe({ result ->
                                //The result ->
                                Log.d("Result", "There are ${result.items.size} Java developers in Lagos")
                                Log.d("Result", "There are ${result.items}")

                            }, { error ->
                                error.printStackTrace()
                            })
            )
        }
    }

    private fun checkInternetConnection(): Boolean {
        val connectivityManager = this.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        val isDeviceConnected = networkInfo != null
                && networkInfo.isConnectedOrConnecting

        if (isDeviceConnected) {
            return true
        } else {
            createAlert()
            return false
        }
    }

    private fun createAlert() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this@MainActivity).create()
        alertDialog.setTitle("No connection")
        alertDialog.setMessage("PLEASE, CHECK FOR INTERNET CONNECTION")
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", { dialog: DialogInterface?, which: Int ->
            startActivity(Intent(Settings.ACTION_SETTINGS))
        })
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", { dialog: DialogInterface?, which: Int ->
            Toast.makeText(this@MainActivity, "You must be connected to the Internet", Toast.LENGTH_SHORT).show()
            finish()
        })
        alertDialog.show()

    }

    override fun onResume() {
        super.onResume()
        checkInternetConnection()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}
