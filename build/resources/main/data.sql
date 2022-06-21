INSERT INTO "user"
     (uuid, login, first_name, last_name, user_role, password, email, cost_per_hour)
 VALUES
     (uuid_generate_v4(), 'admin','Sławomir', 'Błaszkiewicz', 'ADMIN', 'admin', 'blaszkiewiczslawomir@gmial.com', 100.00),
     (uuid_generate_v4(), 'mati','Mateusz', 'Kowalski', 'ADMIN', 'mati123', 'mati@gmial.com', 110.00),
     (uuid_generate_v4(), 'karolina','Karolina', 'Nowak', 'USER', 'karokaro', 'karolina@gmial.com', 80.00);