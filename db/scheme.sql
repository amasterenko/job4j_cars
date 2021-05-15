CREATE DATABASE cars;

create table bodies
(
    id serial primary key,
    name varchar(255) not null unique
);

create table makes
(
    id serial primary key,
    name varchar(255) not null unique
);

create table models
(
    id serial primary key,
    name varchar(255) not null unique,
    make_id int not null references makes(id)
);

create table engines
(
    id serial primary key,
    name varchar(255) not null unique
);

create table transmissions
(
    id serial primary key,
    name varchar(255) not null unique
);

create table colors
(
    id serial primary key,
    name varchar(255) not null unique
);

create table credentials
(
    id serial primary key,
    hash bytea not null,
    salt bytea not null
);

create table users
(
    id serial primary key,
    email varchar(30) not null unique,
    name varchar(30) not null,
    phone varchar(15) not null unique,
    credentials_id int not null references credentials(id)
);

create table ads
(
    id serial primary key,
    created timestamp not null,
    title varchar(50) not null,
    description varchar(2000) not null,
    sold boolean not null,
    km int not null,
    year int not null,
    price int not null,
    engineDisplacement float4,
    user_id int not null references users(id),
    body_id int not null references bodies(id),
    model_id int not null references models(id),
    engine_id int not null references engines(id),
    transmission_id int not null references transmissions(id),
    color_id int not null references colors(id)
);

create table photos
(
    id serial primary key,
    img_path varchar(255) not null unique,
    ad_id int references ads(id)
);


/*Data initialization*/
INSERT INTO bodies (name)
VALUES ('Coupe'),
       ('Convertible'),
       ('Crossover'),
       ('Hatchback'),
       ('Muscle Car'),
       ('MUV/SUV'),
       ('Off-road'),
       ('Pickup'),
       ('Sedan'),
       ('Sports Car'),
       ('Van'),
       ('Wagon')
       ;

INSERT INTO makes (name)
VALUES ('Audi'),
       ('BMW'),
       ('Ford'),
       ('Hyundai'),
       ('Mazda'),
       ('Mitsubishi'),
       ('Nissan'),
       ('Subaru'),
       ('Toyota'),
       ('Volkswagen')
;

INSERT INTO models (name, make_id)
VALUES
       ('A2', 1),
       ('A3', 1),
       ('A5', 1),
       ('Q3', 1),
       ('Q5', 1),
       ('3 Series', 2),
       ('5 Series', 2),
       ('X1 Series', 2),
       ('X3 Series', 2),
       ('X5 Series', 2),
       ('Focus', 3),
       ('Mustang', 3),
       ('Explorer', 3),
       ('Transit', 3),
       ('Escape', 3),
       ('i30', 4),
       ('ix35', 4),
       ('Tucson', 4),
       ('Genesis', 4),
       ('3', 5),
       ('6', 5),
       ('CX3', 5),
       ('CX5', 5),
       ('Lancer', 6),
       ('Outlander', 6),
       ('Pajero', 6),
       ('Pajero Sport', 6),
       ('X-Trail', 7),
       ('Patrol', 7),
       ('Navara', 7),
       ('QASHQAI', 7),
       ('Forester', 8),
       ('Outback', 8),
       ('Legacy', 8),
       ('XV', 8),
       ('Corolla', 9),
       ('RAV4', 9),
       ('Camry', 9),
       ('Land Cruiser', 9),
       ('Polo', 10),
       ('Golf', 10),
       ('Passat', 10),
       ('Jetta', 10),
       ('Tiguan', 10),
       ('Tuareg', 10)
;

INSERT INTO transmissions (name)
VALUES ('Manual'),
       ('Automatic'),
       ('Auto-manual'),
       ('CVT')
;

INSERT INTO engines (name)
VALUES ('Petrol'),
       ('Diesel'),
       ('Electric'),
       ('Hybrid electric')
;

INSERT INTO colors (name)
VALUES ('Beige'),
       ('Black'),
       ('Blue'),
       ('Grey'),
       ('Orange'),
       ('Red'),
       ('White'),
       ('Yellow')
;




