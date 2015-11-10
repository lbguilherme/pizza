create database pizza;
use pizza;

create table Person(
    phoneNumber     varchar(100)    not null primary key,
    name            varchar(100)    not null,
    address         varchar(100)    not null,
    cep             varchar(100)    not null);

create table Employee(
    user            varchar(100)    not null primary key,
    phoneNumber     varchar(100)    not null,
    hashPass        varchar(100)    not null,
    role            varchar(100)    not null,
    cpf             varchar(100)    not null,
    foreign key (phoneNumber) references Person(phoneNumber));

create table PizzaTaste(
    name            varchar(100)    not null primary key,
    priceMedium     float           not null,
    priceBig        float           not null,
    priceFamily     float           not null);

create table Pizza(
    idClientRequest int             not null,
    idx             int             not null,
    taste1          varchar(100)    not null,
    taste2          varchar(100)    not null,
    taste3          varchar(100)    not null,
    size            varchar(50)     not null,
    primary key (idClientRequest, idx),
    foreign key (taste1) references PizzaTaste(name),
    foreign key (taste2) references PizzaTaste(name),
    foreign key (taste3) references PizzaTaste(name));

create table OtherProductType(
    name            varchar(100)    not null primary key,
    price           float           not null);

create table OtherProduct(
    idClientRequest int             not null,
    idx             int             not null,
    product         varchar(100)    not null,
    primary key (idClientRequest, idx),
    foreign key (product) references OtherProductType(name));

create table ClientRequest(
    idClientRequest int             not null auto_increment primary key,
    phoneNumber     varchar(100)    not null,
    status          varchar(100)    not null,
    foreign key (phoneNumber) references Person(phoneNumber));
