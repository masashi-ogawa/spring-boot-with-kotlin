package com.book.manager.infrastructure.database.repository

import com.book.manager.domain.model.Rental
import com.book.manager.domain.repository.RentalRepository
import com.book.manager.infrastructure.database.table.RentalRecord
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.springframework.stereotype.Repository

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Repository
class RentalRepositoryImpl : RentalRepository {
    override fun startRental(rental: Rental) {
        RentalRecord.insert {
            it[bookId] = rental.bookId
            it[userId] = rental.userId
            it[rentalDatetime] = rental.rentalDatetime
            it[returnDeadline] = rental.returnDeadline
        }
    }

    override fun endRental(bookId: Long) {
        RentalRecord.deleteWhere { RentalRecord.bookId eq bookId }
    }
}