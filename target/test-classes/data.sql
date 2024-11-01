-- Create person table
CREATE TABLE persons
(
    id 	             INT PRIMARY KEY AUTO_INCREMENT,
    name		     VARCHAR(50),
    city  	         VARCHAR(50),
    phone_number     VARCHAR(50)
);
-- Insert data
INSERT INTO persons(name, city, phone_number)
VALUES ('jhon Doe','Paris','123-456-7890'),
       ('jane Smith','Marseille','123-456-7841'),
       ('Bob Gartner','Lyon','111-222-7841');

