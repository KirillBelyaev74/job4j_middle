package ru.job4j.hibernate.mapping.many.to.many;

import org.junit.Test;

public class StoreTest {

    @Test
    public void whenAddAuthorThenDeleteThisAuthor() {
        Store store = new Store();

        Book one = new Book("Идиот");
        Book two = new Book("Преступление и наказание");
        Book three = new Book("Братья Карамазовы");
        Author authorOne = new Author("Фёдор Михайлович Достоевский");
        authorOne.getBooks().add(one);
        authorOne.getBooks().add(two);
        authorOne.getBooks().add(three);
        Book first = new Book("Евгений Онегин");
        Book second = new Book("Капитанская дочка");
        Author authorTwo = new Author("Александр Сергеевич Пушкин");
        authorTwo.getBooks().add(first);
        authorTwo.getBooks().add(second);

        store.save(authorOne);
        store.save(authorTwo);

        Author result = store.get(authorTwo.getId());
        store.delete(result);
    }
}
