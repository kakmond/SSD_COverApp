package com.example.mond.coverapp.model

import java.util.*

abstract class PostRepository : Observable() {
    var postList = ArrayList<Post>()

    abstract fun loadAllPosts()
    abstract fun getPosts(): ArrayList<Post>
    abstract fun addPost(post: Post, user: User)
    abstract fun dealPost(post: Post, user: User)
}