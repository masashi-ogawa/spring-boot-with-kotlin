package com.book.manager.infrastructure.database.repository

import com.book.manager.domain.model.Book
import com.book.manager.domain.model.BookWithRental
import com.book.manager.domain.model.Rental
import com.book.manager.domain.repository.BookRepository
import com.book.manager.infrastructure.database.table.BookRecord
import com.book.manager.infrastructure.database.table.RentalRecord
import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Repository
class BookRepositoryImpl : BookRepository {
    override fun findAllWithRental(): List<BookWithRental> {
        return baseQuery().selectAll().map { toModel(it) }
    }

    override fun findWithRental(id: Long): BookWithRental? {
        return baseQuery().select { BookRecord.id eq id }.singleOrNull()?.let { toModel(it) }
    }

    override fun register(book: Book) {
        BookRecord.insert {
            it[id] = book.id
            it[title] = book.title
            it[author] = book.author
            it[releaseDate] = book.releaseDate
        }
    }

    override fun update(id: Long, title: String?, author: String?, releaseDate: LocalDate?) {
        BookRecord.update({ BookRecord.id eq id }) { record ->
            title?.let { record[BookRecord.title] = title }
            author?.let { record[BookRecord.author] = author }
            releaseDate?.let { record[BookRecord.releaseDate] = releaseDate }
        }
    }

    override fun delete(id: Long) {
        BookRecord.deleteWhere { BookRecord.id eq id }
    }

    private fun baseQuery(): FieldSet {
        return BookRecord
            .leftJoin(RentalRecord, { BookRecord.id }, { RentalRecord.bookId })
            .slice(
                BookRecord.id,
                BookRecord.title,
                BookRecord.author,
                BookRecord.releaseDate,
                RentalRecord.userId,
                RentalRecord.rentalDatetime,
                RentalRecord.returnDeadline
            )
    }

    private fun toModel(record: ResultRow): BookWithRental {
        val book = Book(
            record[BookRecord.id],
            record[BookRecord.title],
            record[BookRecord.author],
            record[BookRecord.releaseDate],
        )
        val rental = record.getOrNull(RentalRecord.userId)?.let {
            Rental(
                record[BookRecord.id],
                record[RentalRecord.userId],
                record[RentalRecord.rentalDatetime],
                record[RentalRecord.returnDeadline],
            )
        }
        return BookWithRental(book, rental)
    }
}