create table policy_floor(
	id varchar(50) primary key,
  	policy_id varchar(50),
  	floor_price int,
  	need_set_cpa boolean,
	need_set_cpc boolean,
	is_price_changed boolean,
	under int,
	status boolean,
	last_user_name varchar(50),
	ptype varchar(2),
	floor_point int,
	type int,
	compare_cabin boolean,
	cpa_d_cpc int
 );
 ALTER TABLE policy_floor ADD INDEX index_policy_id ( policy_id );
 
 create table policy_floor_log(
 	id varchar(50) primary key,
 	policy_floor_id varchar(50),
 	log_date DATETIME,
 	user_name varchar(50),
 	opt_action int,
 	old_value varchar(50),
 	new_value varchar(50)
);
 ALTER TABLE policy_floor_log ADD INDEX index_policy_floor_id ( policy_floor_id );
 
 
 --Y舱全价
  create table y_price(
 	id varchar(50) primary key,
 	flightcode varchar(10),
 	dpt varchar(10),
 	arr varchar(10),
 	price int
  );
  
  
 create table change_price_log(
 	id varchar(50) primary key,
 	policy_id varchar(50),
 	log_date DATETIME,
 	
 	old_returnpoint varchar(10),
 	new_returnpoint varchar(10),
	old_returnprice varchar(10),
	new_returnprice varchar(10),
	
	old_cpc_returnpoint  varchar(10),
	new_cpc_returnpoint  varchar(10),
	old_cpc_returnprice varchar(10),
	new_cpc_returnprice varchar(10),
	
	user_name varchar(50)
	);
  ALTER TABLE change_price_log ADD INDEX index_policy_id ( policy_id );
 
 

 
