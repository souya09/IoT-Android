package jp.ac.hec.cm0107.iot_android

import android.content.Context
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout

class OverlayView @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(ctx, attrs, defStyle) {
    companion object {
        /** Creates an instance of [OverlayView]. */
        fun create(context: Context) =
            View.inflate(context, R.layout.overlay_view, null) as OverlayView
    }

    private val windowManager: WindowManager =
        ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    /** Settings for overlay view */
    private val layoutParams = WindowManager.LayoutParams(
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,  // Overlay レイヤに表示
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE.inv()// フォーカスを奪わない
                or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,  // 画面外への拡張を許可
        PixelFormat.TRANSLUCENT
    )

    /** Starts displaying this view as overlay. */
    @Synchronized
    fun show() {
        if (!this.isShown) {
            layoutParams.flags =
                layoutParams.flags or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            windowManager.addView(this, layoutParams)
        }
    }

    /** Hide this view. */
    @Synchronized
    fun hide() {
        if (this.isShown) {
            windowManager.removeView(this)
        }
    }
}
