drop table if exists marca CASCADE;
drop table if exists modelo CASCADE;
drop table if exists veiculo CASCADE;
create table marca (
    id varchar(255) not null,
    fipe_id integer not null,
    name varchar(255),
    primary key (id)
);
create table modelo (
    id varchar(255) not null,
    fipe_id integer not null,
    name varchar(255),
    id_marca varchar(255) not null,
    primary key (id)
);
create table veiculo (
    id varchar(255) not null,
    create_date date,
    preco_fipe decimal(19,2),
    placa varchar(255) not null,
    preco_anuncio decimal(19,2) not null,
    ano integer not null,
    id_marca varchar(255) not null,
    primary key (id)
);
alter table veiculo add constraint UK_luoyk9d8idgi0wif7bxtefsr5 unique (placa);
alter table modelo add constraint FKnsndlfkbon3sbxik0xvclk082 foreign key (id_marca) references marca;
alter table veiculo add constraint FKdapwkul6vyqdtn5wn8jy9n99p foreign key (id_marca) references modelo;
