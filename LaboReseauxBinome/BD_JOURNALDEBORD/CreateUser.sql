-- SI EN Oracle12c :
-- alter session set "_ORACLE_SCRIPT"=true;
CREATE USER LaboReseaux identified by oracle default tablespace users temporary tablespace temp profile default account unlock;
ALTER USER LaboReseaux quota unlimited on users;
GRANT myrole TO LaboReseaux;
