package com.book.manager.infrastructure.database.table

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.date

object BookRecord : Table("book") {
    val id = long("id")
    val title = varchar("title", 128)
    val author = varchar("author", 32)
    val releaseDate = date("release_date")

    override val primaryKey = PrimaryKey(id)
}