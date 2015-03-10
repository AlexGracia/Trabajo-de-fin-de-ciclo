@echo off
title Drop database alex_gracia
cd %userprofile%\Desktop\AlexGracia
c:\xampp\mysql\bin\mysql.exe -uroot < database\SQL\drop_database.sql
exit