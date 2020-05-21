create table person (
  id int not null primary key,
  first_name text);

drop table if exists friends;
create table friends (
  person_id int not null,
  friend_id int not null
);

insert into person(id, first_name) values (1, 'Dave'), (2, 'Josh'), (3, 'Ted'), (4, 'Hank');
insert into friends(person_id, friend_id) values (1, 3), (1, 2), (1, 4), (2, 4), (3, 2);

