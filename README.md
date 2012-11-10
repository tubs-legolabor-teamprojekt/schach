Allgemeine Informationen
========================

github guidelines
-----------------
- Nur sources committen, keine binaries

coding guidelines
-----------------

- Spaces statt Tabs
- Funktionsnamen klein
- Variablennamen klein
- Klassennamen groß
- Attributnamen klein
- Geschweifte Klammern:
	- bei Klassen/Methoden: neue Zeile
	- bei if, while etc.: gleiche Zeile

coding guidelines bei eclipse einstellen
-----------------------------------------
- window->preferences->java->code style->edit
- auf nachfrage neuen style anlegen
- indentation: tab policy->spaces only
- braces: class or interface decl.->next line; method declaration->next line; rest->same line

- automatisch einrücken (unter windows): strg+shift+f
(achtung, kann probleme bei kommentaren geben ...

Anleitung Webcamzugriff (Ordner camera)
---------------------------------------
Allgemein (Windows + MacOS):
- JavaCV wird benötigt. Eigentlich sollten die .jar Dateien bereits im GIT-Repo sein… aber alternativ kann man sich diese hier downloaden: http://code.google.com/p/javacv/downloads/detail?name=javacv-0.3-bin.zip
- Die entsprechenden .jar's zum Build Path hinzufügen (unter Eclipse: rechtsklick auf das Projekt-> properties->java build path-> libraries -> add jars (bzw. add external jars, je nachdem ob es schon im projekt ist oder nicht, aber wie gesagt, eigentlich sollte es schon drin sein…)

Weiter gehts Systemspezifisch

Windows:
- opencv runterladen, http://sourceforge.net/projects/opencvlibrary/files/opencv-win/2.4.3/
- installieren, pfad sollte "c:\opencv" sein
- systemvariablen setzen (start->systemsteuerung->system->einstellungen ändern->erweitert->umgebungsvariablen), dann unter Systemvariablen "Path" doppelklicken. Dann folgendes, durch Semikolon abgetrennt, eintragen:

(64 Bit)
C:\opencv\build\common\tbb\intel64\vc10;C:\opencv\build\x64\vc10\bin;
(32 Bit)
C:\opencv\build\common\tbb\ia32\vc10;C:\opencv\build\x86\vc10\bin;

fertig.

MacOS





Todo
----
coming soon...