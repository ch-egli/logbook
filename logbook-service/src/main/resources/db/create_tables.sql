CREATE TABLE benutzer (
  id             SERIAL PRIMARY KEY,
  benutzername   TEXT NOT NULL UNIQUE,
  passwort       TEXT,
  rollen         TEXT,
  vorname        TEXT,
  nachname       TEXT,
  email          TEXT,
  telefon        TEXT,
  mobile         TEXT
);

CREATE TABLE workout (
  id             SERIAL PRIMARY KEY,
  benutzername   TEXT NOT NULL,
  datum		     DATE NOT NULL,
  ort            TEXT,
  wettkampf      TEXT,
  schlaf         INT,
  gefuehl        INT,
  lead           INT,
  bouldern       INT,
  campus         INT,
  kraftraum      INT,
  dehnen         INT,
  mentaltraining INT,
  geraete        TEXT,
  routen         TEXT,
  art            TEXT,
  zuege          TEXT,
  wiederholungen INT,
  bloecke        INT,
  serien         INT,
  pausen         INT,
  belastung      INT,
  zuege12        INT,
  zuege23        INT,
  zuege34        INT,
  trainingszeit  INT,
  sonstiges      TEXT
);

CREATE TABLE status (
  id             SERIAL PRIMARY KEY,
  benutzername   TEXT NOT NULL,
  datum		     DATE NOT NULL,
  schlaf         NUMERIC(3,1),
  gefuehl        NUMERIC(3,1),
  bemerkung      TEXT
);

CREATE TABLE requestlog (
  id             SERIAL PRIMARY KEY,
  datum		     TIMESTAMP NOT NULL,
  benutzer       TEXT NOT NULL,
  uriFilter      TEXT,
  message        TEXT
);
