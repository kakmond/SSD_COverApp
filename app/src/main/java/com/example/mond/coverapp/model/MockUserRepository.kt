package com.example.mond.coverapp.model

class MockUserRepository {


    private var userList = ArrayList<User>()

    private object Holder {
        val INSTANCE = MockUserRepository()
    }

    companion object {
        val instance: MockUserRepository by lazy { Holder.INSTANCE }
    }

    private constructor() {
        userList.add(User("mic.mond@hotmail.com", "13052531", "Mond", "1102708501645", "0859732456", ArrayList<Post>(), ArrayList<Post>()))
        userList.add(User("sathira@hotmail.com", "151616165", "Mick", "1102758501618", "0859731542", ArrayList<Post>(), ArrayList<Post>()))
        userList.add(User("varit@gmail.com", "123456789", "Kong", "1102859102651", "0895410022", ArrayList<Post>(), ArrayList<Post>()))
        userList.add(User("admin@hotmail.com", "123456", "Admin", "5910545817", "0859732244", ArrayList<Post>(), ArrayList<Post>()))
    }


    fun login(email: String, password: String): User {
        for (user in userList)
            if (email == user.email && password == user.password)
                return user
        throw NullPointerException()
    }

    fun register(email: String,
                 password: String,
                 name: String,
                 id: String,
                 tel: String) {
        userList.add(User(email, password, name, id, tel, ArrayList<Post>(), ArrayList<Post>()))
    }


}