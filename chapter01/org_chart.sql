CREATE TABLE org_chart (
employee_id           SMALLINT NOT NULL,
manager_employee_id   SMALLINT NULL,
employee_name         VARCHAR(20) NOT NULL
);

insert into org_chart (employee_id, manager_employee_id, employee_name) values
(1, 3, 'You'), (2, 3, 'Co-Worker'), (3, 4, 'Team Lead'), (4, 5, 'Manager #2'),
(5, 8, 'VP'), (6, 5, 'Manager #1'), (7, 5, 'Manager #3'), (8, NULL, 'President/CEO');


WITH RECURSIVE org AS (
     SELECT employee_id, manager_employee_id, employee_name, 1 AS level
     FROM org_chart
UNION
     SELECT e.employee_id, e.manager_employee_id, e.employee_name, m.level + 1 AS level
     FROM org_chart AS e
INNER JOIN org AS m ON e.manager_employee_id = m.employee_id
) SELECT employee_id, manager_employee_id, employee_name 
FROM org ORDER BY level ASC;


