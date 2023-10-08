CREATE SEQUENCE "book_records_generator" START WITH 1 INCREMENT BY 5;

CREATE TABLE book_records
(
    id         bigint                 not null,
    book_id    uuid                   not null,
    taken_at   time without time zone not null,
    expired_at time without time zone not null,
    primary key (id)
)