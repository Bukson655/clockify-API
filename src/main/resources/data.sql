INSERT INTO "user"
    (id, uuid, login, first_name, last_name, user_role, password, email, cost_per_hour)
VALUES
    (nextval('hibernate_sequence'), uuid_generate_v4(), 'admin','Sławomir', 'Błaszkiewicz', 'ADMIN', 'admin', 'blaszkiewiczslawomir@gmial.com', 100.00),
    (nextval('hibernate_sequence'), uuid_generate_v4(), 'mati','Mateusz', 'Kowalski', 'MANAGER', 'mati123', 'mati@gmial.com', 105.00),
    (nextval('hibernate_sequence'), uuid_generate_v4(), 'karolina','Karolina', 'Nowak', 'USER', 'karokaro', 'karolina@gmial.com', 80.00),
    (nextval('hibernate_sequence'), uuid_generate_v4(), 'andrzej','Andrzej', 'Andrzewicz', 'USER', 'andrzej123', 'andrzejewicz@onet.pl', 90.00),
    (nextval('hibernate_sequence'), uuid_generate_v4(), 'piotr','Piotr', 'Frączewski', 'USER', 'fracek99', 'p.fraczewski@wp.com', 85.00);

INSERT INTO "project"
    (id, uuid, title, description, start_date, end_date, current_spending, budget)
VALUES
    (nextval('hibernate_sequence'), uuid_generate_v4(), 'Projekt Drzewo','Szczegóły tajne jak życie youtubera!', '2022-04-20', '2022-09-22', 100000,500000.00),
    (nextval('hibernate_sequence'), uuid_generate_v4(), 'Staż','3 tygodnie stażu. Własny projekt', '2022-06-20', '2022-07-08', 0, 10000.00),
    (nextval('hibernate_sequence'), uuid_generate_v4(), 'NoNameProject ','', '2021-11-19', '2022-03-03', 859354, 1000000.00);

INSERT INTO "project_user"
    (project_id, user_id)
VALUES
    (30, 25),
    (30, 26),
    (30, 27),
    (31, 27),
    (31, 28);

INSERT INTO "record"
    (id, uuid, start_date_time, end_date_time, description, project_id, user_id)
VALUES
    (nextval('hibernate_sequence'), uuid_generate_v4(), '2022-06-06 8:00','2022-06-06 12:00', 'testowanie', 30, 25),
    (nextval('hibernate_sequence'), uuid_generate_v4(), '2022-06-06 10:00','2022-06-06 16:00', '', 30, 26),
    (nextval('hibernate_sequence'), uuid_generate_v4(), '2022-06-06 8:00','2022-06-06 16:00', 'praca', 30, 27),
    (nextval('hibernate_sequence'), uuid_generate_v4(), '2022-06-07 8:00','2022-06-07 15:45', 'praca i jeszcze raz praca', 31, 27);