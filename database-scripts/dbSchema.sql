DROP TABLE IF EXISTS project, employee, location, clearance CASCADE;

CREATE TABLE clearance (
	  clearance_level INT
    , clearance_type VARCHAR(32)
    
    , CONSTRAINT PRIMARY KEY (clearance_level)
    , CONSTRAINT clearance_level_ck CHECK (clearance_level IN (1, 2, 3, 4, 5))
);

INSERT INTO clearance (clearance_level, clearance_type)
VALUES
  (1, 'Top Secret')
, (2, 'Secret')
, (3, 'Confidential')
, (4, 'Q Clearance')
, (5, 'L Clearance')
;



CREATE TABLE location (
	  location_id INT AUTO_INCREMENT
    , location_name VARCHAR(128) UNIQUE
    , province VARCHAR (128)
    , country VARCHAR (128)
    , longitude DEC(4,2)
    , latitude DEC(5,2)
    
    , CONSTRAINT PRIMARY KEY (location_id)
);



CREATE TABLE project (
	  project_id INT AUTO_INCREMENT
	, codename VARCHAR(256) NOT NULL
    , description VARCHAR(8192)
    , min_clearance INT
    , priority VARCHAR(64)
--  , personnel INT 			--This seems like a count since it's a number in the model, but no need to store in db I think. Will review later
    , img VARCHAR(4096)
    
    , CONSTRAINT PRIMARY KEY (project_id)
    , CONSTRAINT FOREIGN KEY (min_clearance) REFERENCES clearance (clearance_level)
);

CREATE TABLE employee (
	  employee_id VARCHAR(64)
    , first_name VARCHAR(128) NOT NULL
    , last_name VARCHAR(128) NOT NULL
    , email VARCHAR(256)
    , phone_number VARCHAR(16)
    , occupation VARCHAR(64)
    , clearance_level INT
    , location_id INT
    , project_id INT
    , img VARCHAR(4096)
    
    , CONSTRAINT PRIMARY KEY (employee_id)
    , CONSTRAINT FOREIGN KEY (project_id) REFERENCES project (project_id)
    , CONSTRAINT FOREIGN KEY (clearance_level) REFERENCES clearance (clearance_level)
    , CONSTRAINT FOREIGN KEY (location_id) REFERENCES location (location_id)
);