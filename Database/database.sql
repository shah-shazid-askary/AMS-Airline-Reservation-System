
CREATE DATABASE airlinemanagementsystem;


USE airlinemanagementsystem;


CREATE TABLE login (
    username VARCHAR(20),
    password VARCHAR(20)
);

select * from login;

INSERT INTO login VALUES ('admin', 'admin');

CREATE TABLE passenger (
    name VARCHAR(20),
    nationality VARCHAR(20),
    phone VARCHAR(15),
    address VARCHAR(50),
    nid VARCHAR(20),
    gender VARCHAR(20)
);


select * from passenger;


CREATE TABLE flight (
    f_code VARCHAR(20),
    f_name VARCHAR(20),
    source VARCHAR(40),
    destination VARCHAR(40)
);


INSERT INTO flight VALUES ('1001', 'AI-1001', 'City 1', 'City 2');
INSERT INTO flight VALUES ('1002', 'AI-1002', 'City 2', 'City 1');
INSERT INTO flight VALUES ('1003', 'AI-1003', 'City 2', 'City 3');
INSERT INTO flight VALUES ('1004', 'AI-1004', 'City 3', 'City 2');
INSERT INTO flight VALUES ('1005', 'AI-1005', 'City 3', 'City 4');
INSERT INTO flight VALUES ('1006', 'AI-1006', 'City 4', 'City 3');
INSERT INTO flight VALUES ('1007', 'AI-1007', 'City 4', 'City 5');
INSERT INTO flight VALUES ('1008', 'AI-1008', 'City 5', 'City 4');
INSERT INTO flight VALUES ('1009', 'AI-1009', 'City 5', 'City 1');
INSERT INTO flight VALUES ('1010', 'AI-1010', 'City 1', 'City 5');


select * from flight;


CREATE TABLE reservation (
    PNR VARCHAR(20), 
    TICKET VARCHAR(20),
    nid VARCHAR(20),
    name VARCHAR(20),
    nationality VARCHAR(30),
    address VARCHAR(255),
    gender VARCHAR(255),
    flightname VARCHAR(15),
    flightcode VARCHAR(20),
    src VARCHAR(30),
    des VARCHAR(30),
    ddate VARCHAR(30)
);



select * from reservation;

CREATE TABLE cancel (
    pnr VARCHAR(20),
    name VARCHAR(40),
    cancelno VARCHAR(20),
    fcode VARCHAR(20),
    ddate VARCHAR(30)
);


select * from cancel;


CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50),
    flight_code VARCHAR(20),
    destination VARCHAR(50),
    card_number VARCHAR(16),
    payment_date DATETIME DEFAULT CURRENT_TIMESTAMP
);


select * from payments;





