package com.example.imagetextocr

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.google.mlkit.vision.text.Text

class TextOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val boxPaint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.STROKE
        strokeWidth = 4f
        alpha = 128
    }

    private val selectedBoxPaint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 4f
        alpha = 200
    }

    private val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = 32f
        setShadowLayer(4f, 0f, 0f, Color.BLACK)
    }

    private var textBlocks: List<Text.TextBlock> = emptyList()
    private var selectedBlockIndex: Int = -1
    private var onTextBlockSelectedListener: ((Text.TextBlock) -> Unit)? = null
    private var imageWidth: Int = 0
    private var imageHeight: Int = 0

    fun setTextBlocks(blocks: List<Text.TextBlock>) {
        textBlocks = blocks
        selectedBlockIndex = -1
        invalidate()
    }

    fun setOnTextBlockSelectedListener(listener: (Text.TextBlock) -> Unit) {
        onTextBlockSelectedListener = listener
    }

    fun setImageSize(width: Int, height: Int) {
        imageWidth = width
        imageHeight = height
        invalidate()
    }

    private fun mapRect(rect: android.graphics.Rect): RectF {
        val viewWidth = width.toFloat()
        val viewHeight = height.toFloat()
        if (imageWidth == 0 || imageHeight == 0) return RectF(rect)
        val scaleX = viewWidth / imageWidth
        val scaleY = viewHeight / imageHeight
        val scale = minOf(scaleX, scaleY)
        val dx = (viewWidth - imageWidth * scale) / 2
        val dy = (viewHeight - imageHeight * scale) / 2
        return RectF(
            rect.left * scale + dx,
            rect.top * scale + dy,
            rect.right * scale + dx,
            rect.bottom * scale + dy
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        textBlocks.forEachIndexed { index, block ->
            val rect = block.boundingBox
            if (rect != null) {
                val mappedRect = mapRect(rect)
                val paint = if (index == selectedBlockIndex) selectedBoxPaint else boxPaint
                canvas.drawRect(mappedRect, paint)
                canvas.drawText("${index + 1}", mappedRect.left, mappedRect.top - 10f, textPaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val x = event.x
            val y = event.y
            
            textBlocks.forEachIndexed { index, block ->
                val rect = block.boundingBox
                if (rect != null) {
                    val mappedRect = mapRect(rect)
                    if (mappedRect.contains(x, y)) {
                        selectedBlockIndex = index
                        onTextBlockSelectedListener?.invoke(block)
                        invalidate()
                        performClick()
                        return true
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
} 