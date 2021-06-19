CREATE TABLE SCHOOLS(
    ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    NAME VARCHAR(255)
);
--
INSERT INTO SCHOOLS (ID, NAME) VALUES (1, 'English Forward');
--
INSERT INTO SCHOOLS (NAME) VALUES ('English Forward');
--
CREATE TABLE BRANCHES(
    ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    NAME VARCHAR(255),
    SCHOOL_ID INT,
    FOREIGN KEY (SCHOOL_ID) REFERENCES SCHOOLS(ID)
);
--
INSERT INTO BRANCHES (ID, NAME, SCHOOL_ID)
    VALUES (1, 'EF - Горьковская', 1),
           (2, 'EF - Парк Победы', 1);
--
INSERT INTO BRANCHES (NAME, SCHOOL_ID)
VALUES ('EF - Горьковская', 1),
       ('EF - Парк Победы', 1);
--
CREATE TABLE ROLES(
    ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    ROLE VARCHAR(30) NOT NULL
);
--
INSERT INTO ROLES (ROLE) VALUES ('ADMIN'), ('TEACHER'), ('STUDENT'), ('UNKNOWN');
--
CREATE TABLE USERS(
    ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    LASTNAME VARCHAR(255) NOT NULL,
    FIRSTNAME VARCHAR(255) NOT NULL,
    MIDDLENAME VARCHAR(255),
    EMAIL VARCHAR(255) NOT NULL UNIQUE,
    LOGIN VARCHAR(255) NOT NULL UNIQUE,
    PASSWORD VARCHAR(255) NOT NULL,
    ROLE_ID INT NOT NULL,
    SCHOOL_ID INT NOT NULL,
    BRANCH_ID INT NOT NULL,
    FOREIGN KEY (ROLE_ID) REFERENCES ROLES(ID),
    FOREIGN KEY (BRANCH_ID) REFERENCES BRANCHES(ID)
);
--
INSERT INTO USERS (LASTNAME, FIRSTNAME, MIDDLENAME, EMAIL, LOGIN, PASSWORD, ROLE_ID, SCHOOL_ID, BRANCH_ID) VALUES
    ('Ruzaeva', 'Alisa', 'Andreevna', 'alise.ruzaeva@yandex.ru', 'alise.ruzaeva@yandex.ru', '1', 3, 1, 2);
--
INSERT INTO USERS (LASTNAME, FIRSTNAME, MIDDLENAME, EMAIL, LOGIN, PASSWORD, ROLE_ID, SCHOOL_ID, BRANCH_ID) VALUES
('Ruzaeva', 'Oxana', 'Nikolaevna', 'om@eforward.ru', 'om@eforward.ru', '$2a$10$YNL73tPwGAssNlDwyW5VKOOsGrxiAaRjzX8/WLFqEsGosfFPHCI3y', 1, 1, 2);
--
ALTER TABLE USERS ADD SCHOOL_ID INT;
ALTER TABLE USERS DROP COLUMN SCHOOL_ID;
ALTER TABLE USERS ADD TEACHER_ID INT;
--
UPDATE USERS SET SCHOOL_ID = 1 WHERE id > 0;
--
CREATE TABLE GROUPS(
   ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
   GROUP_NAME VARCHAR(255) NOT NULL,
   SCHOOL_ID INT NOT NULL
);
--
INSERT INTO GROUPS (GROUP_NAME, SCHOOL_ID, TEACHER_ID) VALUES ('755A', 1, 12);
ALTER TABLE GROUPS ADD TEACHER_ID INT;
UPDATE GROUPS SET GROUPS.TEACHER_ID = 12 WHERE ID > 0;
--
CREATE TABLE LESSONS(
   ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
   LESSON_NAME VARCHAR(255) NOT NULL,
   PATH VARCHAR(255) NOT NULL,
   LEVEL_ID INT NOT NULL,
   COURSE_ID INT NOT NULL,
   SCHOOL_ID INT NOT NULL
);
--
INSERT INTO LESSONS (LESSON_NAME, PATH, LEVEL_ID, COURSE_ID, SCHOOL_ID) VALUES ('REGULAR VERBS', 'D:\coding\projects\EF\express_test_project\src\main\resources\tests\eng\level01\lesson01.txt', 1, 1, 1);
ALTER TABLE LESSONS ADD PATH VARCHAR(255) NOT NULL;
--

CREATE TABLE TESTRESULTS(
   ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1 INCREMENT BY 1) PRIMARY KEY,
   STUDENT_ID INT NOT NULL,
   LESSON_ID INT NOT NULL,
   RESULTS VARCHAR(65536) NOT NULL,
   TOTAL_SCORE INT NOT NULL,
   FOREIGN KEY (STUDENT_ID) REFERENCES USERS(ID),
   FOREIGN KEY (LESSON_ID) REFERENCES LESSONS(ID)
);
--
INSERT INTO TESTRESULTS (STUDENT_ID, SCHOOL_ID, LESSON_ID, RESULTS, TOTAL_SCORE) VALUES (?, ?, ?, ?, ?); --preparedStatement
--normalization--
ALTER TABLE USERS ADD UNIQUE (LOGIN); -- MAKE EXISTING COLUMN LOGIN UNIQUE
ALTER TABLE USERS ADD UNIQUE (EMAIL);
ALTER TABLE USERS
    ADD FOREIGN KEY (SCHOOL_ID)
        REFERENCES SCHOOLS (ID);

ALTER TABLE USERS ALTER COLUMN BRANCH_ID SET NULL;

ALTER TABLE GROUPS ADD FOREIGN KEY (SCHOOL_ID) REFERENCES SCHOOLS (ID);
ALTER TABLE GROUPS ADD FOREIGN KEY (TEACHER_ID) REFERENCES USERS (ID);
-- ALTER TABLE <tablename> ALTER COLUMN <columnname> SET [NOT] NULL

-- ALTER TABLE <tablename>
--     ADD [CONSTRAINT <constraintname>] FOREIGN KEY (<column list>)
--     REFERENCES <exptablename> (<column list>)
--     [ON {DELETE | UPDATE} {CASCADE | SET DEFAULT | SET NULL}];


--USERS with many foreign keys:
CREATE TABLE USERS
(
    ID         INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    LASTNAME   VARCHAR(255) NOT NULL,
    FIRSTNAME  VARCHAR(255) NOT NULL,
    MIDDLENAME VARCHAR(255),
    EMAIL      VARCHAR(255) NOT NULL UNIQUE,
    LOGIN      VARCHAR(255) NOT NULL UNIQUE,
    PASSWORD   VARCHAR(255) NOT NULL,
    ROLE_ID    INT          NOT NULL,
    SCHOOL_ID  INT          NOT NULL,
    BRANCH_ID  INT          NOT NULL,
    TEACHER_ID  INT          NOT NULL,
    GROUP_ID    INT,
    FOREIGN KEY (ROLE_ID) REFERENCES ROLES (ID),
    FOREIGN KEY (BRANCH_ID) REFERENCES BRANCHES (ID),
    FOREIGN KEY (TEACHER_ID) REFERENCES USERS (ID),
    FOREIGN KEY (SCHOOL_ID) REFERENCES SCHOOLS (ID),
    FOREIGN KEY (GROUP_ID) REFERENCES GROUPS (ID)
);
--
INSERT INTO USERS (LASTNAME, FIRSTNAME, MIDDLENAME, EMAIL, LOGIN, PASSWORD, ROLE_ID, SCHOOL_ID, BRANCH_ID, TEACHER_ID) VALUES
('Ruzaeva', 'Oxana', 'Nikolaevna', 'om@eforward.ru', 'om@eforward.ru', '$2a$10$YNL73tPwGAssNlDwyW5VKOOsGrxiAaRjzX8/WLFqEsGosfFPHCI3y', 1, 1, 2, 1);
--
CREATE TABLE COURSES
(
    ID         INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
    COURSE_NAME VARCHAR(255) NOT NULL,
    SCHOOL_ID INT NOT NULL,
    FOREIGN KEY (SCHOOL_ID) REFERENCES SCHOOLS (ID)
)
--
ALTER TABLE COURSES ADD FOREIGN KEY (SCHOOL_ID) REFERENCES SCHOOLS (ID);
--
INSERT INTO COURSES (COURSE_NAME, SCHOOL_ID) VALUES ('Курс английского языка', 1);
--
INSERT INTO COURSES (COURSE_NAME, SCHOOL_ID) VALUES ('Курс испанского языка', 1);
--
CREATE TABLE LEVELS
(
    ID         INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
    LEVEL_NAME VARCHAR(255) NOT NULL,
    COURSE_ID INT NOT NULL,
    FOREIGN KEY (COURSE_ID) REFERENCES COURSES (ID)
)
--
INSERT INTO LEVELS (LEVEL_NAME, COURSE_ID) VALUES ('Beginner', 1);
INSERT INTO LEVELS (LEVEL_NAME, COURSE_ID) VALUES ('Elementary', 1);
INSERT INTO LEVELS (LEVEL_NAME, COURSE_ID) VALUES ('Pre-Intermediate-1', 1);
ALTER TABLE LESSONS DROP COLUMN COURSE_ID;
ALTER TABLE LESSONS ADD FOREIGN KEY (LEVEL_ID) REFERENCES LEVELS (ID);

SELECT S.ID, S.NAME FROM LESSONS INNER JOIN LEVELS L on L.ID = LESSONS.LEVEL_ID
                                 JOIN COURSES C on C.ID = L.COURSE_ID
                                 JOIN SCHOOLS S on C.SCHOOL_ID = S.ID;

SELECT DISTINCT B.ID, B.NAME FROM USERS INNER JOIN SCHOOLS S on S.ID = USERS.SCHOOL_ID
                                        INNER JOIN BRANCHES B on S.ID = B.SCHOOL_ID;

SELECT LESSONS.ID, LESSONS.LESSON_NAME, PATH, L.ID, L.LEVEL_NAME, C.ID, C.COURSE_NAME
            FROM LESSONS
            INNER JOIN LEVELS L on L.ID = LESSONS.LEVEL_ID
            INNER JOIN COURSES C on C.ID = L.COURSE_ID
            WHERE LESSON_NAME = 'REGULAR VERBS';

SELECT LESSONS.ID, LESSONS.LESSON_NAME, PATH, L.ID, L.LEVEL_NAME, C.ID, C.COURSE_NAME
FROM LESSONS
         INNER JOIN LEVELS L on L.ID = LESSONS.LEVEL_ID
         INNER JOIN COURSES C on C.ID = L.COURSE_ID
WHERE LESSONS.ID = 3;