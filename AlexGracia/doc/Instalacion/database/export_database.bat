@echo off
title Base de datos alex_gracia
echo Exportando...
c:\xampp\mysql\bin\mysqldump.exe -uroot -B alex_gracia > database\SQL\full_database.sql
exit