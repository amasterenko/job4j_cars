CREATE DATABASE cars;

create table bodies
(
    id serial primary key,
    name varchar(255) not null unique
);

create table models
(
    id serial primary key,
    name varchar(255) not null unique
);

create table photos
(
    id serial primary key,
    img bytea not null
);

create table users
(
    id serial primary key,
    email varchar(255) not null unique,
    name varchar(255) not null,
    phone varchar(255) not null unique,
    password varchar(50) not null
);

create table ads
(
    id serial primary key,
    created timestamp not null,
    description varchar(2000) not null,
    sold boolean not null,
    body_id int not null references bodies(id),
    model_id int not null references models(id),
    user_id int not null references users(id)
);

create table ads_photos
(
    ad_id int not null references ads(id),
    photo_id int not null references photos(id),
    constraint ads_photos_pkey primary key (ad_id, photo_id)
);


