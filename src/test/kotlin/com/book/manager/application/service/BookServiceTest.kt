package com.book.manager.application.service

import com.book.manager.domain.model.Book
import com.book.manager.domain.model.BookWithRental
import com.book.manager.domain.repository.BookRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate

class BookServiceTest : BehaviorSpec() {
    init {
        val bookRepository = mockk<BookRepository>()
        val bookService = BookService(bookRepository)

        given("getList") {
            When("本のデータが存在している") {
                val book = Book(1, "Kotlin入門", "コトリン太郎", LocalDate.now())
                val bookWithRental = BookWithRental(book, null)
                val expected = listOf(bookWithRental)

                every { bookRepository.findAllWithRental() } returns expected

                then("本の一覧データを取得できる") {
                    val result = bookService.getList()
                    result shouldBe expected
                }
            }
        }
    }
}