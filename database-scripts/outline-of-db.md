# 51_greys_classified_database


## Projects
id (pk)
codename
description
min_clearance
priority
personnel
img
employees

## Employee
id (pk) 
first_name
last_name
email
phone_number
occupation
clearance
img
projects

## task (todo)
id (pk)     - int
name        - varchar
due_date    - (date of some kind)
description - varchar

## Division
(?) Maybe at later implementation

(?)
## Employee Login
id (pk)
name
password (hashed, salted)
