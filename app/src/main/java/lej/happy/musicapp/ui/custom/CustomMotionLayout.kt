package lej.happy.musicapp.ui.custom

import android.content.Context
import android.gesture.Gesture
import android.graphics.Rect
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import lej.happy.musicapp.R

class CustomMotionLayout(context: Context, attributes: AttributeSet? = null) :
    MotionLayout(context, attributes) {

    private var motionTouchStarted = false // 정확한 위치에서만 true
    private val mainContainerView by lazy {
        findViewById<View>(R.id.view_background) // view 속성만 필요해서 view 로 받음
    }
    private val playerListView by lazy {
        findViewById<View>(R.id.fcv_player_list)
    }
    private val hitRect = Rect()

    init {
        setTransitionListener(object : TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                // 트렌지션 리스너에서 스텐지션이 완료됬다면 모션 터치도 끝난 것으로 볼 수 있기 때매
                // 완료시 모션 터치 스타츠를 false 로
                motionTouchStarted = false // transitions 가 완료되고 나서는 해제
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
        })
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            // 터치 뗄 때 또는 취소 한 경우에는 Default 처리 (터치 즉, 누르고 있을 떄만 처리하겠다는 거)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                motionTouchStarted = false
                return super.onTouchEvent(event)
            }
        }

        if (!motionTouchStarted) {
            mainContainerView.getHitRect(hitRect)
            // 실제로 이벤트의 좌표가 mainContainerView 에 포함되는지 확인 (포함되면 true)
            motionTouchStarted = hitRect.contains(event.x.toInt(), event.y.toInt())
        }

        return super.onTouchEvent(event) && motionTouchStarted
    }

    private val gestureListener by lazy {
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                mainContainerView.getHitRect(hitRect)
                val inTouchRect = hitRect.contains(e1.x.toInt(), e1.y.toInt())
                playerListView.getHitRect(hitRect)
                val outTouchRect = !hitRect.contains(e1.x.toInt(), e1.y.toInt()) // 플레이어 리스트에는 터치 이벤트 안가도록 설정
                return inTouchRect && outTouchRect
            }
        }
    }

    private val gestureDetector by lazy {
        GestureDetector(context, gestureListener)
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }


}