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
- Keine Sonderzeichen (ä,ö,ü,ß) im gesamten Code, auch nicht in Kommentaren, verwenden ODER in Eclipse umstellen: Window(bzw. Eclipse unter Mac) -> Preferences(Einstellungen) -> General -> Content Types -> Text; 
unten unter "Default Encoding" folgendes eingeben: "UTF8" und mit Update übernehmen.

coding guidelines bei eclipse einstellen
-----------------------------------------
- window->preferences->java->code style->edit
- auf nachfrage neuen style anlegen
- indentation: tab policy->spaces only
- braces: class or interface decl.->next line; method declaration->next line; rest->same line

- automatisch einrücken (unter windows): strg+shift+f
(achtung, kann probleme bei kommentaren geben ...

Anleitung Webcamzugriff (Ordner camera)
=======================================
Allgemein (Windows + MacOS):
- JavaCV wird benötigt. Eigentlich sollten die .jar Dateien bereits im GIT-Repo sein… aber alternativ kann man sich diese hier downloaden: http://code.google.com/p/javacv/downloads/detail?name=javacv-0.3-bin.zip
- Die entsprechenden .jar's zum Build Path hinzufügen (unter Eclipse: rechtsklick auf das Projekt-> properties->java build path-> libraries -> add jars (bzw. add external jars, je nachdem ob es schon im projekt ist oder nicht, aber wie gesagt, eigentlich sollte es schon drin sein…)

Weiter gehts Systemspezifisch

Windows:
--------
- opencv runterladen, http://sourceforge.net/projects/opencvlibrary/files/opencv-win/2.4.3/
- installieren, pfad sollte "c:\opencv" sein
- systemvariablen setzen (start->systemsteuerung->system->einstellungen ändern->erweitert->umgebungsvariablen), dann unter Systemvariablen "Path" doppelklicken. Dann folgendes, durch Semikolon abgetrennt, eintragen:

- (64 Bit)
C:\opencv\build\common\tbb\intel64\vc10;C:\opencv\build\x64\vc10\bin;

- (32 Bit)
C:\opencv\build\common\tbb\ia32\vc10;C:\opencv\build\x86\vc10\bin;

Microsoft Visual C++ installieren (nur EINE der beiden Versionen, je nachdem ob bereits 32 o. 64 Bit gewählt wurde)

- (64 Bit)
http://www.microsoft.com/en-us/download/details.aspx?id=14632 

- (32 Bit)
http://www.microsoft.com/en-us/download/details.aspx?id=5555

- fertig.

MacOS
-----
- opencv runterladen, http://sourceforge.net/projects/opencvlibrary/files/opencv-unix/2.4.3/
- xCode sollte installiert sein, damit make funktioniert. Eventuell damit das alles noch im terminal geht, noch die CommandLineTools aus dem DeveloperCenter installieren: https://developer.apple.com/downloads/index.action
(sind standardmäßig NICHT installiert)
- cmake wird noch benötigt: http://cmake.org/cmake/resources/software.html dmg datei laden und installieren.
- Den Ordner "OpenCV-2.4.3" nach beispielsweise "opt" kopieren. (z.b. im terminal "sudo cp -r -v OpenCV-2.4.3 opt/" (oder irgendwo anders hin…)
- in den ordner wechseln, und "cmake ." eingeben
- nachdem das durchgeführt wurde, "sudo make" eingeben (kann so 15-30 min dauern)
- danach "sudo make install"
- fertig






Todo
====
coming soon...
