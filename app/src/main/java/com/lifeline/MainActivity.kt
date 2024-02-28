package com.lifeline

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.math.BigInteger


class MainActivity : AppCompatActivity() {
    lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
        textView.text = "Loading ID..."
        runTextRecognition()


    }
    private fun runTextRecognition() {
        val bitmap =  BitmapFactory.decodeResource(resources, R.drawable.id)
        val image = InputImage.fromBitmap(bitmap, 0)

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(image)
            .addOnSuccessListener { texts ->
                textView.text = processTextRecognitionResult(texts).toString()
                Log.e("MainActivity", "Success")
            }
            .addOnFailureListener { e -> // Task failed with an exception
                Log.e("MainActivity", e.toString())
            }
    }
    private fun processTextRecognitionResult(texts: Text):BigInteger? {
        // Get all text blocks
        for (block in texts.textBlocks) {
            // Get all lines in the text blocks
            for (line in block.lines) {
                // If the line starts with a digit and is 16 chars long, it's probably an ID
                if(line.text.first().isDigit() && line.text.length == 16){
                    return try {
                        line.text.toBigInteger()
                    } catch (e:Exception){
                        Log.e("MainActivity", e.toString())
                        null
                    }
                }
            }
        }

        return null
    }
}