CREATE SEQUENCE "author_generator" START WITH 1 INCREMENT BY 5;
CREATE SEQUENCE "book_generator" START WITH 1 INCREMENT BY 5;
CREATE SEQUENCE "user_generator" START WITH 1 INCREMENT BY 5;

CREATE TABLE books(
    id bigint not null,
    external_id uuid not null,
    isbn varchar(255) not null,
    title varchar(255) not null,
    description varchar(255) not null,
    genre varchar(255) not null,
    book_status varchar(255) not null,
    is_deleted boolean not null,
    primary key (id)
);

CREATE TABLE authors(
    id bigint not null,
    full_name varchar(255) not null,
    book_id bigint REFERENCES books (id),
    primary key (id)
);

CREATE TABLE users(
    id bigint not null,
    external_id uuid not null,
    email varchar(255) not null,
    name varchar(255) not null,
    password varchar(255) not null,
    roles varchar(255) not null,
    primary key (id)
)
