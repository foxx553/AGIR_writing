package com.example.paint

import android.os.Vibrator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.os.VibrationEffect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import com.example.paint.MainActivity.Companion.paintBrush
import com.example.paint.MainActivity.Companion.path
import kotlin.math.min
import kotlin.math.max

class PaintView : View {

    var params : ViewGroup.LayoutParams? = null

    private var drawLetterA = false
    private var xOfA = 0f
    private var yOfA = 0f

    private var currentX =0
    private var currentY =0


    private lateinit var drawingBitmap: Bitmap


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
        paintBrush.color = Color.BLUE
        paintBrush.style = Paint.Style.STROKE
        paintBrush.strokeJoin = Paint.Join.ROUND
        paintBrush.strokeWidth = 8f

        params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onTouchEvent(event: MotionEvent): Boolean {
        var x = event.x.toInt()
        var y = event.y.toInt()

        minX = min(minX, x)
        maxX = max(maxX, x)
        minY = min(minY, y)
        maxY = max(maxY, y)


        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x.toFloat(), y.toFloat())
                xOfA = x.toFloat()
                yOfA = y.toFloat()
                drawLetterA = true
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x.toFloat(), y.toFloat())
                currentX=x
                currentY=y
                if (getColorAtTouch(x, y) == -1) {
                    performVibration()
                }

                print(getColorAtTouch(x,y))
                print("\n")
                print(x)
                print(", ")
                println(y)
            }
            else -> return false
        }
        postInvalidate()
        return false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun performVibration() {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // VibrationEffect.createOneShot() pour une seule vibration
        // VibrationEffect.DEFAULT_AMPLITUDE pour une amplitude par défaut
        val amplitudes = intArrayOf(0, 255, 0, 255) // Exemple d'intensités (0 à 255)
        val timings = longArrayOf(4, 15, 4, 15) // Durée en millisecondes pour chaque amplitude

        val vibrationEffect = VibrationEffect.createWaveform(timings, amplitudes, -1)
        // Vérifier la version d'Android avant de lancer la vibration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(vibrationEffect)
            print("je le fais quand meme")
        } else {
            // Versions d'Android antérieures à Oreo, utilisez simplement vibrate()
            vibrator.vibrate(50)
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (drawLetterA) {
            drawCapitalA(canvas, xOfA, yOfA)
        }

        // Dessiner sur le bitmap
        val bitmapCanvas = Canvas(drawingBitmap)
        //bitmapCanvas.drawPath(path, paintBrush)


        // Dessiner le chemin sur le canvas principal
        canvas.drawPath(path, paintBrush)

        invalidate()
    }

    private fun drawCapitalA(canvas: Canvas, x: Float, y: Float) {
        val width = 600f // Largeur de la lettre A
        val height = 1000f // Hauteur de la lettre A

        val defaultColor = paintBrush.color
        val defaultStrokeWidth = paintBrush.strokeWidth

        paintBrush.apply {
            color = Color.GRAY // Couleur grise
            strokeWidth = 90f // Largeur du pinceau plus large
        }

        val pathA = Path()


        // Dessiner la lettre A à la position spécifiée
        pathA.moveTo(x, y) // Déplacer le pinceau au point de départ
        pathA.lineTo(x + width / 2, y - height) // Dessiner la première ligne ascendante
        pathA.lineTo(x + width, y) // Dessiner la barre horizontale du milieu
        pathA.moveTo(x + width / 4, y - height / 2) // Déplacer le pinceau au milieu
        pathA.lineTo(x + 3 * width / 4, y - height / 2) // Dessiner la barre horizontale du milieu

        canvas.drawPath(pathA, paintBrush)
        drawingBitmap?.eraseColor(Color.WHITE) // Remplit le bitmap en blanc
        Canvas(drawingBitmap!!).drawPath(pathA, paintBrush)



        paintBrush.color = defaultColor
        paintBrush.strokeWidth = defaultStrokeWidth

        invalidate() // Mettre à jour la vue
    }

    private fun getColorAtTouch(x: Int, y: Int): Int {
        if (!::drawingBitmap.isInitialized) {
            return Color.BLACK
        }
        val bitmapX = x
        val bitmapY = y
        return drawingBitmap.getPixel(bitmapX, bitmapY)
    }


    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        drawingBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    }





}