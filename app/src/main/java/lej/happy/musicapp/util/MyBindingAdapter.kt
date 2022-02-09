package lej.happy.musicapp.util

import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import java.io.File
import java.lang.Exception

object MyBindingAdapter {
    private val TAG = MyBindingAdapter::class.java.simpleName

    @androidx.databinding.BindingAdapter("bind:glideCacheImage")
    @JvmStatic
    fun setGlideImage(imageView: ImageView, path: String?) {
        try{
            path?.let {
                Glide.with(imageView.context)
                    .load(it)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .centerCrop()
                    .into(imageView)
            }
        } catch (e : Exception) {
            Log.e(TAG, "thumbnail error : $e")
        }
    }
}