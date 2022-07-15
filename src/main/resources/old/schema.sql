DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;

CREATE TABLE books(
id BIGSERIAL PRIMARY KEY,
author_id INT NOT NULL,
title VARCHAR(250) NOT NULL,
price_old VARCHAR(250) DEFAULT NULL,
price VARCHAR(250) DEFAULT NULL
);

create table authors (
                         id BIGSERIAL PRIMARY key,
                         first_name VARCHAR(50),
                         last_name VARCHAR(50)
);