package com.example.mond.coverapp.presenter

import com.example.mond.coverapp.model.Post


interface DealView {
    fun setDealList(posts: ArrayList<Post>)
    fun setPostList(posts: ArrayList<Post>)
    fun setMoney(money: Double)
}