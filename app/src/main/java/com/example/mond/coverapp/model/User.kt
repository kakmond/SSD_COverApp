package com.example.mond.coverapp.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*
import kotlin.collections.ArrayList

class User(val email: String,
           val password: String,
           val name: String,
           val id: String,
           val tel: String,
           var dealList: ArrayList<Post>,
           var postList: ArrayList<Post>) : Parcelable, Observable() {

    private var balance: Double = 0.0

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            arrayListOf<Post>().apply {
                parcel.readList(this, Post::class.java.classLoader)
            },
            arrayListOf<Post>().apply {
                parcel.readList(this, Post::class.java.classLoader)
            }
    ) {
        balance = parcel.readDouble()
    }

    fun deal(post: Post) {
        this.dealList.add(post)
        if (post.type == "Offer")
            this.withdraw(post.price)
        else
            this.addMoney(post.price)
    }

    fun unpost(post: Post) {
        if (post.type == "Request") {
            var rest: Int = post.dealAmount - post.dealBy.size
            var moneyBack: Double = rest * post.price
            this.balance += moneyBack
        }
        post.dealAmount = post.dealBy.size
        setChanged()
        notifyObservers()
    }

    fun post(post: Post) {
        if (post.type == "Request")
            this.balance -= post.price * post.dealAmount
        this.postList.add(post)
        setChanged()
        notifyObservers()
    }

    fun getBalance(): Double {
        return balance
    }

    fun withdraw(amount: Double) {
        this.balance -= amount
        setChanged()
        notifyObservers()
    }

    fun addMoney(amount: Double) {
        this.balance += amount
        setChanged()
        notifyObservers()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(name)
        parcel.writeString(id)
        parcel.writeString(tel)
        parcel.writeDouble(balance)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}