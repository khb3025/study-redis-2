select * from stocks;
insert into stocks(quantity)
values( 10000 );
commit;
truncate stocks;


SHOW VARIABLES LIKE 'sql_safe_updates';