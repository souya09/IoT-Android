package jp.ac.hec.cm0107.iot_android

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity

/**
 * オーバーレイ表示を開始／終了するためのトグルボタンを表示します。
 */
class OverlayShowActivity : AppCompatActivity() {
    companion object {
        /** ID for the runtime permission dialog */
        private const val OVERLAY_PERMISSION_REQUEST_CODE = 1
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.overlay_show_activity)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        requestOverlayPermission()

        // Show/hide overlay view with a toggle button.


        OverlayService.start(this@OverlayShowActivity)


    }

    /** Requests an overlay permission to the user if needed. */
    private fun requestOverlayPermission() {
        if (isOverlayGranted()) return
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )
        startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE)
    }

    /** Terminates the app if the user does not accept an overlay. */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!isOverlayGranted()) {
                finish()  // Cannot continue if not granted
            }
        }
    }

    /** Checks if the overlay is permitted. */
    private fun isOverlayGranted() =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(this)
}
