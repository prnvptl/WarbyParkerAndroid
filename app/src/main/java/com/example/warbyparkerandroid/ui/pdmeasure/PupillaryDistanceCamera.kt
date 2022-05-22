package com.example.warbyparkerandroid.ui.pdmeasure

import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.warbyparkerandroid.ui.common.BackPressHandler


@Composable
fun PupillaryDistanceCamera(
    modifier: Modifier = Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    onBack: () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    BackPressHandler(onBackPressed = { onBack() })
    Box() {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                val previewView = PreviewView(context).apply {
                    this.scaleType = scaleType
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    // Preview is incorrectly scaled in Compose on some devices without this
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                }

                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    // Preview
                    val preview = Preview.Builder()
                        .build()
                        .also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }

                    try {
                        // Must unbind the use-cases before rebinding them.
                        cameraProvider.unbindAll()

                        cameraProvider.bindToLifecycle(
                            lifecycleOwner, cameraSelector, preview
                        )
                    } catch (exc: Exception) {
                        Log.e("CameraPreview", "Use case binding failed", exc)
                    }
                }, ContextCompat.getMainExecutor(context))

                previewView
            })
        Text(
            "Look directly at the camera and place a card approximately size of a credit card under your nose and snap a pic!",
            modifier = Modifier
                .align(
                    Alignment.TopCenter
                )
                .padding(top = 20.dp)
                .padding(horizontal = 20.dp),
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.SemiBold
        )
        Box(modifier = Modifier.align(Alignment.Center)) {
            val stroke = Stroke(
                width = 2f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
            )
            Canvas(
                modifier = Modifier
                    .width(350.dp)
                    .height(500.dp)
                    .align(Alignment.Center),
                onDraw = {
                    drawOval(Color.White, style = stroke)
                })
            Canvas(
                modifier = Modifier
                    .width(200.dp)
                    .height(150.dp)
                    .padding(bottom = 50.dp)
                    .align(Alignment.BottomCenter),
                onDraw = {
                    drawRect(Color.White, style = stroke)
                })
        }
    }

}