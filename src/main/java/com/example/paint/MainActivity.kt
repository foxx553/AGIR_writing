package com.example.paint

import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.paint.PaintView.Companion.maxX
import com.example.paint.PaintView.Companion.maxY
import com.example.paint.PaintView.Companion.minX
import com.example.paint.PaintView.Companion.minY

class MainActivity : ComponentActivity() {

    companion object {
        var path = Path()
        var paintBrush = Paint()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val whiteBtn = findViewById<ImageButton>(R.id.whiteColor)
        val finishBtn = findViewById<ImageButton>(R.id.finish)

        whiteBtn.setOnClickListener {
            path.reset()
        }

        finishBtn.setOnClickListener {
            // Square frame
            var maxDelta = 0
            var minDelta = 0
            if (maxX - minX >= maxY - minY) {
                maxDelta = maxX - minX
                minDelta = maxY - minY
                minY -= (maxDelta - minDelta) / 2
                maxY += (maxDelta - minDelta) / 2
            } else {
                maxDelta = maxY - minY
                minDelta = maxX - minX
                minX -= (maxDelta - minDelta) / 2
                maxX += (maxDelta - minDelta) / 2
            }
        }

    }
}