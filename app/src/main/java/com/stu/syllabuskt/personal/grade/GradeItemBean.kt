package com.stu.syllabuskt.personal.grade

/**
 * Create by yuan on 2020/12/16
 */
data class GradeItemBean(val firstText: String, val secondText: String, val itemType: GradeItem) {
    override fun toString(): String {
        return "firstText: $firstText, secondText: $secondText, itemType: ${itemType.name}"
    }
}
