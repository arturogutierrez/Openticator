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

class CountdownWidget : View, ValueAnimator.AnimatorUpdateListener {

  private lateinit var paint: Paint
  private lateinit var bounds: RectF

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
      val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountdownWidget, 0, 0)
      try {
        color = typedArray.getColor(R.styleable.CountdownWidget_arcColor, Color.BLACK)
      } finally {
        typedArray.recycle()
      }
    }

    angle = 0
    bounds = RectF(0f, 0f, width.toFloat(), height.toFloat())
    paint = Paint(Paint.ANTI_ALIAS_FLAG)
    paint.color = color
  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    bounds.set(0f, 0f, w.toFloat(), h.toFloat())
    super.onSizeChanged(w, h, oldw, oldh)
  }

  override fun onDraw(canvas: Canvas) {
    canvas.drawArc(bounds, 0f, angle.toFloat(), true, paint)
  }

  fun startAnimation(lengthInSeconds: Int, initialPercent: Float) {
    stopAnimation()

    val initialValue = (360 * Math.min(initialPercent, 1.0f)).toInt()
    valueAnimator = ValueAnimator.ofInt(initialValue, 0)
    valueAnimator!!.duration = (lengthInSeconds * 1000).toLong()
    valueAnimator!!.addUpdateListener(this)
    valueAnimator!!.start()
  }

  fun stopAnimation() {
    if (valueAnimator != null) {
      valueAnimator!!.cancel()
    }
  }

  override fun onAnimationUpdate(animation: ValueAnimator) {
    angle = animation.animatedValue as Int
    invalidate()
  }
}
