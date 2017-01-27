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

###Instsallationsanleitung
    1. IntelliJ "New - Project from version control" (git clone https://github.com/ibwgr/TripPlanner)
    2. Postgres DB: Neue Datenbank mit frei wählbarem Namen angeben (z.B. CREATE DATABASE trip_planner_db;)
    3. Postgres DB: Das <b>db_script.sql file</b> auf der neuen Datenbank starten (/resources/db_script.sql)
    4. Property File mit dem Datenbank-Namen und den Datenbank-Credentials anpassen: /resources/db.properties
    5. IntelliJ start Klasse: /src/main/java/Start.java

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

####Lizenz

Lizenziert unter der GNU Lesser General Public License, siehe <a href="https://github.com/ibwgr/TripPlanner/blob/master/COPYRIGHT.txt">COPYRIGHT.txt</a> und <a href="https://github.com/ibwgr/TripPlanner/blob/master/COPYING.LESSER.GNU-LPGL.txt">COPYING.LESSER.GNU-LPGL.txt</a> </br>
LGPL ist eine etwas entschärfte Variante der GPL (Lizenz für freie Software), die aussagt, dass die Verwendung von Programmen, die unter dieser Lizenz stehen, nicht dazu führen muss, dass die ganze Software unter dieser Lizenz (und damit frei) herausgegeben werden muss. 

<i>Disclaimer Notice:</br>
Trip Planner uses JxMaps http://www.teamdev.com/jxmaps, which is a proprietary software. 
The use of JxMaps is governed by JxMaps Product Licence Agreement http://www.teamdev.com/jxmaps-licence-agreement. If you would like to use JxMaps in your development, please contact TeamDev.
</i>
