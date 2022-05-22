package com.example.warbyparkerandroid.ui.virtualtryon

import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.warbyparkerandroid.R
import com.example.warbyparkerandroid.data.model.GlassStyle
import com.example.warbyparkerandroid.data.model.Glasses
import com.example.warbyparkerandroid.databinding.ActivityAugmentedFaceBinding
import com.example.warbyparkerandroid.ui.glassdetail.GlassDetail
import com.google.ar.core.AugmentedFace
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.ArSceneView
import com.google.ar.sceneform.Sceneform
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.Texture
import com.google.ar.sceneform.ux.ArFrontFacingFragment
import com.google.ar.sceneform.ux.AugmentedFaceNode
import java.util.concurrent.CompletableFuture

class AugmentedFaceActivity : AppCompatActivity() {
    private val loaders: MutableSet<CompletableFuture<*>> = HashSet()
    private var arFragment: ArFrontFacingFragment? = null
    private var arSceneView: ArSceneView? = null
    private var faceTexture: Texture? = null
    private var faceModel: ModelRenderable? = null
    private val facesNodes = HashMap<AugmentedFace, AugmentedFaceNode>()
    private var mViewBinding: ActivityAugmentedFaceBinding? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = DataBindingUtil.setContentView<ActivityAugmentedFaceBinding>(
            this,
            R.layout.activity_augmented_face
        )
        var glass: Glasses = intent.extras?.get("glass") as Glasses
        var style: GlassStyle = intent.extras?.get("glass_style") as GlassStyle

        mViewBinding?.apply {
            composeView.apply {
                if (style != null && glass != null) {
                    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
                    setContent {
                        GlassDetail(glass, style) { emulateBackPress() }
                    }
                }
            }
        }
        supportFragmentManager.addFragmentOnAttachListener { fragmentManager: FragmentManager, fragment: Fragment ->
            this.onAttachFragment(
                fragmentManager,
                fragment
            )
        }
        if (savedInstanceState == null) {
            if (Sceneform.isSupported(this)) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.arFragment, ArFrontFacingFragment::class.java, null)
                    .commit()
            }
        }
        loadModels()
    }

    private fun emulateBackPress() {
        this.dispatchKeyEvent(
            KeyEvent(
                KeyEvent.ACTION_DOWN,
                KeyEvent.KEYCODE_BACK
            )
        )
        this.dispatchKeyEvent(
            KeyEvent(
                KeyEvent.ACTION_UP,
                KeyEvent.KEYCODE_BACK
            )
        )
        finish()
    }

    private fun onAttachFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        if (fragment.id == R.id.arFragment) {
            arFragment = fragment as ArFrontFacingFragment
            arFragment!!.setOnViewCreatedListener { arSceneView: ArSceneView ->
                onViewCreated(
                    arSceneView
                )
            }
        }
    }

    private fun onViewCreated(arSceneView: ArSceneView) {
        this.arSceneView = arSceneView

        // This is important to make sure that the camera stream renders first so that
        // the face mesh occlusion works correctly.
        arSceneView.setCameraStreamRenderPriority(Renderable.RENDER_PRIORITY_FIRST)

        // Check for face detections
        arFragment!!.setOnAugmentedFaceUpdateListener { augmentedFace: AugmentedFace ->
            onAugmentedFaceTrackingUpdate(
                augmentedFace
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        for (loader in loaders) {
            if (!loader.isDone) {
                loader.cancel(true)
            }
        }
        mViewBinding = null
    }

    private fun loadModels() {
        loaders.add(ModelRenderable.builder()
            .setSource(this, Uri.parse("models/yellow-glasses.glb"))
            .setIsFilamentGltf(true)
            .build()
            .thenAccept { model: ModelRenderable? -> faceModel = model }
            .exceptionally { throwable: Throwable? ->
                Toast.makeText(this, "Unable to load renderable", Toast.LENGTH_LONG).show()
                null
            })
    }

    private fun loadTextures() {
        loaders.add(
            Texture.builder()
                .setSource(this, Uri.parse("textures/freckles.png"))
                .setUsage(Texture.Usage.COLOR_MAP)
                .build()
                .thenAccept { texture: Texture? -> faceTexture = null }
                .exceptionally { throwable: Throwable? ->
                    Toast.makeText(this, "Unable to load texture", Toast.LENGTH_LONG).show()
                    null
                })
    }

    private fun onAugmentedFaceTrackingUpdate(augmentedFace: AugmentedFace) {
        if (faceModel == null) {
            return
        }
        val existingFaceNode = facesNodes[augmentedFace]
        when (augmentedFace.trackingState) {
            TrackingState.TRACKING -> if (existingFaceNode == null) {
                val faceNode = AugmentedFaceNode(augmentedFace)
                val modelInstance = faceNode.setFaceRegionsRenderable(faceModel)
                modelInstance.isShadowCaster = false
                modelInstance.isShadowReceiver = true
                faceNode.faceMeshTexture = faceTexture
                arSceneView!!.scene.addChild(faceNode)
                facesNodes[augmentedFace] = faceNode
            }
            TrackingState.STOPPED -> {
                if (existingFaceNode != null) {
                    arSceneView!!.scene.removeChild(existingFaceNode)
                }
                facesNodes.remove(augmentedFace)
            }
        }
    }
}