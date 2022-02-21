package lej.happy.musicapp.ui.custom

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import lej.happy.musicapp.R

class CustomMotionLayout(context: Context, attributes: AttributeSet? = null) :
    MotionLayout(context, attributes) {

    private val mainContainerView by lazy {
        findViewById<View>(R.id.view_background) // view 속성만 필요해서 view 로 받음
    }
    private val playerListView by lazy {
        findViewById<View>(R.id.fcv_player_list)
    }
    private val hitRect = Rect()


    override fun onTouchEvent(event: MotionEvent): Boolean {
        return super.onTouchEvent(event) && getValidSwipe(event)
    }

    private fun getValidSwipe(e1: MotionEvent) : Boolean {
        mainContainerView.getHitRect(hitRect)
        val inTouchRect = hitRect.contains(e1.x.toInt(), e1.y.toInt())
        playerListView.getHitRect(hitRect)
        val outTouchRect = !hitRect.contains(e1.x.toInt(), e1.y.toInt()) // 플레이어 리스트에는 터치 이벤트 안가도록 설정
       return inTouchRect && outTouchRect
    }

}