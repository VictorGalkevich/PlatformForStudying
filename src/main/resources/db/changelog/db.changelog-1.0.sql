--liquibase formatted sql

--changeset victordev:1
CREATE TABLE IF NOT EXISTS groups
(
    id INTEGER PRIMARY KEY,
    name VARCHAR(255)
);

--changeset victordev:2
CREATE TABLE IF NOT EXISTS tests
(
    id INTEGER PRIMARY KEY,
    access BOOLEAN,
    duration BIGINT,
    end_time TIMESTAMP,
    start_time TIMESTAMP,
    created_by VARCHAR(255),
    test_name VARCHAR(255)
);


--changeset victordev:3
CREATE TABLE IF NOT EXISTS assignments
(
    id INTEGER PRIMARY KEY ,
    rating INT,
    status VARCHAR(255),
    test_id INTEGER REFERENCES tests (id) ON DELETE CASCADE
);

--changeset victordev:4
CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    enabled BOOLEAN,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255),
    type VARCHAR(255),
    username VARCHAR(255)
);

--changeset victordev:5
CREATE TABLE IF NOT EXISTS teachers
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE
);

--changeset victordev:6
CREATE TABLE IF NOT EXISTS teacher_tests
(
    teacher_id BIGINT REFERENCES teachers(id) ON DELETE CASCADE,
    test_id INTEGER REFERENCES tests(id) ON DELETE CASCADE
);

--changeset victordev:7
CREATE TABLE IF NOT EXISTS students
(
    id INTEGER PRIMARY KEY,
    test_id INTEGER REFERENCES tests(id) ON DELETE CASCADE,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE
);

--changeset victordev:8
CREATE TABLE IF NOT EXISTS students_tests
(
    student_id INTEGER REFERENCES students(id) ON DELETE CASCADE,
    test_id INTEGER REFERENCES tests(id) ON DELETE CASCADE
);

--changeset victordev:9
CREATE TABLE IF NOT EXISTS teacher_groups
(
    teacher_id BIGINT REFERENCES teachers(id) ON DELETE CASCADE,
    group_id INTEGER REFERENCES groups(id) ON DELETE CASCADE
);

--changeset victordev:10
CREATE TABLE IF NOT EXISTS student_assignments
(
    student_id INTEGER REFERENCES students(id) ON DELETE CASCADE,
    assignment_id INTEGER REFERENCES assignments(id) ON DELETE CASCADE
);

--changeset victordev:11
CREATE TABLE IF NOT EXISTS questions
(
    id INTEGER PRIMARY KEY,
    right_answer VARCHAR(255),
    text VARCHAR(255),
    type VARCHAR(255)
);

--changeset victordev:12
CREATE TABLE IF NOT EXISTS profile
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE
);

--changeset victordev:13
CREATE TABLE IF NOT EXISTS possible_answers
(
    option_question_id INTEGER REFERENCES questions(id) ON DELETE CASCADE,
    possible_answers VARCHAR(255)
);

--changeset victordev:14
CREATE TABLE IF NOT EXISTS groups_students
(
    group_id INTEGER REFERENCES groups(id) ON DELETE CASCADE,
    student_id INTEGER REFERENCES students(id) ON DELETE CASCADE
);

--changeset victordev:16
CREATE TABLE IF NOT EXISTS assignments_answers
(
    answer_id INTEGER REFERENCES assignments(id) ON DELETE CASCADE,
    user_answers VARCHAR(255)
);

--changeset victordev:17
CREATE TABLE IF NOT EXISTS tests_questions
(
    test_id INTEGER REFERENCES assignments(id) ON DELETE CASCADE,
    question_id INTEGER REFERENCES assignments(id) ON DELETE CASCADE
);


