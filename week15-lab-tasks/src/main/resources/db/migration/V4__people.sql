CREATE TABLE if NOT EXISTS person (
id BIGINT NOT NULL AUTO_INCREMENT,
name_of_person VARCHAR(250),
date_of_birth DATE,
height INT,
weight FLOAT,
PRIMARY KEY (id)
);