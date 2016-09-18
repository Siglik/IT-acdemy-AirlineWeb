INSERT INTO `airline`.`user`
	(`login`,
	`password_sha`,
	`first_name`,
	`last_name`,
	`type`) 
    VALUES 
    ('admin', 
    SHA2('adminUnds&4s>dfuPMdqmx84Yfagt=274bfa#fdsa64q1ADMIN', 256), 
    'Ivan', 
    'Ivanov',
    'admin'),
     ('disp', 
    SHA2('1234Unds&4s>dfuPMdqmx84Yfagt=274bfa#fdsa64q1DISP', 256), 
    'Petr', 
    'Petrov',
    'dispatcher')
    ON DUPLICATE KEY UPDATE password_sha=values(password_sha);