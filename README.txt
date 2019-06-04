****************QuizBattle****************
1.   Um die Desktopanwendung ausf�hren zu k�nnen, muss zuerst in pgAdmin eine neue Datenbank
     erstellt werden und in diese Datenbank muss dann das BackupFile (backupFile.backup) geladen
     werden.
2.   Mittels Rechtsklick auf die erstellte Datenbank und "Restore" kann das File ausgew�hlt
     werden und somit eingelesen werden.
3.   Wenn man das Projekt in NetBeans �ffnet, muss man vor Beginn das Config-File anpassen.
4.   Folgende Library muss eingebunden werden:
	* PostgreSQL JDBC Driver
5.   Wurde dies gemacht, muss als erstes der Server gestartet werden und danach kann der
     GUIBuilder beliebig oft gestartet werden.

*******************************************
Sollten mehr Fragen in die Datenbank importiert werden wollen. 
Bietet sich folgende API an: https://opentdb.com/api_config.php
Nachdem die URL generiert wurde, muss diese in "DB_JSON" ersetzt werden.
F�r das einf�gen weiterer Fragen in die Datenbank sind folgende Libraries notwendig:
	* common-lang3.jar (damit Sonderzeichen korrekt gelesen werden k�nnen)
	* java-json.jar (f�r den Zugriff auf die API)
Beide Labraries sind im Repository im Ordner Libraries aufzufinden.

*******************************************
Die Datenbankstruktur ist �ber das File "ER-Diagrmm.PNG" ersichtlich und beinhaltet ebenso das Relationen Modell.
Weiters gibt es auch ein Sequendiagramm unseres Projekts, der den Ablauf verdeutlicht.

*******************************************
Link zu unserem Trello-Board:
https://trello.com/b/5Lfvj8Rq/quizbattle