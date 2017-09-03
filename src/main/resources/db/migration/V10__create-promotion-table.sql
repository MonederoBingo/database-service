CREATE TABLE IF NOT EXISTS promotion (
  promotion_id SERIAL PRIMARY KEY,
  company_id   INT,
  client_id    INT,
  description  TEXT    NOT NULL,
  used_points  DECIMAL NOT NULL,
  date         TEXT
);
