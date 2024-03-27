package com.example.paint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
class LetterSelection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.letter_selection)

        val buttonA = findViewById<Button>(R.id.A) // Assuming you have buttons for each letter
        val buttonB = findViewById<Button>(R.id.B) // Example button B

        // Set OnClickListener for button A
        buttonA.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("selected_letter", "A") // Pass the selected letter as an extra parameter
            startActivity(intent)
            println("Ca marche")
        }

        // Set OnClickListener for button B
        buttonB.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("selected_letter", "B") // Pass the selected letter as an extra parameter
            startActivity(intent)
        }

        // Add OnClickListener for other buttons as needed
    }
}