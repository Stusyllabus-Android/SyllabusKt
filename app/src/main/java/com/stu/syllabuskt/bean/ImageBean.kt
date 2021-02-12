package com.stu.syllabuskt.bean

/**
 * Create by yuan on 2021/1/19
 */
data class ImageBean(val topImagePath: String, val folderName: String, val imageCounts: Int) {
    override fun toString(): String {
        return "topImagePath: $topImagePath, folderName: $folderName, imageCount: $imageCounts"
    }
}
