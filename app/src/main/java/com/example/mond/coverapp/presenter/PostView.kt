package com.example.mond.coverapp.presenter

import com.example.mond.coverapp.model.Post

interface PostView {
    fun setPostList(books: ArrayList<Post>)
}