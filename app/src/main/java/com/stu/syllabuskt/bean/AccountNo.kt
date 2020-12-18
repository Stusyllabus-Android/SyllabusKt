package com.stu.syllabuskt.bean

/**
 * Create by yuan on 2020/12/18
 */

/**
 *  {"accountNo":75354,"balance":9932,"name":"包永祥"}
 */
data class AccountNo(val accountNo: Long, val balance: Double, val name: String) {
    override fun toString(): String {
        return "accountNo: $accountNo, balance: $balance, name: $name"
    }
}
