create table if not exists public.person(
person_id serial primary key,
name varchar unique,
password varchar not null,
role varchar not null,
rating integer not null,
email varchar not null unique
);

create table if not exists public.skill(
skill_id serial primary key,
name varchar not null unique
);

create table if not exists public.person_skill(
person_skill_id serial primary key,
level varchar not null,
person_id integer not null,
skill_id integer not null,
foreign key (person_id) references public.person(person_id),
foreign key (skill_id) references public.skill(skill_id)
);

create table if not exists public.task(
task_id serial primary key ,
name varchar not null,
description text,
deadline timestamp,
time_created timestamp,
status varchar not null,
performer_id integer ,
author_id integer not null,
foreign key (performer_id) references public.person(person_id),
foreign key (author_id) references public.person(person_id)
);

create table if not exists public.review(
review_id serial primary key,
description text,
datetime timestamp,
done BOOL DEFAULT false,
task_id integer,
person_id integer not null,
parent_id integer,
foreign key (person_id) references public.person(person_id),
foreign key (task_id) references public.task(task_id),
foreign key (parent_id) references public.review(review_id)
);

create table if not exists public.candidate(
candidate_id serial primary key,
task_id integer not null,
person_id integer not null,
foreign key(person_id) references public.person(person_id),
foreign key(task_id) references public.task(task_id)
);

create table if not exists public.task_skill(
task_skill_id serial  primary key,
level varchar not null,
task_id integer not null,
skill_id integer not null,
foreign key (task_id) references public.task (task_id),
foreign key (skill_id) references public.skill (skill_id)
);

insert into skill(name) values('java');
insert into skill(name) values('sql');
insert into skill(name) values('spring');
insert into skill(name) values('javascript');

create index idx_task_name on task(name);
create index idx_task_due_date on task(deadline);
create index idx_task_deadline on task(time_created);
create index idx_person_name on person(name);