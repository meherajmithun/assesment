"""Simple tests for the TEC206 bookstore inventory program."""

import unittest

from bookstore_inventory import Book, FictionBook, NonFictionBook


class BookTests(unittest.TestCase):
    def test_book_getters(self):
        book = Book("Python Basics", "Sam Lee", 20.50, 4, "NonFictionBook")

        self.assertEqual(book.getTitle(), "Python Basics")
        self.assertEqual(book.getAuthor(), "Sam Lee")
        self.assertEqual(book.getPrice(), 20.50)
        self.assertEqual(book.getQuantity(), 4)
        self.assertEqual(book.getType(), "NonFictionBook")

    def test_book_setters(self):
        book = Book("Old Title", "Old Author", 10, 1, "FictionBook")

        book.setTitle("New Title")
        book.setAuthor("New Author")
        book.setPrice(15.99)
        book.setQuantity(8)
        book.setType("NonFictionBook")

        self.assertEqual(book.getTitle(), "New Title")
        self.assertEqual(book.getAuthor(), "New Author")
        self.assertEqual(book.getPrice(), 15.99)
        self.assertEqual(book.getQuantity(), 8)
        self.assertEqual(book.getType(), "NonFictionBook")

    def test_fiction_book(self):
        book = FictionBook("Dune", "Frank Herbert", 18.95, 7, "Science Fiction")

        self.assertEqual(book.getType(), "FictionBook")
        self.assertEqual(book.getGenre(), "Science Fiction")
        self.assertIn("Genre: Science Fiction", repr(book))

    def test_non_fiction_book(self):
        book = NonFictionBook("Sapiens", "Yuval Noah Harari", 22.50, 3, "History")

        self.assertEqual(book.getType(), "NonFictionBook")
        self.assertEqual(book.getSubject(), "History")
        self.assertIn("Subject: History", repr(book))

    def test_invalid_values_are_rejected(self):
        with self.assertRaises(ValueError):
            FictionBook("", "Author", 10, 1, "Fantasy")
        with self.assertRaises(ValueError):
            FictionBook("Title", "Author", -1, 1, "Fantasy")
        with self.assertRaises(ValueError):
            FictionBook("Title", "Author", 10, -1, "Fantasy")
        with self.assertRaises(ValueError):
            NonFictionBook("Title", "Author", 10, 1, "")


if __name__ == "__main__":
    unittest.main(verbosity=2)
