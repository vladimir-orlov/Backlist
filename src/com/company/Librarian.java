package com.company;

public interface Librarian {
/*
* in: FIND [author=<автор>] [name=<bookname>]
* out: FOUND id=<индекс1> lib=<библиотека1>
*      FOUNDMISSING id=<индекс1> lib=<библиотека1> issued=<дата выдачи1>
*      NOTFOUND
* */
    String findBook();

/*
* in: ORDER id=<индекс> abonent=<имя абонента>
* out: OK abonent=<имя абонента> date= <текущая дата>
*      RESERVED abonent=<имя абонента> date= <текущая дата>
*      NOTFOUND
* */
    String orderBook();

/*
* in: RETURN id=<индекс>
* out: OK abonent=<имя абонента>
*      ALREADYRETURNED
*      NOTFOUND
* */
    String returnBook();
}
