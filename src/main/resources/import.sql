INSERT INTO position(created_time,updated_at,name) values ('2023-02-02','2023-05-05','ProjectManager');

insert into position_permission_list(position_id, permission_list) values (1, 'ADD_ADDRESS');
insert into position_permission_list(position_id, permission_list) values (1, 'EDIT_ADDRESS');
insert into position_permission_list(position_id, permission_list) values (1, 'DELETE_ADDRESS');
insert into position_permission_list(position_id, permission_list) values (1, 'VIEW_ADDRESS');

insert into position_permission_list(position_id, permission_list) values (1, 'ADD_STORE');
insert into position_permission_list(position_id, permission_list) values (1, 'DELETE_STORE');
insert into position_permission_list(position_id, permission_list) values (1, 'EDIT_STORE');
insert into position_permission_list(position_id, permission_list) values (1, 'VIEW_STORE');
insert into position_permission_list(position_id, permission_list) values (1, 'VIEW_MY_STORE');

insert into position_permission_list(position_id, permission_list) values (1, 'ADD_POSITION');
insert into position_permission_list(position_id, permission_list) values (1, 'DELETE_POSITION');
insert into position_permission_list(position_id, permission_list) values (1, 'EDIT_POSITION');
insert into position_permission_list(position_id, permission_list) values (1, 'VIEW_POSITION');

insert into position_permission_list(position_id, permission_list) values (1, 'ADD_INPUTS');
insert into position_permission_list(position_id, permission_list) values (1, 'DELETE_INPUTS');
insert into position_permission_list(position_id, permission_list) values (1, 'EDIT_INPUTS');
insert into position_permission_list(position_id, permission_list) values (1, 'VIEW_INPUTS');

insert into position_permission_list(position_id, permission_list) values (1, 'ADD_OUTPUTS');
insert into position_permission_list(position_id, permission_list) values (1, 'DELETE_OUTPUTS');
insert into position_permission_list(position_id, permission_list) values (1, 'EDIT_OUTPUTS');
insert into position_permission_list(position_id, permission_list) values (1, 'VIEW_OUTPUTS');

insert into position_permission_list(position_id, permission_list) values (1, 'ADD_WORKER');
insert into position_permission_list(position_id, permission_list) values (1, 'DELETE_WORKER');
insert into position_permission_list(position_id, permission_list) values (1, 'EDIT_WORKER');
insert into position_permission_list(position_id, permission_list) values (1, 'VIEW_MY_WORKERS');
insert into position_permission_list(position_id, permission_list) values (1, 'BLOCK_OR_UNBLOCK');
insert into position_permission_list(position_id, permission_list) values (1, 'CHANGE_MY_PASSWORD');


insert into position_permission_list(position_id, permission_list) values (1, 'ADD_PRODUCT');
insert into position_permission_list(position_id, permission_list) values (1, 'DELETE_PRODUCT');
insert into position_permission_list(position_id, permission_list) values (1, 'EDIT_PRODUCT');
insert into position_permission_list(position_id, permission_list) values (1, 'VIEW_MY_PRODUCTS');
insert into position_permission_list(position_id, permission_list) values (1, 'FILTER_PRODUCTS');


INSERT INTO  workers (id,enabled, fullname, password, salary, username, position_id) values (1,true, 'Bo`riyev Shahboz', '$2a$12$E.uF5SubGbVa00g1P4oGfuv/AoJArTMrx.NC9VsUu2usVUK5I8OIK', 15,'devshaha',1);
--