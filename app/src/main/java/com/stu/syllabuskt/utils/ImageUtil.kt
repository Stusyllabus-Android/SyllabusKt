package com.stu.syllabuskt.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import java.io.File

/**
 * Create by yuan on 2021/1/22
 */
object ImageUtil {

    /**
     * 读取本地相册的图片
     *io操作，须在子线程中调用
     */
    fun getLocalImage(context: Context): ArrayList<String>{
        var mImageList = ArrayList<String>()
        val mCursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
            arrayOf("image/jpeg", "image/png"),
            MediaStore.Images.Media.DATE_MODIFIED
        ) ?: return mImageList
        while (mCursor.moveToNext()) {
            val path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA)) //获取图片路径
            mImageList.add(path)
        }
        mCursor.close()
        return mImageList
    }

    fun decodeBitmapFromFile(context: Context, path: String): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        options.inSampleSize = computeScale(options, LayoutUtil.getScreenWidth(context) / 4, LayoutUtil.getScreenHeight(context) / 8)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(path, options)
    }

    /**
     * 根据View(主要是ImageView)的宽和高来计算Bitmap缩放比例。默认不缩放
     * @param options
     * @param width
     * @param height
     */
    private fun computeScale(options: BitmapFactory.Options, viewWidth: Int, viewHeight: Int): Int {
        var inSampleSize = 1
        if (viewWidth == 0 || viewWidth == 0) {
            return inSampleSize
        }
        val bitmapWidth = options.outWidth
        val bitmapHeight = options.outHeight

        //假如Bitmap的宽度或高度大于我们设定图片的View的宽高，则计算缩放比例
        if (bitmapWidth > viewWidth || bitmapHeight > viewWidth) {
            val widthScale = Math.round(bitmapWidth.toFloat() / viewWidth.toFloat())
            val heightScale = Math.round(bitmapHeight.toFloat() / viewWidth.toFloat())

            //为了保证图片不缩放变形，我们取宽高比例最小的那个
            inSampleSize = if (widthScale < heightScale) widthScale else heightScale
        }
        return inSampleSize
    }
}