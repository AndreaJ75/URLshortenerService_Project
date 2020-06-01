insert into app_user (id, creation_date, email, first_name, name, uid, update_date) values (nextval('app_user_seq'), '2020-05-22T00:00:000', 'alice.cooper@gmail.com', 'alice', 'Cooper', 'alice', '2020-05-22T00:00:000');
insert into app_user (id, creation_date, email, first_name, name, uid, update_date) values (nextval('app_user_seq'), '2020-05-22T00:00:000', 'lenny.kravitz@gmail.com', 'lenny', 'Kravitz', 'lenny', '2020-05-22T00:00:000');
insert into app_user (id, creation_date, email, first_name, name, uid, update_date) values (nextval('app_user_seq'), '2020-05-22T00:00:000', 'celia.cruz@gmail.com', 'celia', 'Cruz', 'celia', '2020-05-22T00:00:000');
insert into app_user (id, creation_date, email, first_name, name, uid, update_date) values (nextval('app_user_seq'), '2020-05-22T00:00:000', 'liza.minelli@gmail.com', 'liza', 'Minelli', 'liza', '2020-05-22T00:00:000');
insert into app_user (id, creation_date, email, first_name, name, uid, update_date) values (nextval('app_user_seq'), '2020-05-22T00:00:000', 'yves.leconte@gmail.com', 'yves', 'Leconte', 'yves', '2020-05-22T00:00:000');

insert into app_user_authority (app_user_id, authority_id) values (9,2);
insert into app_user_authority (app_user_id, authority_id) values (10,2);
insert into app_user_authority (app_user_id, authority_id) values (11,2);
insert into app_user_authority (app_user_id, authority_id) values (12,2);
insert into app_user_authority (app_user_id, authority_id) values (13,2);

insert into url_link(id, click_number, creation_date, expiration_date, max_click_number, update_date, url_long, url_password, url_short_key, app_user_id) values (nextval('url_link_seq'), 0, '2020-05-22T00:00:000', '2020-06-01T00:00:000', 500, '2020-05-22T00:00:000', 'https://cuisine.larousse.fr/recette/sauce-bechamel-et-sauce-mornay', '', 'Aaaaaaaa', 5);
insert into url_link(id, click_number, creation_date, expiration_date, max_click_number, update_date, url_long, url_password, url_short_key, app_user_id) values (nextval('url_link_seq'), 0, '2020-05-22T00:00:000', '2020-06-01T00:00:000', 500, '2020-05-22T00:00:000', 'https://www.marmiton.org/recettes/recette_sauce-tomate-a-tout-faire-de-jacqueline_36550.aspx', '', 'Bbbbbbbb', 5);
insert into url_link(id, click_number, creation_date, expiration_date, max_click_number, update_date, url_long, url_password, url_short_key, app_user_id) values (nextval('url_link_seq'), 0, '2020-05-22T00:00:000', '2020-06-01T00:00:000', 500, '2020-05-22T00:00:000', 'https://www.marmiton.org/recettes/recette_creme-anglaise-reussie-onctueuse-a-souhait_37391.aspx', '', 'Cccccccc', 5);
insert into url_link(id, click_number, creation_date, expiration_date, max_click_number, update_date, url_long, url_password, url_short_key, app_user_id) values (nextval('url_link_seq'), 0, '2020-05-22T00:00:000', '2020-06-01T00:00:000', 500, '2020-05-22T00:00:000', 'https://www.marmiton.org/recettes/recette_sauce-hollandaise-facile_30941.aspx', '', 'Dddddddd', 7);
insert into url_link(id, click_number, creation_date, expiration_date, max_click_number, update_date, url_long, url_password, url_short_key, app_user_id) values (nextval('url_link_seq'), 0, '2020-05-22T00:00:000', '2020-06-01T00:00:000', 500, '2020-05-22T00:00:000', 'https://www.marmiton.org/recettes/recherche.aspx?aqt=sauce-au-poivre', '', 'Eeeeeeee', 5);
