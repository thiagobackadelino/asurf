insert into usuarios ( id , nome , senha , sexo , email ,cpf , telefone ,ativo  ) values ( 1 , 'admin' , '123' ,1,'administrador@asurf.com.br','43043654870','1122-8833',true  );
insert into roles (id , nome ) values (1, 'ROLE_ADMIN');
insert into roles (id , nome ) values (2, 'ROLE_USER');
insert into roles (id , nome ) values (2, 'PROFESSOR');
insert into usuarios_roles values (1,1);