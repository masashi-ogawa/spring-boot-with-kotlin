package com.book.manager.infrastructure.database.table

import com.book.manager.domain.enum.RoleType
import org.jetbrains.exposed.sql.Table

object UserRecord : Table("user") {
    val id = long("id")
    val email = varchar("email", 256)
    val password = varchar("password", 128)
    val name = varchar("name", 32)
    val roleType = customEnumeration("role_type", null, { value -> RoleType.valueOf(value as String) }, {it.name})

    override val primaryKey = PrimaryKey(email)

    init {
        uniqueIndex(email)
    }
}
