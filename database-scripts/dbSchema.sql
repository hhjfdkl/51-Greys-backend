DROP TABLE IF EXISTS employee_project, project, employee CASCADE;

CREATE TABLE project (
	  project_id INT AUTO_INCREMENT
	, codename VARCHAR(256) NOT NULL
    , description VARCHAR(8192)
    , min_clearance VARCHAR(64)
    , priority VARCHAR(64)
--  , personnel INT 			--This seems like a count since it's a number in the model, but no need to store in db I think. Will review later
    , img VARCHAR(4096)
    
    , CONSTRAINT PRIMARY KEY (project_id)
);

CREATE TABLE employee (
	  employee_id INT AUTO_INCREMENT
    , first_name VARCHAR(128) NOT NULL
    , last_name VARCHAR(128) NOT NULL
    , email VARCHAR(256)
    , phone_number VARCHAR(16)
    , occupation VARCHAR(64)
    , clearance VARCHAR(64)
    , img VARCHAR(4096)
    
    , CONSTRAINT PRIMARY KEY (employee_id)
);

CREATE TABLE employee_project (
	  employee_id INT
    , project_id INT
    
    , CONSTRAINT PRIMARY KEY (employee_id, project_id)
    , CONSTRAINT FOREIGN KEY (employee_id) REFERENCES employee (employee_id)
    , CONSTRAINT FOREIGN KEY (project_id) REFERENCES project (project_id)
);