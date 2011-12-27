create table dictionary (id bigint not null generated always as identity, en varchar(1000), zh varchar(1000), type varchar(100),source varchar(100),accurate int)
create table exam (id bigint not null generated always as identity, description varchar(1000), options varchar(1000), date DATE,remain int,correct int,wrong int)
create table exam_word (id bigint not null generated always as identity, en varchar(1000), zh varchar(1000), type varchar(100),source varchar(100),examId bigint)
create table history_exam_word (id bigint not null generated always as identity, en varchar(1000), zh varchar(1000), type varchar(100),source varchar(100),examId bigint)