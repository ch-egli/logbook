-- benutzer for logbook application
-- password is stored as a bcrypt hash calculated by https://www.dailycred.com/article/bcrypt-calculator

INSERT INTO benutzer VALUES(default, 'zoe', '$2a$04$alCNYl6zQ012IcdtlcY3DuifdmXiAOQZXJY7.IgSc50YG8sLZytO2', 'athlet', 'Zoe', 'Egli', null, null, null);
INSERT INTO benutzer VALUES(default, 'liv', '$2a$04$2etITmFEzkCZqPjgxb4JHOOUwDvTZStsjpYGM0ep0qDzrMznBSYZK', 'athlet', 'Liv', 'Egli', null, null, null);
INSERT INTO benutzer VALUES(default, 'jojo', '$2a$04$j0rpsAv.Dz4fTm8rRVfZb.aBmQIjCPVaFrok1fTLzLKStIOa.OkcC', 'athlet,admin', 'Joelle', 'Egli', null, null, null);
INSERT INTO benutzer VALUES(default, 'chrigu', '$2a$04$rHEt7ejCf3T6LtM1OinHEepvjoKlj7lY6GVdDquYrFHdvASq8wnXO', 'athlet,admin', 'Christian', 'Egli', null, null, null);

INSERT INTO benutzer VALUES(default, 'joelle', '$2a$04$h0wy./H0dMlrvbxe56Bk1.BGjdh4sQ2TZa5/lUxLOYGBQp8.kdwSa', 'athlet', 'Joelle', 'Niederberger', null, null, null);  -- 94
INSERT INTO benutzer VALUES(default, 'maex', '$2a$04$H3Q9lkcqFz3et4WC/ButjO8pl/hANxTfVD6yPfLHv7wWfr/wUyWWS', 'athlet,admin', 'Mäx', 'Grossmann', null, null, null); -- 99

