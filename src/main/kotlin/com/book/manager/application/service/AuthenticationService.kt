package com.book.manager.application.service

import com.book.manager.domain.model.User
import com.book.manager.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthenticationService(private val userRepository: UserRepository) {
    @Transactional
    fun findUser(email: String): User? {
        return userRepository.find(email)
    }
}
