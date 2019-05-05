insert into person(person_id, name, password, role, rating, email)
 values (1, 'testww', 'test', 'user', 1, 'test@ru.com');

  insert into task(task_id, name, status, author_id)
  values (1, 'hhh', 'read', '1');
  insert into task(task_id, name, status, author_id)
  values (2, 'hfdfhh', 'read', '1');
  insert into task(task_id, name, status, author_id)
  values (3, 'hhhjj', 'read', '1');
  insert into task(task_id, name, status, author_id)
  values (4, 'hhhll', 'read', '3');
  insert into task(task_id, name, status, author_id)
  values (5, 'hjjjhh', 'read', '3');

 insert into candidate(candidate_id, task_id, person_id)
 values (1, 1, 3);
 insert into candidate(candidate_id, task_id, person_id)
 values (2, 4, 1);

insert into skill(skill_id, name)
values(10,'Java');
insert into skill(skill_id, name)
values(11,'JavaScript');
insert into skill(skill_id, name)
values(12,'Latin');

insert into task_skill(task_skill_id, level, task_id, skill_id) values(1,12, 3, 10);
insert into task_skill(task_skill_id, level, task_id, skill_id) values(2,11, 3, 12);

update task set status='write' where task_id=1;