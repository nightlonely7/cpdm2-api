INSERT INTO user (username, password, role, available) VALUES
('quang', '$2a$11$JDbss487mfwgvAzx7g.6L.Y2hXwLh58861Q.wvLKIbfr0b9gDzh3u', 'ROLE_ADMIN', true),
('khanh', '$2a$11$JDbss487mfwgvAzx7g.6L.Y2hXwLh58861Q.wvLKIbfr0b9gDzh3u', 'ROLE_ADMIN', true),
('nhan', '$2a$11$JDbss487mfwgvAzx7g.6L.Y2hXwLh58861Q.wvLKIbfr0b9gDzh3u', 'ROLE_ADMIN', true);

INSERT INTO document (title, summary, detail, created_time, last_modified_time) VALUES
('title1', 'summary1', 'detail1', '2017-09-20 07:22:11', '2017-09-20 07:22:11'),
('title2', 'summary2', 'detail2', '2017-09-20 07:22:12', '2017-09-20 07:22:12');