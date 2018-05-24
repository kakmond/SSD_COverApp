package com.example.mond.coverapp.model

class Post(val postBy: User,
           val title: String,
           val type: String,
           val description: String,
           var dealAmount: Int,
           val price: Double,
           val dealBy: ArrayList<User>) {

    fun isFull(): Boolean {
        return dealBy.size >= dealAmount
    }

    fun isDealed(user: User): Boolean {
        return this.dealBy.contains(user)
    }

}