package com.stu.syllabuskt.bean

/**
 * Create by yuan on 2020/12/18
 */

/**
 *  {
 *    "accountNo": 75354,
 *    "time": "2020-12-11T12:05:03+08:00",
 *    "type": "持卡人消费",
 *    "collector": "G座新食堂",
 *    "balance": 1669,
 *    "cost": -460,
 *    "description": ""
 *  }
 */
data class ExpenseRecord(val accountNo: Long, val time: String, val type: String,
    val collector: String, val balance : Double, val cost: Double, val description: String) {
    override fun toString(): String {
        return "accountNo: $accountNo, time: $time, type: $type, collector: $collector, balance: $balance, cost: $cost, description: $description"
    }
}
