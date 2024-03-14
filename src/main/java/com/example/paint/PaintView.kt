package com.example.paint

import android.os.Vibrator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import com.example.paint.MainActivity.Companion.paintBrush
import com.example.paint.MainActivity.Companion.path
import kotlin.math.min
import kotlin.math.max

class PaintView : View {

    var params : ViewGroup.LayoutParams? = null

    companion object {
        var currentBrush = Color.BLACK
        var minX = 0
        var maxX = 2000
        var minY = 0
        var maxY = 1000

    }

    constructor(context: Context) : this(context, null) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        paintBrush.isAntiAlias = true
        paintBrush.color = currentBrush
        paintBrush.style = Paint.Style.STROKE
        paintBrush.strokeJoin = Paint.Join.ROUND
        paintBrush.strokeWidth = 8f

        params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var x = event.x.toInt()
        var y = event.y.toInt()

        minX = min(minX, x)
        maxX = max(maxX, x)
        minY = min(minY, y)
        maxY = max(maxY, y)
        performVibration()


        print(x)
        print(", ")
        println(y)

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x.toFloat(), y.toFloat())
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x.toFloat(), y.toFloat())
            }
            else -> return false
        }
        postInvalidate()
        return false
    }

    private fun performVibration() {
        // Gestion de la vibration
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(500)
        }
    }

    override fun onDraw(canvas: Canvas) {

        canvas.drawPath(path, paintBrush)
        invalidate()

    }

}