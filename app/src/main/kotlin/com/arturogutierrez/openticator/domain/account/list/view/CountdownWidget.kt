package com.arturogutierrez.openticator.domain.account.list.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.helpers.use

class CountdownWidget : View, ValueAnimator.AnimatorUpdateListener {

  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val bounds = RectF(0f, 0f, width.toFloat(), height.toFloat())

  private var color: Int = 0
  private var angle: Int = 0
  private var valueAnimator: ValueAnimator? = null

  constructor(context: Context) : super(context) {
    initialize(context, null)
  }

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    initialize(context, attrs)
  }

  private fun initialize(context: Context, attrs: AttributeSet?) {
    if (attrs != null) {
      context.obtainStyledAttributes(attrs, R.styleable.CountdownWidget, 0, 0).use {
        color = getColor(R.styleable.CountdownWidget_arcColor, Color.BLACK)
      }
    }

    angle = 0
    paint.color = color
  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    bounds.set(0f, 0f, w.toFloat(), h.toFloat())
    super.onSizeChanged(w, h, oldw, oldh)
  }

  override fun onDraw(canvas: Canvas) {
    canvas.drawArc(bounds, 0f, angle.toFloat(), true, paint)
  }

  fun startAnimation(lengthInSeconds: Long, initialPercent: Float) {
    stopAnimation()

    val initialValue = (360 * Math.min(initialPercent, 1.0f)).toInt()
    valueAnimator = ValueAnimator.ofInt(-initialValue, 0).apply {
      duration = lengthInSeconds * 1000
      addUpdateListener(this@CountdownWidget)
      start()
    }
  }

  fun stopAnimation() {
    valueAnimator?.cancel()
  }

  override fun onAnimationUpdate(animation: ValueAnimator) {
    angle = animation.animatedValue as Int
    invalidate()
  }
}
