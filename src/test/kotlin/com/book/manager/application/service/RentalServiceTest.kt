package com.book.manager.application.service

import com.book.manager.domain.enum.RoleType
import com.book.manager.domain.model.Book
import com.book.manager.domain.model.BookWithRental
import com.book.manager.domain.model.Rental
import com.book.manager.domain.model.User
import com.book.manager.domain.repository.BookRepository
import com.book.manager.domain.repository.RentalRepository
import com.book.manager.domain.repository.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDate
import java.time.LocalDateTime

class RentalServiceTest : DescribeSpec() {
    init {
        describe("endRental") {
            val userId = 100L
            val bookId = 1000L
            val user = User(userId, "test@test.com", "pass", "kotlin", RoleType.USER)
            val book = Book(bookId, "Kotlin入門", "コトリン太郎", LocalDate.now())

            context("貸出中") {
                val userRepository = mockk<UserRepository>()
                val bookRepository = mockk<BookRepository>()
                val rentalRepository = mockk<RentalRepository>()
                val rentalService = RentalService(userRepository, bookRepository, rentalRepository)
                val rental = Rental(bookId, userId, LocalDateTime.now(), LocalDateTime.MAX)
                val bookWithRental = BookWithRental(book, rental)

                every { userRepository.find(any() as Long) } returns user
                every { bookRepository.findWithRental(any()) } returns bookWithRental
                every { rentalRepository.endRental(any()) } returns Unit

                it("レンタルのレコードが削除される") {
                    rentalService.endRental(bookId, userId)

                    verify { userRepository.find(userId) }
                    verify { bookRepository.findWithRental(bookId) }
                    verify(exactly = 1) { rentalRepository.endRental(bookId) }
                }
            }

            context("貸出していない") {
                val userRepository = mockk<UserRepository>()
                val bookRepository = mockk<BookRepository>()
                val rentalRepository = mockk<RentalRepository>()
                val rentalService = RentalService(userRepository, bookRepository, rentalRepository)
                val bookWithRental = BookWithRental(book, null)

                every { userRepository.find(any() as Long) } returns user
                every { bookRepository.findWithRental(any()) } returns bookWithRental
                every { rentalRepository.endRental(any()) } returns Unit

                val exception = shouldThrow<IllegalStateException> {
                    rentalService.endRental(bookId, userId)
                }

                exception.message shouldBe "未貸出の商品です bookId:$bookId"

                verify { userRepository.find(userId) }
                verify { bookRepository.findWithRental(bookId) }
                verify(exactly = 0) { rentalRepository.endRental(any()) }
            }
        }
    }
}