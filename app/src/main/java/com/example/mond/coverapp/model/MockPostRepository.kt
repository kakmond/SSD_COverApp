package com.example.mond.coverapp.model

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.mond.coverapp.R
import java.util.ArrayList

class MockPostRepository : PostRepository() {

    override fun dealPost(post: Post, user: User) {
        super.postList.get(postList.indexOf(post)).dealBy.add(user)
        user.deal(post)
        setChanged()
        notifyObservers()
    }

    override fun addPost(post: Post, user: User) {
        super.postList.add(0, post)
        user.post(post)
        setChanged()
        notifyObservers()
    }

    override fun getPosts(): ArrayList<Post> {
        return super.postList
    }

    override fun loadAllPosts() {

        postList.add(0, Post(User("mic.mond@hotmail.com", "13052531", "Mond", "1102708501645", "0859732456", ArrayList<Post>(), ArrayList<Post>())
                , "Be my girlfriend", "Offer", "200 baht for free.", 1, 200.0, ArrayList<User>()))
        postList.add(0, Post(User("sathira@hotmail.com", "151616165", "Mick", "1102758501618", "0859731542", ArrayList<Post>(), ArrayList<Post>())
                , "Teach SSD", "Offer", "I got A in this subject so trust me.", 1, 1000.0, ArrayList<User>()))
        postList.add(0, Post(User("sathira@hotmail.com", "151616165", "Mick", "1102758501618", "0859731542", ArrayList<Post>(), ArrayList<Post>())
                , "I want to teach ADT", "Offer", "Location: Computer building KU 18.00", 5, 2500.0, ArrayList<User>()))
        postList.add(0, Post(User("varit@gmail.com", "123456789", "Kong", "1102859102651", "0895410022",
                ArrayList<Post>(), ArrayList<Post>())
                , "Need OOP1 tutors", "Request", "Find someone that expert in OOP1", 2, 250.50, ArrayList<User>()))
        postList.add(0, Post(User("varit@gmail.com", "123456789", "Kong", "1102859102651", "0895410022",
                ArrayList<Post>(), ArrayList<Post>())
                , "Need OOP2 tutors", "Request", "Someone helps me OOP2", 5, 500.0, ArrayList<User>()))
        setChanged()
        notifyObservers()
    }

}