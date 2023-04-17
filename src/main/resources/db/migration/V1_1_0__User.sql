CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
                       user_id varchar(255) PRIMARY KEY DEFAULT uuid_generate_v4(),
                       username CHAR(30),
                       password varchar(255),
                       email CHAR(20),
                       role CHAR(10)
);

INSERT INTO users (username, password, email, role)
VALUES ('admin', '$2a$10$gX0ZpLxOCHGR4axDIUanZOHgn7Pj0Ik5OpvltXgIY6yeMQEHeejzy', 'admin@example.com', 'ADMIN'),
       ('user', '$2y$10$vqT/1Da.ACmmWbIqnbZcLutjxIE5fNcAX196wVo8f/OxTHw/IUk4y', 'user@example.com', 'USER');

CREATE TABLE IF NOT EXISTS vacancy_card (
                                            vacancy_id varchar(255) PRIMARY KEY,
                                            name varchar(255),
                                            date_of_submitting_for_vacancy timestamp,
                                            date_of_appointment timestamp,
                                            status varchar(255),
                                            text text,
                                            user_id varchar(255) REFERENCES users(user_id)
);

ALTER TABLE vacancy_card
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id)
        REFERENCES users(user_id) ON DELETE CASCADE;


INSERT INTO vacancy_card (vacancy_id, name, date_of_submitting_for_vacancy, date_of_appointment, status, text, user_id)
VALUES
    (uuid_generate_v4(), 'Software Engineer', '2023-04-01 10:00:00', '2023-04-15 10:00:00', 'OFFER', 'We are seeking a skilled software engineer with 5+ years of experience to join our team.', (SELECT user_id FROM users WHERE username = 'admin')),
    (uuid_generate_v4(), 'Marketing Manager', '2023-04-02 10:00:00', '2023-04-16 10:00:00', 'WAITING', 'We are looking for an experienced marketing manager to help drive our growth.', (SELECT user_id FROM users WHERE username = 'admin')),
    (uuid_generate_v4(), 'Product Designer', '2023-04-03 10:00:00', '2023-04-17 10:00:00', 'REFUSED', 'We are seeking a talented product designer to help us create beautiful and intuitive user interfaces.', (SELECT user_id FROM users WHERE username = 'admin')),
    (uuid_generate_v4(), 'Customer Support Specialist', '2023-04-04 10:00:00', '2023-04-18 10:00:00', 'WAITING', 'We are looking for a friendly and empathetic customer support specialist to join our team.', (SELECT user_id FROM users WHERE username = 'user'));


