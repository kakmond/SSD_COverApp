package com.example.mond.coverapp.presenter

import android.app.Dialog
import android.content.DialogInterface
import android.os.AsyncTask
import android.support.v7.app.AlertDialog
import com.example.mond.coverapp.model.Post
import com.example.mond.coverapp.model.PostRepository
import com.example.mond.coverapp.model.User
import java.util.*
import kotlin.collections.ArrayList

class PostPresenter(val view: PostView,
                    val repository: PostRepository) : Observer {

    override fun update(p0: Observable?, p1: Any?) {
        view.setPostList(repository.getPosts())
    }

    fun newPost(title: String,
                type: String,
                description: String,
                dealAmount: Int,
                price: Double,
                user: User) {
        var post = Post(user, title, type, description, dealAmount, price, ArrayList<User>())
        repository.addPost(post, user)
    }

    fun refresh() {
        repository.loadAllPosts()
    }


    fun deal(post: Post, user: User) {
        this.repository.dealPost(post, user)
    }

    fun start() {
        repository.addObserver(this)
        repository.loadAllPosts()
    }

}