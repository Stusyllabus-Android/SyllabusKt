package com.stu.syllabuskt.personal.card

/**
 * Create by yuan on 2020/12/18
 */
interface ICardContract {
    interface IView {
        fun showLoading()
        fun showData(accountAndExpenseData: AccountAndExpenseData)
        fun showErrMsg(msg: String)
    }
    interface IPresenter {
        fun init(type: Type, refreshListener: CardPresenter.RefreshListener?)
    }
}