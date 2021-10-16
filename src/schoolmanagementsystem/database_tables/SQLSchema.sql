CREATE DATABASE IF NOT EXISTS schoolmanagementsystem;

CREATE TABLE IF NOT EXISTS schoolmanagementsystem.admins ( 
    id BIGINT NOT NULL , 
    username VARCHAR(255) NOT NULL , 
    password VARCHAR(255) NOT NULL , 
    PRIMARY KEY (id), 
    UNIQUE (username)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS schoolmanagementsystem.professors ( 
    id BIGINT NOT NULL , 
    username VARCHAR(255) NOT NULL , 
    password VARCHAR(255) NOT NULL , 
    name VARCHAR(255) NOT NULL , 
    department VARCHAR(255) NOT NULL , 
    PRIMARY KEY (id),
    UNIQUE (username)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS schoolmanagementsystem.students ( 
    id BIGINT NOT NULL , 
    username VARCHAR(255) NOT NULL , 
    password VARCHAR(255) NOT NULL , 
    name VARCHAR(255) NOT NULL , 
    section VARCHAR(255) NOT NULL , 
    PRIMARY KEY (id), 
    UNIQUE (username)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS schoolmanagementsystem.subjects ( 
    id VARCHAR(255) NOT NULL , 
    name VARCHAR(255) NOT NULL , 
    units INT NOT NULL DEFAULT '1' , 
    description VARCHAR(255) NOT NULL , 
    professor_id BIGINT NOT NULL , 
    PRIMARY KEY (id),
    FOREIGN KEY (professor_id) REFERENCES schoolmanagementsystem.professors(id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS schoolmanagementsystem.handled_subjects ( 
    professor_id BIGINT NOT NULL , 
    subject_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (professor_id) REFERENCES schoolmanagementsystem.professors(id),
    FOREIGN KEY (subject_id) REFERENCES schoolmanagementsystem.subjects(id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS schoolmanagementsystem.student_subjects ( 
    student_id BIGINT NOT NULL , 
    subject_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (student_id) REFERENCES schoolmanagementsystem.students(id),
    FOREIGN KEY (subject_id) REFERENCES schoolmanagementsystem.subjects(id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS schoolmanagementsystem.notes ( 
    id BIGINT NOT NULL , 
    subject_id VARCHAR(255) NOT NULL , 
    title VARCHAR(255) NOT NULL , 
    content VARCHAR(255) NOT NULL , 
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP , 
    PRIMARY KEY (id),
    FOREIGN KEY (subject_id) REFERENCES schoolmanagementsystem.subjects(id)
) ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS schoolmanagementsystem.sessions ( 
    id BIGINT NOT NULL , 
    subject_id VARCHAR(255) NOT NULL , 
    title VARCHAR(255) NOT NULL , 
    description VARCHAR(255) NOT NULL , 
    held_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP , 
    PRIMARY KEY (id),
    FOREIGN KEY (subject_id) REFERENCES schoolmanagementsystem.subjects(id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS schoolmanagementsystem.attendances ( 
    id BIGINT NOT NULL , 
    student_id BIGINT NOT NULL , 
    session_id BIGINT NOT NULL , 
    is_present BOOLEAN NOT NULL , 
    reason VARCHAR(255) NOT NULL , 
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
    PRIMARY KEY (id),
    FOREIGN KEY (student_id) REFERENCES schoolmanagementsystem.students(id),
    FOREIGN KEY (session_id) REFERENCES schoolmanagementsystem.sessions(id)
) ENGINE = InnoDB;