package jp.ac.hec.cm0107.iot_android

import android.util.Log

object MyLog {
    private const val TAG = "OverlaySample"
    fun i(msg: String) = Log.i(TAG, msg)
    fun w(msg: String) = Log.w(TAG, msg)
    fun e(msg: String) = Log.e(TAG, msg)
    fun v(msg: String) = Log.v(TAG, msg)
}
