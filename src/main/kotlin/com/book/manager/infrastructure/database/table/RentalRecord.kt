package com.book.manager.infrastructure.database.table

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object RentalRecord : Table("rental") {
    val bookId = long("book_id")
    val userId = long("user_id")
    val rentalDatetime = datetime("rental_datetime")
    val returnDeadline = datetime("return_deadline")

    override val primaryKey = PrimaryKey(bookId)
}
