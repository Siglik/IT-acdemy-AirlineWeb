CREATE USER 'airline'@'localhost' IDENTIFIED BY 'NPZfu&;fe#4.D8frqRh4Vbra4a!q';
CREATE USER 'airline'@'%' IDENTIFIED BY 'NPZfu&;fe#4.D8frqRh4Vbra4a!q';
GRANT ALL PRIVILEGES ON airline.* TO 'airline'@'localhost';
GRANT ALL PRIVILEGES ON airline.* TO 'airline'@'%';