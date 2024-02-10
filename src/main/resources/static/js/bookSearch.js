function filterBook() {
    const input = document.getElementById("bookSearchInput");
    const searchText = input.value.toUpperCase();
    const books = document.getElementById("listBooks").querySelectorAll("li");
    for (let i = 0; i < books.length; i++) {
        if(books[i].id === "listHeader") continue;
        const bookTitle = books[i].getAttribute("data-title").toUpperCase();
        if (bookTitle.includes(searchText)) {
            books[i].classList.remove("d-none");
        } else {
            books[i].classList.add("d-none");
        }
    }
}
