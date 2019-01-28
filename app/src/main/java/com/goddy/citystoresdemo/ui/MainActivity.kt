package com.goddy.citystoresdemo.ui

import android.Manifest
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.goddy.citystoresdemo.R
import com.goddy.citystoresdemo.utils.ManagePermissions
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Initialize a list of required permissions to request runtime
        val list = listOf<String>(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        // Initialize a new instance of ManagePermissions class
        managePermissions = ManagePermissions(this, list, PermissionsRequestCode)

    }

    override fun onSaveInstanceState(oldInstanceState: Bundle) {
        super.onSaveInstanceState(oldInstanceState)
        oldInstanceState.clear()
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            managePermissions.checkPermissions()
    }

    // Receive the permissions request result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PermissionsRequestCode -> {
                val isPermissionsGranted = managePermissions
                        .processPermissionsResult(requestCode, permissions, grantResults)

                if (isPermissionsGranted) {
                    // Do the task now
                    Toast.makeText(this,"Permissions granted.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this,"Permissions denied.", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.e("THIS CODE TEST $requestCode")
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}
