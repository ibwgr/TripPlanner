# Semesterarbeit "Trip Planner"
## Dieter Biedermann, Reto Kaufmann / IBW Chur 2017/01
<b>Applikation fuer die Planung einer Reise</b>

Mit der Trip Planner Applikation kann ein Benutzer seine individuelle Reise planen und grafisch darstellen. 
Dazu werden vom Administrator CSV Datenfiles mit Orten und Point-of-Interest (POI) in Datenbank-Tabellen eingelesen. Grundsätzlich werden diese CSV Files immer wieder aktualisiert müssen vom Administrator auf periodischer Basis neu eingelesen und automatisch mit der Tabelle abgeglichen werden. Für den CSV Import nützt der Admin ein eigenes Admin-GUI.
Der Benutzer loggt sich ein und beginnt mit der Erstellung einer neuen Reise. Danach sucht er sich aus den Orte/POI Tabellen die für seine Reise interessanten Orte und Sehenswürdigkeiten aus und speichert diese in seiner Reisaktivitätenliste. Diese wird ihm tabellarisch und auf einem Map-Panel grafisch angezeigt.

<b>Komponenten</b>

Die Applikation basiert auf den im ersten Semester erlernten Komponenten mit Java. Dies beinhaltet im Moment auch ein Swing GUI. Natürlich könnte dieses GUI später auch durch ein Web-basiertes Java GUI, wie zum Beispiel “Vaadin”, ersetzt werden.

<p align="left">
  <img src="https://github.com/ibwgr/TripPlanner/blob/master/README.Komponenten.jpg" width="800" height="600"/>
</p>
