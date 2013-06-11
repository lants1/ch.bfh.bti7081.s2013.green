##### Installation-Path
spitapp-master-pom enthält:
	- spitapp-core
	- spitapp-gui
als Module

##### vorgehen bei neuinstallation
mvn clean install > auf spitapp-master-pom
mvn tomcat7:deploy > auf spitapp-gui

alternativ kann auch jetty verwendet werden

##### Datenbank
als Datenbank wird postgres verwendet

1wichtig um die Tabellenstruktur neu anzulegen muss:
2 Im hibernate.cfg.xml im Core die Zeile erstmals vorhanden sein.
2   <property name="hbm2ddl.auto">create</property>
3 Kurz DatabaseServiceTest ausführen damit die Tabellen generiert werden.
4 create Zeile wieder auskommentieren
5 normal builden. 

alternativen unter spitapp-core einstellen.

#### Testdaten generieren
   
Testdaten werden mit den Tests generiert und jeweils neu angelegt.