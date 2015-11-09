create database pizza;
use pizza;

create table Person(
    phoneNumber     varchar(100)    not null primary key,
    name            varchar(100)    not null,
    cpf             varchar(100)    not null,
    address         varchar(100)    not null,
    cep             varchar(100)    not null);

create table Employee(
    user            varchar(100)    not null primary key,
    phoneNumber     varchar(100)    not null,
    hashPass        varchar(100)    not null,
    role            varchar(100)    not null,
    foreign key (phoneNumber)  references Person(phoneNumber));

create table PizzaTaste(
    idPizzaTaste    int             not null auto_increment primary key,
    taste           varchar(100)    not null,
    size            varchar(100)    not null,
    price           float           not null);

create table DrinkType(
    idDrinkType     int             not null auto_increment primary key,
    name            varchar(100)    not null,
    price           float           not null);

create table ClientRequest(
    idClientRequest int             not null auto_increment primary key,
    phoneNumber     varchar(100)    not null,
    idPizzaTaste    int             not null,
    idDrinkType     int             ,
    status          varchar(100)    not null,
    foreign key (phoneNumber)  references Person(phoneNumber),
    foreign key (idPizzaTaste) references PizzaTaste(idPizzaTaste),
    foreign key (idDrinkType)  references DrinkType(idDrinkType));
