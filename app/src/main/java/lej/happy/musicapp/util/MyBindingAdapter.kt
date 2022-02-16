package lej.happy.musicapp.util

import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.signature.ObjectKey
import lej.happy.musicapp.R
import java.io.File
import java.lang.Exception

object MyBindingAdapter {
    private val TAG = MyBindingAdapter::class.java.simpleName

    @androidx.databinding.BindingAdapter("url", "isOriginSize", requireAll = false)
    @JvmStatic
    fun setGlideImage(imageView: ImageView, url: String?, isOriginSize: Boolean?) {
        try{
            url?.let {
                Glide.with(imageView.context)
                    .load(it)
                    .override(if (isOriginSize == true) Target.SIZE_ORIGINAL else imageView.width)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .centerCrop()
                    .into(imageView)
            }
        } catch (e : Exception) {
            Log.e(TAG, "thumbnail error : $e")
        }
    }

    @androidx.databinding.BindingAdapter("bind:bindClipCircle")
    @JvmStatic
    fun bindClipCircle(imageView: ImageView, isClip: Boolean) {
        imageView.setBackgroundResource(R.drawable.rounded_rectangle)
        imageView.clipToOutline = true
    }

}