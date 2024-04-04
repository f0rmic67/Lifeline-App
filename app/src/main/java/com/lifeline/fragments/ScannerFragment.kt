package com.lifeline.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.lifeline.OcrUtils
import com.lifeline.R


class ScannerFragment : Fragment() {
    private lateinit var captureButton: Button
    private lateinit var cameraController: LifecycleCameraController
    private lateinit var searchEditText: EditText

    companion object {
        private const val ARG_TOOLBAR_TITLE = "toolbarTitle"
        fun newInstance() = ScannerFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchEditText = requireView().findViewById(R.id.search_edittext)

        val backButton = requireView().findViewById<ImageButton>(R.id.searchBackButton)
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (!isGranted) {
                    val msg = "Permission to use camera denied"
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }
            }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        captureButton = view.findViewById(R.id.capture_button)

        captureButton.setOnClickListener {

            Toast.makeText(requireContext(), "Scanning For ID", Toast.LENGTH_SHORT).show()
            takePhoto()
        }

        startCamera()
    }

    private fun takePhoto() {
        cameraController.takePicture(
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    Log.d("Camera", "Capture Success")
                    OcrUtils.runTextRecognition(image, searchEditText)
                }
                override fun onError(exc: ImageCaptureException) {
                    Log.e("CAMERA", "Photo capture failed: ${exc.message}", exc)
                    parentFragmentManager.popBackStack()
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar?.let{
            it.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun startCamera() {
        val previewView = requireView().findViewById<PreviewView>(R.id.camera_preview)
        cameraController = LifecycleCameraController(requireContext())
        cameraController.bindToLifecycle(this)
        cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        previewView?.controller = cameraController
    }
}
