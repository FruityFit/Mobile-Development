package com.adamcoding.prototype.ui.classificator

import android.app.Activity
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.TensorOperator
import org.tensorflow.lite.support.common.TensorProcessor
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class FreshImageClassifier internal constructor(private val context: Activity) {
    private var imageSizeX = 0
    private var imageSizeY = 0
    private var labels: List<String>? = null
    private var tflite: Interpreter? = null
    private var inputImageBuffer: TensorImage? = null
    private var outputProbabilityBuffer: TensorBuffer? = null
    private var probabilityProcessor: TensorProcessor? = null

    init {
        try {
            val opt = Interpreter.Options()
            tflite = Interpreter(loadModelfile(context), opt)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadImage(bitmap: Bitmap): TensorImage {
        inputImageBuffer!!.load(bitmap)

        val cropSize = bitmap.width.coerceAtMost(bitmap.height)
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeWithCropOrPadOp(cropSize, cropSize))
            .add(ResizeOp(imageSizeX, imageSizeY, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(preprocessNormalizeOp)
            .build()
        return imageProcessor.process(inputImageBuffer)
    }

    @Throws(IOException::class)
    private fun loadModelfile(activity: Activity): MappedByteBuffer {
        val fileDescriptor = activity.assets.openFd(MODEL_NAME)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startoffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startoffset, declaredLength)
    }

    fun classifyImage(bitmap: Bitmap) {
        val imageTensorIndex = 0
        val imageShape = tflite!!.getInputTensor(imageTensorIndex).shape() // {1, height, width, 3}
        imageSizeY = imageShape[1]
        imageSizeX = imageShape[2]
        val imageDataType = tflite!!.getInputTensor(imageTensorIndex).dataType()
        val probabilityTensorIndex = 0
        val probabilityShape =
            tflite!!.getOutputTensor(probabilityTensorIndex).shape() // {1, NUM_CLASSES}
        val probabilityDataType = tflite!!.getOutputTensor(probabilityTensorIndex).dataType()
        inputImageBuffer = TensorImage(imageDataType)
        outputProbabilityBuffer =
            TensorBuffer.createFixedSize(probabilityShape, probabilityDataType)
        probabilityProcessor = TensorProcessor.Builder().add(postProcessNormalizeOp).build()
        inputImageBuffer = loadImage(bitmap)
        tflite!!.run(inputImageBuffer!!.buffer, outputProbabilityBuffer!!.buffer.rewind())
    }

    private val preprocessNormalizeOp: TensorOperator
        get() = NormalizeOp(IMAGE_MEAN, IMAGE_STD)

    fun showResult(): String? {
        labels = try {
            FileUtil.loadLabels(context, LABEL_NAME)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        val result = outputProbabilityBuffer!!.floatArray[0]
        return if (result == 0.0f) "Fresh" else "Rotten"
    }

    private val postProcessNormalizeOp: TensorOperator
        get() = NormalizeOp(
            PROBABILITY_MEAN,
            PROBABILITY_STD
        )

    companion object {
        private const val IMAGE_MEAN = 0.0f
        private const val IMAGE_STD = 1.0f
        private const val PROBABILITY_MEAN = 0.0f
        private const val PROBABILITY_STD = 255.0f
        private const val MODEL_NAME = "fresh_model.tflite"
        private const val LABEL_NAME = "fresh_labels.txt"
    }
}