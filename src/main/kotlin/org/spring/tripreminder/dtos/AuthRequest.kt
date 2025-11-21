package org.spring.tripreminder.dtos

class AuthRequest(
    private val username: String,
    private val password: String
){
    fun getUsername(): String{
        return username
    }

    fun getPassword(): String{
        return password
    }
}