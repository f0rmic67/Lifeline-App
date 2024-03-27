package com.lifeline

import android.text.Editable
import android.util.Log
import android.widget.EditText
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.math.BigInteger

object OcrUtils {

    @OptIn(ExperimentalGetImage::class)
    fun runTextRecognition(imageProxy:ImageProxy, editText:EditText) {
        val mediaImage = imageProxy.image
        val image = mediaImage?.let { InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees) }?: return
        imageProxy.close()

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(image)
            .addOnSuccessListener { texts ->
                val processedText = processTextRecognitionResult(texts).toString()
                editText.text = Editable.Factory.getInstance().newEditable(processedText)
                Log.e("OCR", "Success")
            }
            .addOnFailureListener { e -> // Task failed with an exception
                Log.e("OCR", e.toString())
            }

    }
    private fun processTextRecognitionResult(texts: Text): BigInteger? {
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