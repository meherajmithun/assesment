# ---------------------------------------------------------------------------
# 1. Parent class
# ---------------------------------------------------------------------------
class Book:
    """Store the information that every book has."""

    def __init__(self, title, author, price, quantity, book_type):
        # Use the setters so that new objects are validated immediately.
        self.setTitle(title)
        self.setAuthor(author)
        self.setPrice(price)
        self.setQuantity(quantity)
        self.setType(book_type)

    # Getters return the values stored in the object.
    def getTitle(self):
        return self.__title

    def getAuthor(self):
        return self.__author

    def getPrice(self):
        return self.__price

    def getQuantity(self):
        return self.__quantity

    def getType(self):
        return self.__type

    # Setters validate a value before storing it.
    def setTitle(self, title):
        if not isinstance(title, str) or title.strip() == "":
            raise ValueError("Title cannot be empty.")
        self.__title = title.strip()

    def setAuthor(self, author):
        if not isinstance(author, str) or author.strip() == "":
            raise ValueError("Author cannot be empty.")
        self.__author = author.strip()

    def setPrice(self, price):
        if isinstance(price, bool) or not isinstance(price, (int, float)):
            raise ValueError("Price must be a number.")
        if price < 0:
            raise ValueError("Price cannot be negative.")
        self.__price = float(price)

    def setQuantity(self, quantity):
        if isinstance(quantity, bool) or not isinstance(quantity, int):
            raise ValueError("Quantity must be a whole number.")
        if quantity < 0:
            raise ValueError("Quantity cannot be negative.")
        self.__quantity = quantity

    def setType(self, book_type):
        if book_type not in ("FictionBook", "NonFictionBook"):
            raise ValueError("Type must be FictionBook or NonFictionBook.")
        self.__type = book_type

    def __repr__(self):
        """Return a readable description of the book."""
        return (
            f"Title: {self.__title} | Author: {self.__author} | "
            f"Price: ${self.__price:.2f} | Quantity: {self.__quantity} | "
            f"Type: {self.__type}"
        )


# ---------------------------------------------------------------------------
# 2. Child classes
# ---------------------------------------------------------------------------
class FictionBook(Book):
    """A Book that also has a fiction genre."""

    def __init__(self, title, author, price, quantity, genre):
        # super() calls the constructor in the parent Book class.
        super().__init__(title, author, price, quantity, "FictionBook")

        if not isinstance(genre, str) or genre.strip() == "":
            raise ValueError("Genre cannot be empty.")
        self.__genre = genre.strip()

    def getGenre(self):
        return self.__genre

    def __repr__(self):
        return super().__repr__() + f" | Genre: {self.__genre}"


class NonFictionBook(Book):
    """A Book that also has a non-fiction subject."""

    def __init__(self, title, author, price, quantity, subject):
        super().__init__(title, author, price, quantity, "NonFictionBook")

        if not isinstance(subject, str) or subject.strip() == "":
            raise ValueError("Subject cannot be empty.")
        self.__subject = subject.strip()

    def getSubject(self):
        return self.__subject

    def __repr__(self):
        return super().__repr__() + f" | Subject: {self.__subject}"


# ---------------------------------------------------------------------------
# 3. Input-validation functions
# ---------------------------------------------------------------------------
def get_text(prompt):
    """Keep asking until the user enters some text."""
    while True:
        value = input(prompt).strip()
        if value != "":
            return value
        print("Input cannot be empty. Please try again.")


def get_price(prompt):
    """Keep asking until the user enters a number that is zero or higher."""
    while True:
        try:
            price = float(input(prompt))
            if price >= 0:
                return price
            print("Price cannot be negative.")
        except ValueError:
            print("Please enter a valid number, for example 12.50.")


def get_quantity(prompt):
    """Keep asking until the user enters a whole number that is zero or higher."""
    while True:
        try:
            quantity = int(input(prompt))
            if quantity >= 0:
                return quantity
            print("Quantity cannot be negative.")
        except ValueError:
            print("Please enter a valid whole number.")


def get_book_number(inventory):
    """Ask for a displayed book number and make sure it exists."""
    while True:
        try:
            book_number = int(input("Enter the book number: "))
            if 1 <= book_number <= len(inventory):
                # List positions start at 0, so subtract 1 from the displayed number.
                return book_number - 1
            print("That book number does not exist.")
        except ValueError:
            print("Please enter a valid whole number.")


# ---------------------------------------------------------------------------
# 4. Menu-action functions
# ---------------------------------------------------------------------------
def add_fiction_book(inventory):
    """Read the details of a fiction book and add it to the list."""
    print("\n--- Add Fiction Book ---")
    title = get_text("Title: ")
    author = get_text("Author: ")
    price = get_price("Price: $")
    quantity = get_quantity("Quantity: ")
    genre = get_text("Genre: ")

    new_book = FictionBook(title, author, price, quantity, genre)
    inventory.append(new_book)
    print("Fiction book added successfully.")


def add_non_fiction_book(inventory):
    """Read the details of a non-fiction book and add it to the list."""
    print("\n--- Add Non-Fiction Book ---")
    title = get_text("Title: ")
    author = get_text("Author: ")
    price = get_price("Price: $")
    quantity = get_quantity("Quantity: ")
    subject = get_text("Subject: ")

    new_book = NonFictionBook(title, author, price, quantity, subject)
    inventory.append(new_book)
    print("Non-fiction book added successfully.")


def display_books(inventory):
    """Display every book currently stored in the inventory list."""
    print("\n--- Book Inventory ---")

    if len(inventory) == 0:
        print("The inventory is empty.")
        return

    for number, book in enumerate(inventory, start=1):
        # Python automatically calls the object's __repr__ method here.
        print(f"{number}. {book}")


def update_price(inventory):
    """Choose a book and give it a new price."""
    if len(inventory) == 0:
        print("\nThe inventory is empty. Add a book first.")
        return

    display_books(inventory)
    book_index = get_book_number(inventory)
    new_price = get_price("Enter the new price: $")
    inventory[book_index].setPrice(new_price)
    print("Price updated successfully.")


def update_quantity(inventory):
    """Choose a book and give it a new stock quantity."""
    if len(inventory) == 0:
        print("\nThe inventory is empty. Add a book first.")
        return

    display_books(inventory)
    book_index = get_book_number(inventory)
    new_quantity = get_quantity("Enter the new quantity: ")
    inventory[book_index].setQuantity(new_quantity)
    print("Quantity updated successfully.")


# ---------------------------------------------------------------------------
# 5. Main menu
# ---------------------------------------------------------------------------
def display_menu():
    print("\n===== BOOKSTORE INVENTORY =====")
    print("1. Add Fiction Book")
    print("2. Add Non-Fiction Book")
    print("3. Display Books")
    print("4. Update Price")
    print("5. Update Quantity")
    print("6. Exit")


def main():
    """Create the inventory list and repeat the menu until Exit is chosen."""
    inventory = []

    while True:
        display_menu()
        choice = input("Choose an option (1-6): ").strip()

        if choice == "1":
            add_fiction_book(inventory)
        elif choice == "2":
            add_non_fiction_book(inventory)
        elif choice == "3":
            display_books(inventory)
        elif choice == "4":
            update_price(inventory)
        elif choice == "5":
            update_quantity(inventory)
        elif choice == "6":
            print("Thank you for using the bookstore inventory program.")
            break
        else:
            print("Invalid option. Please choose a number from 1 to 6.")


# This makes main() run only when this file is started directly.
if __name__ == "__main__":
    main()
