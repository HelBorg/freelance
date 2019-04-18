create schema if not exists freelance;

CREATE TYPE freelance.user_role AS ENUM ('admin', 'user');

create table if not exists freelance.person
(
    person_id integer primary key null,
    name      varchar(100) unique,
    password  varchar(100)        not null,
    role      user_role           not null,
    rating    integer             not null,
    email     varchar(100)        not null unique
);

create table if not exists freelance.skill
(
    skill_id integer primary key null,
    name     varchar(100)        not null unique
);

CREATE TYPE freelance.skill_level AS ENUM ('bad', 'semi_good', 'good', 'semi_profi', 'profi');


create table if not exists freelance.person_skill
(
    person_skill_id integer primary key null,
    level           skill_level         not null,
    person_id       integer             not null,
    skill_id        integer             not null,
    foreign key (person_id) references freelance.person (person_id),
    foreign key (skill_id) references freelance.skill (skill_id)
);

CREATE TYPE freelance.task_status AS ENUM ('preparing', 'publish', 'assigned', 'in_work', 'done');

create table if not exists freelance.task
(
    task_id      integer primary key null,
    name         varchar(100)        not null,
    description  varchar(100),
    deadline     timestamp,
    status       task_status         not null,
    performer_id integer,
    author_id    integer             not null,
    foreign key (performer_id) references freelance.person (person_id),
    foreign key (author_id) references freelance.person (person_id)
);

create table if not exists freelance.review
(
    review_id   integer primary key null,
    description varchar(100),
    datetime    timestamp,
    task_id     integer             not null,
    person_id   integer             not null,
    foreign key (person_id) references freelance.person (person_id),
    foreign key (task_id) references freelance.task (task_id)
);


CREATE TYPE freelance.message_status AS ENUM ('inbox', 'outbox');

create table if not exists freelance.message
(
    msg_id      integer primary key null,
    message     varchar(100),
    datetime    timestamp,
    status      message_status      not null,
    sender_id   integer,
    receiver_id integer,
    task_id     integer,
    foreign key (sender_id) references freelance.person (person_id),
    foreign key (receiver_id) references freelance.person (person_id),
    foreign key (task_id) references freelance.task (task_id)
);

create table if not exists freelance.candidate
(
    candidate_id integer primary key null,
    task_id      integer             not null,
    person_id    integer             not null,
    foreign key (person_id) references freelance.person (person_id),
    foreign key (task_id) references freelance.task (task_id)
);

create table if not exists freelance.task_skill
(
    task_skill_id integer primary key null,
    level         skill_level         not null,
    task_id       integer             not null,
    skill_id      integer             not null,
    foreign key (task_id) references freelance.task (task_id),
    foreign key (skill_id) references freelance.skill (skill_id)
);

insert into person(person_id, name, password, role, rating, email)
values (3, 'test', 'qwerty', 'user', 1, 'example@ru.com')