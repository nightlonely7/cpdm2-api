INSERT INTO role (name) VALUES ('ROLE_ADMIN'), ('ROLE_MANAGER'), ('ROLE_DOCUMENT_WRITER'), ('ROLE_STAFF');

INSERT INTO user (username, password, role_id, available) VALUES
('quang', '$2a$11$JDbss487mfwgvAzx7g.6L.Y2hXwLh58861Q.wvLKIbfr0b9gDzh3u', 1, true),
('khanh', '$2a$11$JDbss487mfwgvAzx7g.6L.Y2hXwLh58861Q.wvLKIbfr0b9gDzh3u', 2, true),
('nhan', '$2a$11$JDbss487mfwgvAzx7g.6L.Y2hXwLh58861Q.wvLKIbfr0b9gDzh3u', 2, true);

INSERT INTO outsider(code, name, contact_data) VALUES
('code1', 'name1', 'contact_data1'),
('code2', 'name2', 'contact_data2');

INSERT INTO document (code, outsider_id, title, summary, detail, arrival_date, created_time, last_modified_time, started_processing, processed) VALUES
('code1', 1, 'title1', 'summary1111 111111 111111 1111111 11111 1111111 111111111 111111111 11111111 1111 11111 11111111 11111111 111111',
 'detail1', '2017-09-20', '2017-09-20 07:22:11', '2017-09-20 07:22:11', false, false),
('code2', 1, 'title2', 'summary2', 'detail2', '2017-09-20', '2017-09-20 07:22:12', '2017-09-20 07:22:12', false, false);

-- INSERT INTO document_process(name, description, created_time, available) VALUES
-- ('name 1','des 1', '2018-05-08 00:00:00',true),
-- ('name 2','des 2', '2018-05-08 01:00:00',true);