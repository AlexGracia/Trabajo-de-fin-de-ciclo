title Exportar database alex_gracia
echo off
cls
c:\xampp\mysql\bin\mysqldump.exe -uroot -B alex_gracia > database\SQL\full_database.sql
exit