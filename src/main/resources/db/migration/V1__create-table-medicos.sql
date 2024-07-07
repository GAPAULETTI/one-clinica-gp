create table medicos(

    id bigint not null auto_increment,
    nombre varchar(100) not null,
    email varchar(100) not null unique,
    documento varchar(10) not null unique,
    especialidad varchar(100) not null,
    calle varchar(100) not null,
    numero int (20),
    ciudad varchar(100) not null,
    provincia varchar(100),

    primary key(id)


);