-- SI EN Oracle12c :
-- alter session set "_ORACLE_SCRIPT"=true;
CREATE role myrole not identified;
GRANT ALTER session TO myrole;
GRANT CREATE database link TO myrole;
GRANT CREATE session TO myrole;
GRANT CREATE procedure TO myrole;
GRANT CREATE sequence TO myrole;
GRANT CREATE table TO myrole;
GRANT CREATE trigger TO myrole;
GRANT CREATE type to myrole;
GRANT CREATE synonym TO myrole;
GRANT CREATE view TO myrole;
GRANT CREATE job TO myrole;
GRANT CREATE materialized view TO myrole;
GRANT EXECUTE ON sys.dbms_lock TO myrole;
GRANT EXECUTE ON sys.owa_opt_lock TO myrole;