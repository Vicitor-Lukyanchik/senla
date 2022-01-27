CREATE TABLE product(
                        maker VARCHAR(10) NOT NULL,
                        model VARCHAR(50) PRIMARY KEY NOT NULL,
                        type VARCHAR(50) NOT NULL,
                        CHECK (type IN ('pc', 'laptop', 'printer'))
);

CREATE TABLE pc(
                   code         int PRIMARY KEY NOT NULL,
                   model VARCHAR(50) REFERENCES NOT NULL REFERENCES product(model),
                   speed smallint NOT NULL,
                   ram smallint NOT NULL,
                   hd real NOT NULL,
                   cd VARCHAR(10) NOT NULL,
                   price decimal
);

CREATE TABLE printer(
                        code int PRIMARY KEY NOT NULL,
                        model VARCHAR(50) NOT NULL REFERENCES product(model),
                        color char(1) DEFAULT 'n',
                        type VARCHAR(10) NOT NULL,
                        price decimal,
                        CHECK (type IN('Laser','Jet','Matrix')),
                        CHECK (color IN('y', 'n'))
);

CREATE TABLE laptop(
                       code         int PRIMARY KEY,
                       model VARCHAR(50) NOT NULL  REFERENCES product(model),
                       speed smallint NOT NULL,
                       ram smallint NOT NULL,
                       hd real NOT NULL,
                       price decimal,
                       screen smallint NOT NULL
);
