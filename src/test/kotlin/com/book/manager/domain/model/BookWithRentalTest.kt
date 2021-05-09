package com.book.manager.domain.model

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.LocalDateTime

class BookWithRentalTest : DescribeSpec() {
    init {
        describe("isRental") {
            val book = Book(1, "Kotlin入門", "コトリン太郎", LocalDate.now())

            context("貸出されていない状態") {
                val bookWithRental = BookWithRental(book, null)

                it("falseになる") {
                    bookWithRental.isRental shouldBe false
                }
            }

            context("貸出中") {
                val rental = Rental(1, 100, LocalDateTime.now(), LocalDateTime.MAX)
                val bookWithRental = BookWithRental(book, rental)

                it("trueになる") {
                    bookWithRental.isRental shouldBe true
                }
            }
        }
    }
}