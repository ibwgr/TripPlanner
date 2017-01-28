# Semesterarbeit "Trip Planner"
## Dieter Biedermann, Reto Kaufmann </br>NDS HF Applikationsentwicklung, IBW Chur 2017/01

<table>
 <tr>
  <td>
  Travis Build/Test Status:
  </td>
  <td>
   <a href="https://travis-ci.org/ibwgr/TripPlanner" target="_blank"><img src="https://travis-ci.org/ibwgr/TripPlanner.svg?branch=master"/></a>
  </td>
 </tr>
</table>
</br>

<b>Applikation fuer die Planung einer Reise</b>

Mit der Trip Planner Applikation kann ein Benutzer seine individuelle Reise planen und grafisch darstellen. </br>
Dazu werden vom Administrator CSV Datenfiles mit Orten und Point-of-Interest (POI) in Datenbank-Tabellen eingelesen. Grundsätzlich werden diese CSV Files immer wieder aktualisiert müssen vom Administrator auf periodischer Basis neu eingelesen und automatisch mit der Tabelle abgeglichen werden. Für den CSV Import nützt der Admin ein eigenes Admin-GUI.</br>
Der Benutzer loggt sich ein und beginnt mit der Erstellung einer neuen Reise. Danach sucht er sich aus den Orte/POI Tabellen die für seine Reise interessanten Orte und Sehenswürdigkeiten aus und speichert diese in seiner Reisaktivitätenliste. Diese wird ihm tabellarisch und auf einem Map-Panel grafisch angezeigt.

<b>Komponenten</b>

Die Applikation basiert auf den im ersten Semester erlernten Komponenten mit Java. Dies beinhaltet im Moment auch ein Swing GUI. Natürlich könnte dieses GUI später auch durch ein zusätzliches Web-basiertes Java GUI, wie zum Beispiel “Vaadin”, erweitert werden.

<p align="left">
  <img src="https://github.com/ibwgr/TripPlanner/blob/master/doc/Komponenten.jpg" width="800" height="600"/>
</p>

###Installationsanleitung
    1. IntelliJ "New - Project from version control" (git clone https://github.com/ibwgr/TripPlanner)
       - Non-managed pom.xml file found: Add as Maven Project
       - Java SDK in auf Projekt setzten 
       - Allfällig weitere notwendige IntelliJ Projekt-Einstellungen sind am Schluss dieses Dokuments beschrieben) 
    2. Postgres DB: Neue Datenbank mit frei wählbarem Namen anlegen (z.B. CREATE DATABASE trip_planner_db;)
    3. Postgres DB: Das db_script.sql file auf der neuen Datenbank ausführen (/resources/db_script.sql)
    4. Property File mit dem Datenbank-Namen und den Datenbank-Credentials anpassen: /resources/db.properties
    5. In IntelliJ folgende Klasse starten: /src/main/java/Start.java


####Anleitung Admin-Funktionen
1. Login als "admin" (PW="admin")
2. Importieren der Kategorien-Datei: /resources/poi_categories.csv
<img src="https://github.com/ibwgr/TripPlanner/blob/master/doc/Screenshot_Import_Category.png" width="450" height="191"/>
3. Importieren der POI (Point of Interests) Dateien, z.B.: /resources/sweden-latest.csv und switzerland-latest.csv usw.
<img src="https://github.com/ibwgr/TripPlanner/blob/master/doc/Screenshot_Import_Poi.png" width="450" height="185"/>

####Anleitung Benutzer-Funktionen
1. Login als "benutzer" (PW="benutzer")
2. Neue Reise erfassen, muttels "Add New Trip" im unteren Bereich des Fensters
<img src="https://github.com/ibwgr/TripPlanner/blob/master/doc/Screenshot_TripView.png" width="800" height="450" />
3. Mittels Doppelklick auf die Reise wird man zur Aktivitätsliste weitergeleitet, diese ist noch leer
4. Neue Aktivität zur Reise erfassen mittels "New Activity"
<img src="https://github.com/ibwgr/TripPlanner/blob/master/doc/Screenshot_ActivityView.png" width="800" height="545" />
5. Man wird zur Ort Suche weitergeleitet
6. Ort suchen durch eingabe eines Ortnamens (oder Teile davon) im Feld "City Name" und Search
<img src="https://github.com/ibwgr/TripPlanner/blob/master/doc/Screenshot_CitySearch.png" width="800" height="545" />
7. Nun einen der aufgefundenen Ortschaften anklicken (auswählen)
8. Jetzt kann man entweder mit "Add Activity" diesen Ort alleine zur Aktivitätsliste hinzufüen
  ODER man klickt auf "Search POI near city" um nach Interessantem in der Gegend um den Ort zu suchen.
9. In diesem Fall wird man wird zur POI Suche weitergeleitet
10. Nun entweder nach POI Kategorie suchen (Dropdown) ODER nach POI Name wie z.B. "Hotel"
11. Aus der Liste der aufgefundenen POI einen auswählen, unten Datum und Kommentar eingeben und speichern.
<img src="https://github.com/ibwgr/TripPlanner/blob/master/doc/Screenshot_PointOfInterestSearch.png" width="800" height="545" />
Grundsätzlich kann man sich mit den Pfeiltasten oder dem X innerhalb der Fenster navigieren.


<p>&nbsp;</p>

####Source Code Testing
Innerhalb IntelliJ werden mittels <b>“Run all tests”</b> alle Unit Tests und alle Integrationstests gestartet. Hierbei muss somit die DB gestartet sein.<br/>
Laufen die Tests unter Maven, werden bei <b>“mvn test”</b> explizit nur die Unit-Tests durch laufen.<br/>
Travis startet beim Push und nach dem Merge (Pull Request in Master) auch die Unit-Tests via Maven. 

<p>&nbsp;</p>

####IntelliJ 
Folgende Einstellungen auf dem IntelliJ sind zu setzen, damit sich das Start.java Programm problemlos starten lässt:
1. Nach dem "File new project from version control" bemerkt IntelliJ dass es sich hier um ein Maven Projekt handelt und frägt nach was mit dem pom.xml geschehen soll. Am einfachsten man klickt auf "Add as Maven Project". Falls das Fenster mit dieser Frage verschwunden ist, kann man es im Event Log Fenster wieder auffinden.
<img src="https://github.com/ibwgr/TripPlanner/blob/master/doc/IntelliJ-Project2.jpg" width="556" height="156"/>
2. Das Project SDK muss zwingend gesetzt sein (Java 8) 
<img src="https://github.com/ibwgr/TripPlanner/blob/master/doc/IntelliJ-Project1.jpg" width="538" height="386"/>
(Project compiler output muss nicht zwingend gesetzt sein)
3. Eigentlich sollte die Projektstruktur von IntelliJ automatisch erkannt werden. Falls nicht, kann in der "Project Structure" unter Modules noch das Source und Test Verzeichnis markiert werden:
<img src="https://github.com/ibwgr/TripPlanner/blob/master/doc/IntelliJ-Project3.jpg" width="616" height="478"/>
4. Sollten unerwarteterweise immer noch einige Libraries in den Imports als fehlend aufscheinen, dann kann mittels Maven - Reimport (auf Projektebene) das Neuladen aller Dependencies angestossen werden:
<img src="https://github.com/ibwgr/TripPlanner/blob/master/doc/IntelliJ-Project4.jpg" width="456" height="283"/>


<p>&nbsp;</p>

####Lizenz

Lizenziert unter der GNU Lesser General Public License, siehe <a href="https://github.com/ibwgr/TripPlanner/blob/master/COPYRIGHT.txt">COPYRIGHT.txt</a> und <a href="https://github.com/ibwgr/TripPlanner/blob/master/COPYING.LESSER.GNU-LPGL.txt">COPYING.LESSER.GNU-LPGL.txt</a> </br>
LGPL ist eine etwas entschärfte Variante der GPL (Lizenz für freie Software), die aussagt, dass die Verwendung von Programmen, die unter dieser Lizenz stehen, nicht dazu führen muss, dass die ganze Software unter dieser Lizenz (und damit frei) herausgegeben werden muss. 

<i>Disclaimer Notice:</br>
Trip Planner uses JxMaps http://www.teamdev.com/jxmaps, which is a proprietary software. 
The use of JxMaps is governed by JxMaps Product Licence Agreement http://www.teamdev.com/jxmaps-licence-agreement. If you would like to use JxMaps in your development, please contact TeamDev.
</i>
