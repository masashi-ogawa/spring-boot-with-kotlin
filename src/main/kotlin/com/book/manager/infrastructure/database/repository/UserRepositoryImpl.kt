package com.book.manager.infrastructure.database.repository

import com.book.manager.domain.model.User
import com.book.manager.domain.repository.UserRepository
import com.book.manager.infrastructure.database.table.UserRecord
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Repository
class UserRepositoryImpl : UserRepository {
    override fun find(email: String): User? {
        val record = UserRecord.select { UserRecord.email eq email }.single()
        return toModel(record)
    }

    override fun find(id: Long): User? {
        val record = UserRecord.select { UserRecord.id eq id }.single()
        return toModel(record)
    }

    private fun toModel(record: ResultRow): User {
        return User(
            record[UserRecord.id]!!,
            record[UserRecord.email]!!,
            record[UserRecord.password]!!,
            record[UserRecord.name]!!,
            record[UserRecord.roleType]!!,
        )
    }
}