; En lugar de $INSTDIR, se puede usar un guión entre comillas.
; Ejemplo: SetOutPath "-", en vez de SetOutPath $INSTDIR

;Variables globales
!define NAME "AlexGracia"
!define OUTFILE "AlexGracia_setup.exe"
!define INSTALLDIR "$DESKTOP\AlexGracia"
!define ICON "AlexGracia.ico"
!define UNICON "AlexGracia_uninstall.ico"
!define INSTDIRMANUALES "$INSTDIR\manuales"
!define URLJAVA "https://java.com/download"
; !define URLXAMPP "https://www.apachefriends.org/es/download.html"
!define URLXAMPP "http://sourceforge.net/projects/xampp/files/latest/download"
!define INSTDIRDATABASE "$INSTDIR\database"

; Definitions for Java 7.0
; !define JRE_VERSION "1.7.0_51-b13"
; !define JRE "jre-7u51-windows-i586.exe"
; !define JRE_URL "http://javadl.sun.com/webapps/download/AutoDL?BundleId=83383"

; Definitions for MySQL Installer 5.6.17
; !define MYSQL_VERSION "5.6.17"
; !define MYSQL "mysql-installer-community-5.6.17.0.msi"
; !define MYSQL_URL "http://dev.mysql.com/get/Downloads/MySQLInstaller/mysql-installer-community-5.6.17.0.msi"

SetCompressor /SOLID lzma
InstType $(tipo1)
InstType $(tipo2)

;Include Modern UI

!include "MUI2.nsh"

;--------------------------------
;GENERAL

;Name and file
Name ${NAME}
OutFile ${OUTFILE}

;CARPETA DE INSTALACIÓN POR DEFECTO
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
CRCCheck on

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

!insertmacro MUI_PAGE_LICENSE $(license)
!insertmacro MUI_PAGE_COMPONENTS
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES

!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES

;--------------------------------
;LENGUAJES

!insertmacro MUI_LANGUAGE "English"
!insertmacro MUI_LANGUAGE "SpanishInternational"
!insertmacro MUI_LANGUAGE "French"

; Traducciones manuales
LangString tipo1 ${LANG_ENGLISH} "Full"
LangString tipo1 ${LANG_SPANISHINTERNATIONAL} "Completa"
LangString tipo1 ${LANG_FRENCH} "Complet"

LangString tipo2 ${LANG_ENGLISH} "Minimal"
LangString tipo2 ${LANG_SPANISHINTERNATIONAL} "Mínima"
LangString tipo2 ${LANG_FRENCH} "Minimal"

LicenseLangString license ${LANG_ENGLISH} "licencias\license.txt"
LicenseLangString license ${LANG_SPANISHINTERNATIONAL} "licencias\license-spanishinternational.txt"
LicenseLangString license ${LANG_FRENCH} "licencias\license-french.txt"

;--------------------------------
;FUNCIONES

Function .onInit

	; Iniciar servicio mysql
	Exec '"C:\xampp\mysql\bin\mysqld.exe"'

	;Language selection dialog
	!insertmacro MUI_LANGDLL_DISPLAY
	
FunctionEnd

Function .onGUIEnd ; se ejecuta al cerrarse el instalador

	IfFileExists $PROGRAMFILES\Java +3 0
	; El +3 salta a la instrucción 3, contando desde esta linea.
	MessageBox MB_OK "Debe instalar Java (${URLJAVA})\
	$\nSe abrirá la página web.\
	$\nSi tiene Java instalado en una ubicación distinta a '$PROGRAMFILES\Java', disculpe las molestias."
	ExecShell "open" "${URLJAVA}"
	; ExecShell, en este caso abre el navegador por defecto con la URL ${URLJAVA}.

	IfFileExists C:\xampp +3 0
	MessageBox MB_OK "Debe instalar xampp (${URLXAMPP})\
	$\nSe abrirá la página web.\
	$\nSi tiene xampp instalado en una ubicación distinta a 'C:\xampp', la opción 'Exportar BD' no funcionará."
	ExecShell "open" "${URLXAMPP}"

FunctionEnd

;--------------------------------
;SECCION DEL INSTALADOR

; Programa
SectionGroup /e "Programa" Sec1

	Section "AlexGracia.jar" Sec1JAR
		SectionIn 1 2
		SetOutPath $INSTDIR

		File AlexGracia.jar
		
		File AlexGracia.ico

		; Acceso directo
		CreateShortCut "$DESKTOP\AlexGracia.lnk" "$INSTDIR\AlexGracia.jar" "" "$INSTDIR\AlexGracia.ico" 0 SW_SHOWNORMAL ALT|CONTROL|F8 "Ejecuta $INSTDIR\AlexGracia.jar"
	SectionEnd

	Section "database" Sec1DATABASE
		SectionIn 1 2
		SetOutPath $INSTDIR

		File /r database

		; Instalar database
		ExecWait '"${INSTDIRDATABASE}\install_database.bat"'
		; Parar servicio mysql
		Exec '"C:\xampp\mysql_stop.bat"'
	SectionEnd

	Section "report" Sec1REPORT
		SectionIn 1 2
		SetOutPath $INSTDIR

		File /r report
	SectionEnd

SectionGroupEnd

; Manuales
SectionGroup "Manuales" Sec2

	Section "Manual de instalación" Sec2INSTALACION
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
Section ""

	SetOutPath $INSTDIR
	IntCmp $LANGUAGE ${LANG_SPANISHINTERNATIONAL} 0 +3 +3
		File /oname=license.txt licencias\license-spanishinternational.txt
		Return
	IntCmp $LANGUAGE ${LANG_FRENCH} 0 +3 +3
		File /oname=license.txt licencias\license-french.txt
		Return

	File licencias\license.txt

SectionEnd

; Desinstalador
Section "" ; empty string makes it hidden

	WriteUninstaller "$INSTDIR\AlexGracia_uninstall.exe"

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
	!insertmacro MUI_DESCRIPTION_TEXT ${Sec1} "Programa: contiene el jar ejecutable y ficheros necesarios para su ejecución."
	!insertmacro MUI_DESCRIPTION_TEXT ${Sec1JAR} "AlexGracia.jar: es el jar ejecutable."
	!insertmacro MUI_DESCRIPTION_TEXT ${Sec1DATABASE} "database: contiene .bat y .sql."
	!insertmacro MUI_DESCRIPTION_TEXT ${Sec1REPORT} "report: contiene .jasper necesarios para la opción informe del programa."

	; Manuales
	!insertmacro MUI_DESCRIPTION_TEXT ${Sec2} "Manuales: contiene los manuales de instalación y de usuario."
	!insertmacro MUI_DESCRIPTION_TEXT ${Sec2INSTALACION} "Instalación: manual de instalación en formato pdf."
	!insertmacro MUI_DESCRIPTION_TEXT ${Sec2USUARIO} "Usuario: manual de usuario en formato pdf."

	; !insertmacro MUI_DESCRIPTION_TEXT ${SecJRE} "JRE: contiene el instalador de Java, versión ${JRE_VERSION}."
	; !insertmacro MUI_DESCRIPTION_TEXT ${SecMYSQL} "MySQL: contiene el instalador de MySQL, versión ${MYSQL_VERSION}."

	!insertmacro MUI_FUNCTION_DESCRIPTION_END

;--------------------------------
;SECCION DEL DESINSTALADOR

Section Uninstall

	; Cambiando el SetOutPath, el drop_database.bat funciona, pero no borra la 'carpeta raiz'
	SetOutPath $INSTDIR

	; Borrar database
	ExecWait '"${INSTDIRDATABASE}\drop_database.bat"'
	; Parar servicio mysql
	Exec '"C:\xampp\mysql_stop.bat"'

	; Carpeta raiz
	RMDir /r /REBOOTOK $INSTDIR
	; Añadiendo /r borra todo, no se aconseja su uso.
	; No se puede borrar la carpeta raiz por este código SetOutPath $INSTDIR, pero añadiendo /REBOOTOK la borra en el siguiente inicio de sesión del PC.

	Delete "$DESKTOP\*AlexGracia*.lnk"

	SetAutoClose true

SectionEnd

;--------------------------------
;Uninstaller Functions

Function un.onInit

	; Iniciar servicio mysql
	Exec '"C:\xampp\mysql\bin\mysqld.exe"'

	!insertmacro MUI_UNGETLANGUAGE

FunctionEnd
