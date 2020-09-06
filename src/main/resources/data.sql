INSERT INTO DOCTOR (FIRST_NAME, LAST_NAME) VALUES 
('Andrew','Smith'),
('John','Smith'),
('Tom','Bradley'),
('Terry','Cooper'),
('Daniel','Doe'),
('Andrew','Smith'),
('Sandy','Smith');


INSERT INTO APPOINTMENT (DATE , PATIENT_FIRST_NAME , PATIENT_LAST_NAME , KIND, DOCTOR_ID ) VALUES 
(CURRENT_TIMESTAMP() + 3 , 'Sam', 'Smith', 0, 1),
(CURRENT_TIMESTAMP() + 3 , 'Jane', 'Smith', 0, 1),
(CURRENT_TIMESTAMP() + 3 , 'Sam', 'Smith', 0, 1),
(CURRENT_TIMESTAMP() + 3 , 'Laura', 'Cooper', 0, 2),
(CURRENT_TIMESTAMP() + 3 , 'Jane', 'Smith', 1, 2),
(CURRENT_TIMESTAMP() + 4 , 'Jane', 'Smith', 1, 2),
(CURRENT_TIMESTAMP() + 5 , 'jane', 'Smith', 1, 2),
(CURRENT_TIMESTAMP() + 3 , 'Will', 'Knuth', 0, 3),
(CURRENT_TIMESTAMP() + 3 , 'Henry', 'Davidson', 0, 3),
(CURRENT_TIMESTAMP() + 3 , 'Sam', 'Smith', 1,5),
(CURRENT_TIMESTAMP() + 3 , 'Sam', 'Smith', 1,5),
(CURRENT_TIMESTAMP() + 3 , 'Sam', 'Smith', 0, 4),
(CURRENT_TIMESTAMP() + 3 , 'Sam', 'Smith', 1,4),
(CURRENT_TIMESTAMP() + 3 , 'Sam', 'Smith', 0,6);
