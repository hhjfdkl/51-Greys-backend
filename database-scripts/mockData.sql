INSERT INTO location(location_name, province, country, longitude, latitude)
VALUES
  ('Area 51', 'Nevada', 'United States of America', 37.24, -115.79)
, ('Bermuda Triangle', 'Atlantic Ocean', 'n/a', 25.00, -71.00)
, ('The Great Pyramids', 'Giza', 'Egypt', 29.97, 31.13)
, ('Point Nemo', 'Pacific Ocean', 'n/a', -48.52, -123.23)
, ('Pimpirev Ice Wall', 'Livingston Island', 'Antarctica', -62.37, -60.24)
, ('Roswell', 'New Mexico', 'United States of America', 34.02, -84.36)
, ('Stonehenge', 'Wiltshire', 'England', 51.10, -1.49)
, ('Nazca Lines', 'Nazca', 'Peru', -14.73, -75.13)
, ('Easter Island', 'Easter Island', 'Chile', -27.11, -109.34)
, ('Himeji Castle', 'Hyougo', 'Japan', 34.83, 134.69)
, ('Mount Osore', 'Aomori', 'Japan', 41.30, 141.08)
, ('The Vatican', 'Vatican City', 'The Vatican', 41.90, 12.45)
, ('New York', 'New York', 'United States of America', 40.71, -74.00)
, ('Moscow', 'Moscow', 'Russia', 55.75, 37.61)
, ('Paris', 'Paris', 'France', 48.85, 2.35)
, ('Beijing', 'Heibei', 'China', 39.90, 116.40)
, ('Los Angeles', 'California', 'United States of America', 34.05, -118.24)
, ('Tokyo', 'Tokyo', 'Japan', 35.67, 139.65)
, ('Orlando', 'Florida', 'United States of America', 28.53, -81.37)
, ('London', 'London', 'England', 51.50, -0.12)
, ( 'Cincinnati' , 'Ohio', 'United States of America', 39.10, -84.51)
;


INSERT INTO employee (first_name, last_name, email, phone_number, occupation, clearance_level, location_id, img)
VALUES 
  ('Nate','Koroman','nk@fedmail.net','111-111-1111','Fry Cook',5, 1,null)
, ('Bob','Dole','bdole@51test.gov','735-894-4900','Administration and Management', 1, 2,'https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Ks_1996_dole.jpg/480px-Ks_1996_dole.jpg')
, ('Jim','Smith','jsmith@gmail.org','222-222-2222','Security Guard', 4, 1,null)
, ('Serena','Williams','serwil@earthlink.gov','833-671-1781','Science and Research', 1, 3, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSFfjb2I0jARffSB43NMK7HC4IqVwCGC7e-fw&s')
, ('Kelsey','Williams','kw@yahoo.net','333-333-3333','IT', 3, 1, 'https://www.shutterstock.com/shutterstock/photos/1865153395/display_1500/stock-photo-portrait-of-young-smiling-woman-looking-at-camera-with-crossed-arms-happy-girl-standing-in-1865153395.jpg')
, ('Lindsay','Lohan','llhan@gmail.gov','645-930-6207','Science and Research', 1, 4, 'https://upload.wikimedia.org/wikipedia/commons/thumb/d/d6/Lindsay_Lohan_in_2023_%28cropped%29.jpg/495px-Lindsay_Lohan_in_2023_%28cropped%29.jpg')
, ('Shawn','Stevens','sste@yahoo.net','444-444-4444','Janitor', 5, 1, 'https://media.istockphoto.com/id/1364917563/photo/businessman-smiling-with-arms-crossed-on-white-background.jpg?s=612x612&w=0&k=20&c=NtM9Wbs1DBiGaiowsxJY6wNCnLf0POa65rYEwnZymrM=')
, ('Billy','Mayes','mayes@sec.gov','859-876-7250','Security and Compliance', 1, 5, 'https://people.com/thmb/XUoRFER8lu0CDhbFFtT0dfbC5Oc=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc():focal(119x0:121x2)/billy-mays-320-484e91ecfa0447c9a2c1326b141e6a68.jpg')
, ('Bill','Deters','wdeters@gmail.org','555-555-5555','Security Guard', 4, 1, null)
, ('Bobby','Johnson','wjohnson@gmail.org','666-666-6666','Science and Research', 2, 9, 'https://st2.depositphotos.com/2931363/6569/i/950/depositphotos_65699901-stock-photo-black-man-keeping-arms-crossed.jpg')
, ('Sephanie','Jacobs','sj@yahoo.net','777-777-7777','Security Guard', 4, 1, null)
;

INSERT INTO project (codename, description, min_clearance, priority, img)
VALUES
  ('ACFs -- Research','', 2,'Medium',null)
, ('FRPc -- Containment III','', 4,'Very High',null)
, ('FRPr -- Containment I','', 4,'Low',null)
, ('ACFw -- Research','', 2,'High',null)
, ('Prepare Food','Cook the burgers', 5,'Low','https://www.shutterstock.com/image-photo/classic-hamburger-stock-photo-isolated-600nw-2282033179.jpg')
;

