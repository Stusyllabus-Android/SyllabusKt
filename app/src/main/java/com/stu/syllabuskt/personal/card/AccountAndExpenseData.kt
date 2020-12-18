package com.stu.syllabuskt.personal.card

import com.stu.syllabuskt.bean.AccountNo
import com.stu.syllabuskt.bean.ExpenseRecord

/**
 * Create by yuan on 2020/12/18
 */
data class AccountAndExpenseData(val accountNo: AccountNo, val expenseRecordList: List<ExpenseRecord>?)
