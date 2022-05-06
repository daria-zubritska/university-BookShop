DROP TABLE IF EXISTS user_to_roles;
DROP TABLE IF EXISTS users cascade ;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS books_in_wishlist;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS wishlist;

create table books(
                      isbn varchar primary key,
                      title varchar,
                      author varchar
);

CREATE TABLE users(
                      id int primary key auto_increment,
                      username VARCHAR(25) not null,
                      password VARCHAR(255) not null
);

create table wishlist(
                         id int primary key auto_increment,
                         user_id int not null,
                         constraint fk_user_id_to_user foreign key (user_id) references users(id)
);

create table books_in_wishlist(
                                  book_isbn int not null,
                                  wishlist_id int not null,
                                  constraint fk_book_to_book_wishlist foreign key (book_isbn) references books(isbn),
                                  constraint fk_wishlist_to_book_wishlist foreign key (wishlist_id) references wishlist(id)
);

insert into users (username, password) values
                                        ('admin', 'passadmin'),
                                        ('user', 'passuser');
