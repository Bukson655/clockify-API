INSERT INTO "user"
    (id, uuid, login, first_name, last_name, user_role, password, email, cost_per_hour)
VALUES
    (nextval('hibernate_sequence'), uuid_generate_v4(), 'admin','Sławomir', 'Błaszkiewicz', 'ADMIN', 'admin', 'blaszkiewiczslawomir@gmial.com', 100.00),
    (nextval('hibernate_sequence'), uuid_generate_v4(), 'mati','Mateusz', 'Kowalski', 'MANAGER', 'mati123', 'mati@gmial.com', 105.00),
    (nextval('hibernate_sequence'), uuid_generate_v4(), 'karolina','Karolina', 'Nowak', 'USER', 'karokaro', 'karolina@gmial.com', 80.00),
    (nextval('hibernate_sequence'), uuid_generate_v4(), 'andrzej','Andrzej', 'Andrzewicz', 'USER', 'andrzej123', 'andrzejewicz@onet.pl', 90.00),
    (nextval('hibernate_sequence'), uuid_generate_v4(), 'piotr','Piotr', 'Frączewski', 'USER', 'fracek99', 'p.fraczewski@wp.com', 85.00);

INSERT INTO "project"
    (id, uuid, title, description, start_date, end_date, budget, budget_use)
VALUES
    (nextval('hibernate_sequence'), uuid_generate_v4(), 'Projekt Drzewo','Szczegóły tajne jak życie youtubera!', '2022-04-20', '2022-09-22',5000.00, 33.90),
    (nextval('hibernate_sequence'), uuid_generate_v4(), 'Staż','3 tygodnie stażu. Własny projekt', '2022-06-20', '2022-07-08', 10000.00, 4.80),
    (nextval('hibernate_sequence'), uuid_generate_v4(), 'NoNameProject ','', '2021-11-19', '2022-03-03', 90000.00, 0.00);

INSERT INTO "project_user"
    (project_id, user_id)
VALUES
    (6, 1),
    (6, 2),
    (6, 3),
    (7, 3),
    (7, 4);

INSERT INTO "record"
    (id, uuid, start_date_time, end_date_time, description, cost_of_work, project_id, user_id)
VALUES
    (nextval('hibernate_sequence'), uuid_generate_v4(), '2022-06-06 8:00','2022-06-06 12:00', 'testowanie', 400, 6, 1),
    (nextval('hibernate_sequence'), uuid_generate_v4(), '2022-06-06 10:00','2022-06-06 17:00', '', 735, 6, 2),
    (nextval('hibernate_sequence'), uuid_generate_v4(), '2022-06-06 9:00','2022-06-06 16:00', 'praca', 560, 6, 3),
    (nextval('hibernate_sequence'), uuid_generate_v4(), '2022-06-07 10:00','2022-06-07 16:00', 'praca i jeszcze raz praca', 480, 7, 3);