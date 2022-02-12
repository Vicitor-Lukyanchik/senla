DROP SCHEMA IF EXISTS lukyanchik_task10;
CREATE SCHEMA IF NOT EXISTS lukyanchik_task10 DEFAULT CHARACTER SET utf8;
USE lukyanchik_task10;
CREATE TABLE product(
                        maker VARCHAR(10) NOT NULL,
                        model VARCHAR(50) PRIMARY KEY NOT NULL,
                        type VARCHAR(50) NOT NULL,
                        CHECK (type IN ('pc', 'laptop', 'printer'))
);

CREATE TABLE pc(
                   code         int PRIMARY KEY NOT NULL,
                   model VARCHAR(50) NOT NULL,
                   speed smallint NOT NULL,
                   ram smallint NOT NULL,
                   hd real NOT NULL,
                   cd VARCHAR(10) NOT NULL,
                   price decimal,
                   FOREIGN KEY (model) REFERENCES product(model)
);

CREATE TABLE printer(
                        code int PRIMARY KEY NOT NULL,
                        model VARCHAR(50) NOT NULL,
                        color char(1) DEFAULT 'n',
                        type VARCHAR(10) NOT NULL,
                        price decimal,
                        CHECK (type IN('Laser','Jet','Matrix')),
                        CHECK (color IN('y', 'n')),
                        FOREIGN KEY (model) REFERENCES product(model)
);

CREATE TABLE laptop(
                       code         int PRIMARY KEY,
                       model VARCHAR(50) NOT NULL,
                       speed smallint NOT NULL,
                       ram smallint NOT NULL,
                       hd real NOT NULL,
                       price decimal,
                       screen smallint NOT NULL,
                       FOREIGN KEY (model) REFERENCES product(model)
);
