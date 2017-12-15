use prem;
drop table if exists foo;
drop table if exists foo2;
create table foo (
  id int not null auto_increment primary key,
  description varchar(64) not null
);
create table foo2 (
  id int not null primary key
);
insert into foo(description) values("prem");
insert into foo2(id) values(last_insert_id());
