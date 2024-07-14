create table pacientes(

    id bigint not null auto_increment,
    nombre varchar(100) not null,
    email varchar(100) not null unique,
    documento varchar(14) not null unique,
    telefono varchar(20) not null,
    calle varchar(100) not null,
    numero int (20),
    ciudad varchar(100) not null,
    provincia varchar(100),

    activo tinyint not null,

    primary key(id)

);