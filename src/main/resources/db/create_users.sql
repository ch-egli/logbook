-- benutzer for logbook application
-- password is stored as a bcrypt hash calculated by https://www.dailycred.com/article/bcrypt-calculator

INSERT INTO benutzer VALUES(default, 'zoe', '$2a$04$alCNYl6zQ012IcdtlcY3DuifdmXiAOQZXJY7.IgSc50YG8sLZytO2', 'athlet', 'Zoe', 'Egli', null, null, null);
INSERT INTO benutzer VALUES(default, 'liv', '$2a$04$2etITmFEzkCZqPjgxb4JHOOUwDvTZStsjpYGM0ep0qDzrMznBSYZK', 'athlet', 'Liv', 'Egli', null, null, null);
INSERT INTO benutzer VALUES(default, 'jojo', '$2a$04$j0rpsAv.Dz4fTm8rRVfZb.aBmQIjCPVaFrok1fTLzLKStIOa.OkcC', 'athlet', 'Joelle', 'Egli', null, null, null);
INSERT INTO benutzer VALUES(default, 'chrigu', '$2a$04$rHEt7ejCf3T6LtM1OinHEepvjoKlj7lY6GVdDquYrFHdvASq8wnXO', 'athlet,admin', 'Christian', 'Egli', 'christian.egli@gmx.net', null, null);
