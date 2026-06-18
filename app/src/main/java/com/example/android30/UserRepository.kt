package com.example.android30

class UserRepository {

    private val users = mutableListOf<User>()

    fun addUser(user: User) {
        users.add(user)
    }

    fun getUsers(): List<User> {
        return users
    }

    fun findUser(id: Int): User? {
        return users.find { it.id == id }
    }
}