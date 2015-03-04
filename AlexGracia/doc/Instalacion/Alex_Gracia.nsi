;Variables globales
!define NAME "AlexGracia"
!define OUTFILE "AlexGracia_setup.exe"
!define INSTALLDIR "$DESKTOP\AlexGracia"
!define ICON "AlexGracia.ico"
!define UNICON "AlexGracia_uninstall.ico"
!define INSTDIRMANUALES "$INSTDIR\manuales"
!define LICENCIA "license.txt"

; Definitions for Java 7.0
!define JRE_VERSION "1.7.0_51-b13"
!define JRE "jre-7u51-windows-i586.exe"
; !define JRE_URL "http://javadl.sun.com/webapps/download/AutoDL?BundleId=83383"

; Definitions for MySQL Installer 5.6.17
!define MYSQL_VERSION "5.6.17"
!define MYSQL "mysql-installer-community-5.6.17.0.msi"
; !define MYSQL_URL "http://dev.mysql.com/get/Downloads/MySQLInstaller/mysql-installer-community-5.6.17.0.msi"

SetCompressor /SOLID lzma
InstType "Completa"
InstType "Peque�a"

;Include Modern UI

!include "MUI2.nsh"

;--------------------------------
;GENERAL

;Name and file
Name ${NAME}
OutFile ${OUTFILE}

;CARPETA DE INSTALACI�N POR DEFECTO
InstallDir ${INSTALLDIR}

;--------------------------------
;Version Information

VIProductVersion "1.0.0.1"

VIAddVersionKey "ProductName" ${NAME}
VIAddVersionKey "Comments" "Proyecto de fin de ciclo (DAM)"
VIAddVersionKey "CompanyName" "Alex Gracia"
VIAddVersionKey "LegalTrademarks" "${NAME} is a trademark of Alex Gracia"
VIAddVersionKey "LegalCopyright" "Copyright (C) 2015 Alex Gracia"
VIAddVersionKey "FileDescription" ${NAME}
VIAddVersionKey "FileVersion" "1.0.1"

AutoCloseWindow true

;REQUERIMIENTO DE PRIVILEGIOS DE APLICACION PARA WINDOWS
RequestExecutionLevel user

;Memento Settings
; !define MEMENTO_REGISTRY_ROOT HKLM
; !define MEMENTO_REGISTRY_KEY "Software\Microsoft\Windows\CurrentVersion\Uninstall\NSIS"

;--------------------------------
;CONFIGURACION DEL INTERFACE

!define MUI_ABORTWARNING
!define MUI_COMPONENTSPAGE_SMALLDESC
!define MUI_ICON ${ICON}
!define MUI_UNICON ${UNICON}

;--------------------------------
;PAGINAS

!insertmacro MUI_PAGE_LICENSE ${LICENCIA}
!insertmacro MUI_PAGE_COMPONENTS
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES

!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES

;--------------------------------
;LENGUAJES

!insertmacro MUI_LANGUAGE "Spanish"

;--------------------------------
;SECCION DEL INSTALADOR

; Programa
SectionGroup /e "Programa" Sec1

	Section "AlexGracia.jar" Sec1JAR
		SectionIn 1
		SetOutPath $INSTDIR

		File AlexGracia.jar
	SectionEnd

	Section "database" Sec1DATABASE
		SectionIn 1 2
		SetOutPath $INSTDIR

		File /r database
	SectionEnd

	Section "report" Sec1REPORT
		SectionIn 1 2
		SetOutPath $INSTDIR

		File /r report
	SectionEnd

SectionGroupEnd

; Manuales
SectionGroup "Manuales" Sec2

	Section "Manual de instalacion" Sec2INSTALACION
		SectionIn 1
		SetOutPath ${INSTDIRMANUALES}

		File "manuales\Manual de instalacion.pdf"
	SectionEnd
	
	Section "Manual de usuario" Sec2USUARIO
		SectionIn 1
		SetOutPath ${INSTDIRMANUALES}

		File "manuales\Manual de usuario.pdf"
	SectionEnd

SectionGroupEnd


; Licencia
Section "Licencia" Sec3
	SectionIn 1 2 RO
	SetOutPath $INSTDIR
	File ${LICENCIA}
SectionEnd

Section "Desinstalador" SecUninstall

	SectionIn 1 2 RO
	WriteUninstaller "$INSTDIR\Desinstalador.exe"

SectionEnd

; Section "JRE" SecJRE

	; SectionIn 1
	; SetOutPath ${INSTDIRINSTALADORES}
	; File ${FILEINSTALADORES}\${JRE}
	; Exec ${INSTDIRINSTALADORES}\${JRE}

; SectionEnd

; Section "MYSQL" SecMYSQL

	; SectionIn 1
	; SetOutPath ${INSTDIRINSTALADORES}
	; File ${FILEINSTALADORES}\${MYSQL}
	; Exec ${INSTDIRINSTALADORES}\${MYSQL}

; SectionEnd

;--------------------------------
;DESCRIPCIONES

	;ASIGNAR LAS CADENAS DE LENGUAJE A LAS SECCIONES
	!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN

	; Programa
	!insertmacro MUI_DESCRIPTION_TEXT ${Sec1} "Programa: contiene el jar ejecutable y ficheros necesarios para su ejecuci�n."
	!insertmacro MUI_DESCRIPTION_TEXT ${Sec1JAR} "AlexGracia.jar: es el jar ejecutable."
	!insertmacro MUI_DESCRIPTION_TEXT ${Sec1DATABASE} "database: contiene .bat y .sql."
	!insertmacro MUI_DESCRIPTION_TEXT ${Sec1REPORT} "report: contiene .jasper necesarios para la opci�n informe del programa."

	; Manuales
	!insertmacro MUI_DESCRIPTION_TEXT ${Sec2} "Manuales: contiene los manuales de instalacion y de usuario."
	!insertmacro MUI_DESCRIPTION_TEXT ${Sec2INSTALACION} "Instalaci�n: manual de instalaci�n en formato pdf."
	!insertmacro MUI_DESCRIPTION_TEXT ${Sec2USUARIO} "Usuario: manual de usuario en formato pdf."
	
	; Licencia
	!insertmacro MUI_DESCRIPTION_TEXT ${Sec3} "Licencia: contiene la licencia en formato txt."
	
	!insertmacro MUI_DESCRIPTION_TEXT ${SecUninstall} "Desinstalador: programa encargado de la desinstalaci�n. Puede borrar archivos importantes si se modifica la carpeta de instalaci�n."

	; !insertmacro MUI_DESCRIPTION_TEXT ${SecJRE} "JRE: contiene el instalador de Java, versi�n ${JRE_VERSION}."
	; !insertmacro MUI_DESCRIPTION_TEXT ${SecMYSQL} "MySQL: contiene el instalador de MySQL, versi�n ${MYSQL_VERSION}."

	!insertmacro MUI_FUNCTION_DESCRIPTION_END

;--------------------------------
;SECCION DEL DESINSTALADOR

Section Uninstall
	;A�ADIR LOS FICHEROS A DESINSTALAR AQUI...
	; Raiz
	RMDir /r $INSTDIR
	; A�adiendo /r borra todo, no se aconseja su uso.
	
	SetAutoClose true

SectionEnd