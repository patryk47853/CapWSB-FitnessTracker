-- USERS
INSERT INTO users (id, first_name, last_name, birthdate, email)
VALUES (1, 'Anna', 'Nowak', '1990-04-12', 'anna.nowak@example.com'),
       (2, 'Piotr', 'Kowalski', '1985-09-25', 'piotr.kowalski@example.com'),
       (3, 'Marta', 'Wiśniewska', '1995-02-10', 'marta.wisniewska@example.com');

-- STATISTICS (OneToOne relation with USERS)
INSERT INTO statistics (id, user_id, total_trainings, total_distance, total_calories_burned)
VALUES (1, 1, 25, 120.5, 7800),
       (2, 2, 40, 250.7, 12100),
       (3, 3, 12, 60.2, 3900);

-- HEALTH_METRICS (ManyToOne relation with USERS)
INSERT INTO health_metrics (id, user_id, date, weight, height, heart_rate)
VALUES (1, 1, '2025-11-01', 65.0, 170.0, 72),
       (2, 1, '2025-11-05', 64.8, 170.0, 70),
       (3, 2, '2025-11-02', 82.5, 180.0, 75),
       (4, 2, '2025-11-06', 82.2, 180.0, 74),
       (5, 3, '2025-11-03', 58.3, 165.0, 68);
