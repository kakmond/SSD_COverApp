package com.example.mond.coverapp.presenter

import com.example.mond.coverapp.model.User
import java.util.*

class DealPresenter(val view: DealView,
                    val user: User) : Observer {

    override fun update(p0: Observable?, p1: Any?) {
        if (p0 == user) {
            if (p1 == null) {
                view.setDealList(user.dealList)
                view.setPostList(user.postList)
                view.setMoney(user.getBalance())
            }
        }
    }

    fun start() {
        user.addObserver(this)
        update(user, null)
    }
}