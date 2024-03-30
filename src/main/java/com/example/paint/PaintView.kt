package com.example.paint

import android.os.Vibrator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.media.MediaPlayer
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
import com.example.paint.MainActivity.Companion.pathLetter
import com.example.paint.MainActivity.Companion.selectedLetter
import kotlin.math.min
import kotlin.math.max

val letterHeight = 1000f //hauteur pour toutes les lettres
val letterWidth = 500f   //largeur pour toutes les lettres

class PaintView : View {

    var params : ViewGroup.LayoutParams? = null

    private var drawLetter = false
    private var xOfA = 0f
    private var yOfA = 0f

    private var currentX =0
    private var currentY =0





    private var defaultColor = paintBrush.color
    private var defaultStrokeWidth = paintBrush.strokeWidth


    private lateinit var drawingBitmap: Bitmap

    private var timer=0


    companion object {
        var currentBrush = Color.BLACK
        var minX = 50
        var maxX = 1000
        var minY = 50
        var maxY = 950

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
                if(MainActivity.isDone) {
                    drawLetter = true
                }
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
        val amplitudes = intArrayOf(255, 255, 0, 255) // Exemple d'intensités (0 à 255)
        val timings = longArrayOf(400, 15, 4, 15) // Durée en millisecondes pour chaque amplitude

        val vibrationEffect = VibrationEffect.createWaveform(timings, amplitudes, -1)
        // Vérifier la version d'Android avant de lancer la vibration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(vibrationEffect)
            print(drawingBitmap.width)
        } else {
            // Versions d'Android antérieures à Oreo, utilisez simplement vibrate()
            vibrator.vibrate(50)
        }
        timer++
        if(timer%100==0){
            playInstruction()
        }
    }

    override fun onDraw(canvas: Canvas) {

        if (drawLetter && MainActivity.isDone) {
            when(selectedLetter) {
                "A",null -> drawCapitalA(canvas, xOfA, yOfA)
                "B" -> drawCapitalB(canvas, xOfA, yOfA)
            }
            MainActivity.isDone=false
            drawLetter=false
        }


        // Dessiner sur le bitmap
        val bitmapCanvas = Canvas(drawingBitmap)
        //bitmapCanvas.drawPath(path, paintBrush)


        // Dessiner le chemin sur le canvas principal

        defaultColor = paintBrush.color
        defaultStrokeWidth = paintBrush.strokeWidth

        paintBrush.apply {
            color = Color.GRAY // Gray color
            strokeWidth = 90f // Thicker brush stroke
        }

        canvas.drawPath(pathLetter, paintBrush)

        paintBrush.color = defaultColor
        paintBrush.strokeWidth = defaultStrokeWidth



        canvas.drawPath(path, paintBrush)

        invalidate()
    }

    private fun drawCapitalA(canvas: Canvas, x: Float, y: Float) {

        val defaultColor = paintBrush.color
        val defaultStrokeWidth = paintBrush.strokeWidth

        paintBrush.apply {
            color = Color.GRAY // Couleur grise
            strokeWidth = 90f // Largeur du pinceau plus large
        }

        val pathA = Path()


        // Dessiner la lettre A à la position spécifiée
        pathA.moveTo(x, y) // Déplacer le pinceau au point de départ
        pathA.lineTo(x + letterWidth / 2, y - letterHeight) // Dessiner la première ligne ascendante
        pathA.lineTo(x + letterWidth, y) // Dessiner la barre horizontale du milieu
        pathA.moveTo(x + letterWidth / 4, y - letterHeight / 2) // Déplacer le pinceau au milieu
        pathA.lineTo(x + 3 * letterWidth / 4, y - letterHeight / 2) // Dessiner la barre horizontale du milieu

        canvas.drawPath(pathA, paintBrush)
        drawingBitmap?.eraseColor(Color.WHITE) // Fill the bitmap with white
        Canvas(drawingBitmap!!).drawPath(pathA, paintBrush)
        canvas.drawPath(pathA, paintBrush)

        pathLetter=pathA


        paintBrush.color = defaultColor
        paintBrush.strokeWidth = defaultStrokeWidth

        invalidate() // Mettre à jour la vue
    }

    private fun drawCapitalB(canvas: Canvas, x: Float, y: Float) {
        val radius = letterWidth / 2 // Radius for the round parts of B

        val defaultColor = paintBrush.color
        val defaultStrokeWidth = paintBrush.strokeWidth

        paintBrush.apply {
            color = Color.GRAY // Gray color
            strokeWidth = 90f // Thicker brush stroke
        }

        val pathB = Path()

        // Draw the vertical backbone of the B
        pathB.moveTo(x, y)
        pathB.lineTo(x, y - letterHeight)

        // Draw the top bubble of the B
        pathB.addArc(x- letterWidth-90, y- letterHeight/2, x + letterWidth, y , 270f, 180f)

        // Draw the bottom bubble of the B
        pathB.addArc(x- letterWidth-90, y- letterHeight, x + letterWidth, y- letterHeight/2 , 270f, 180f)

        drawingBitmap?.eraseColor(Color.WHITE) // Fill the bitmap with white
        Canvas(drawingBitmap!!).drawPath(pathB, paintBrush)
        canvas.drawPath(pathB, paintBrush)

        pathLetter=pathB


        paintBrush.color = defaultColor
        paintBrush.strokeWidth = defaultStrokeWidth

        invalidate() // Refresh the view
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

    fun playInstruction() {
        var xTemp=currentX
        var yTemp=currentY

        var distanceD=5000
        var distanceG=5000
        var distanceH=5000
        var distanceB=5000
        var min=5000

        var found = false
        while(xTemp<maxX && found==false){
            xTemp+=10
            if(drawingBitmap.getPixel(xTemp, yTemp)==-7829368){
                found=true
                distanceD=xTemp-currentX
                min=distanceD
            }

        }
        xTemp=currentX
        yTemp=currentY

        found = false
        while(xTemp>minX && found==false){
            if(drawingBitmap.getPixel(xTemp, yTemp)==-7829368){
                found=true
                distanceG=currentX-xTemp
                if(min>distanceG){
                    min=distanceG
                }
            }
            xTemp-=10
        }
        xTemp=currentX
        yTemp=currentY

        found = false
        while(yTemp<maxY && found==false){
            yTemp+=10
            if(drawingBitmap.getPixel(xTemp, yTemp)==-7829368){
                found=true
                distanceB=yTemp-currentY
                if(min>distanceB){
                    min=distanceB
                }
            }

        }

        xTemp=currentX
        yTemp=currentY

        found = false
        while(yTemp>minY && found==false){
            yTemp-=10
            if(drawingBitmap.getPixel(xTemp, yTemp)==-7829368){
                found=true
                distanceH=currentY-yTemp
                if(min>distanceH){
                    MainActivity.mediaPlayerH?.start()
                    return
                }
            }

        }

        if(distanceD==min){
            MainActivity.mediaPlayerD?.start()
        }
        if(distanceG==min){
            MainActivity.mediaPlayerG?.start()
        }
        if(distanceB==min){
            MainActivity.mediaPlayerB?.start()
        }
        return
    }






}