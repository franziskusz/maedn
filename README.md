# Mensch ärgere dich nicht
- Kompilierte, ohne weiteres ausführbare Version ist als Release im Repository enthalten

# Regeln
## Ziel des Spiels
- Das Ziel des Spiels besteht darin alle 4 Figuren als erster ins Zielfeld zu bringen.
## Spielverlauf
- Jeder Spielende würfelt 1x und die größte Zahl beginnt.
    - Sollten mehrere Spielende die gleiche Zahl haben würfeln diese erneut
und die größere Zahl beginnt.
- Die Spielenden würfeln im Uhrzeigersinn (beginnend bei dem Spielenden mit der größten Zahl) 3x bis jemand eine 6 hat.
    - Die Runde im Uhrzeigersinn dauert das ganze Spiel über an.
- Wer eine 6 würfelt, darf anschließend erneut würfeln.
- Wenn jemand eine 6 würfelt, muss eine der Spielfiguren vom Haus auf das Startfeld gestellt werden und es wird anschließend erneut gewürfelt.
    - Solange wie noch Spielfiguren im Haus stehen muss die Figur vom Startfeld gezogen werden um dieses frei zu machen.
    - Sollte keine Spielfigur mehr im Haus stehen, darf frei gewählt werden welche der Figuren gesetzt werden soll und man darf anschließend ebenfalls erneut würfeln.
- Zieht man eine Figur auf ein Feld auf dem bereits eine Figur steht wird diese geschlagen und in das entsprechende Haus zurück gesetzt.
    - Es existiert ein Schlagzwang, wenn man also schlagen kann, dann muss man das auch.
        - Hat man mehrere Möglichkeiten zum schlagen, darf man selbst wählen welche Figur man schlagen möchte.
- Blockiert eine eigene Spielfigur ein Feld auf das gezogen werden soll, kann dieser Zug nicht gemacht werden, da man sich nicht selbst schlagen darf.
- Hat man seine Spielfigur im Ziel, kann diese nicht mehr von anderen Spielfiguren geschlagen werden.
- Sind alle auf dem Feld befindlichen Spielfiguren im Ziel und vollständig aufgerückt, darf wieder 3x gewürfelt werden.
- Man darf seine eigenen Figuren im Ziel nicht überspringen, auf dem Spielfeld ist das jedoch gestattet.


## SetupGUI
- Beim Start des Programms wird die Setup-GUI aufgerufen auf der einige Buttons und Text zu sehen ist.
- Links und rechts neben der entsprechenden Farbe stehen die Optionen “Person” und “Bot”.
    - Klickt man “Person” kann man selbst spielen.
    - Klickt man “Bot” übernimmt die KI den Spieler.
- Durch Klicken auf eine der beiden Optionen wird die ausgewählte Option in
der entsprechenden Farbe gefärbt und die andere Option wird deaktiviert.
    - Um diese Auswahl rückgängig zu machen ist ein erneuter Klick auf den
gefärbten Button notwendig.
- Der Button “Go!” wird nur dann aktiviert, wenn bei jeder Farbe eine Option
ausgewählt wurde.
- Durch einen Klick auf die Checkbox “Admin” wird dem Spiel ein Textfeld und
ein Button hinzugefügt über den dann Eingaben gemacht werden können.

## GameGUI
- Nach dem Klick auf “Go!” in der Setup-GUI wird das Spiel mit den eingegebenen Einstellungen gestartet.
- Im oberen Bereich ist eine Ausgabe zu sehen, die die aktuelle Anweisung mitteilt, in diesem Fall soll Rot jetzt würfeln.
- Die Hintergrundfarbe ändert sich je nach Spielendem, der an der Reihe ist.
- Durch einen Klick auf den “Dice” Button wird gewürfelt und das Würfelbild
ändert sich entsprechend der gewürfelten Zahl.
- Die Farbe des Buttons beim Klicken wechselt je nach Farbe des Spielenden der an der Reihe ist.
- Wenn ein Zug möglich ist werden die Buttons 0,1,2,3 aktiviert und in dem Spielstein werden die gleichen Zahlen eingeblendet.
    - Durch Klick auf den Button 0 wird die Figur mit der 0 bewegt.
        - Sollten mehrere Optionen möglich sein werden nur diese
Buttons aktiviert.
- Durch Klicken auf das Textfeld können Eingaben gemacht werden
(siehe Admin-Befehle).
- Am Ende des Spiels werden die Platzierungen angezeigt und durch Klicken des Buttons “Play again?” wird man erneut zur Setup-GUI weitergeleitet, die sich die Einstellungen vom letzten Spiel merkt.
- Der Regler unten ist für die Steuerung der Botgeschwindigkeit vorgesehen.
    - Dieser ist nur aktiviert wenn Bots im Spiel sind und lässt sich nach links und rechts schieben.

# Admin-Befehle
## Funktionsweise
In der Setup-GUI besteht die Möglichkeit durch anklicken der Checkbox “Admin” ein Admin Feld in der Game-GUI sichtbar zu machen. In dieses können dann Befehle eingegeben werden und durch Button Klick auf “Admin” oder Enter drücken im Textfeld ausgeführt werden.  
Die Befehlseingabe ist nur möglich, wenn ein Spieler der kein Bot ist, würfeln soll. In allen anderen Fällen die Eingabe sowie das Ausführen nicht möglich, da es den Spielablauf zerstören könnte.
## Befehle:

**SKIP_DETERMINE_BEGINNER**  
- Überspringt den Beginner Auswürfel-Prozess (ROT beginnt).
- kann nur ausgeführt werden, wenn der Auswürfel-Prozess noch nicht beendet ist.

**DICE [ZAHL]**  
- Imitiert den “Dice”-Button-Klick und schreibt schreibt die zu würfelnde Zahl vor.

**MOVE [PICE_ID] [FELD]**  
- Bewegt den angegebenen Spielstein (des Spielers der dran ist) zum angegebenen Feld (siehe Bild unterhalb).
- Kann nicht beim Beginner Auswürfel-Prozess ausgeführt werden.

**SHOW_ID**  
- Zeigt die IDs auf den Spielsteinen des aktuellen Spielers an.

**NEXT_PLAYER**  
- Überspringt den aktuellen Spieler.
- Kann auch nach einmal 6 Würfeln ausgeführt werden.
- Kann nicht beim Beginner Auswürfel-Prozess ausgeführt werden.
