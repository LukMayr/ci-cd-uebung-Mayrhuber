# Übung 2 – Arbeitsstand und Nachweise

Repository: https://github.com/LukMayr/ci-cd-uebung-Mayrhuber

## Ausgangspunkt

- Ausgangscommit: `239344d9fa68102062001d2425f2c2e8c23c9241`.
- Baseline unter Java 17: 8 Tests, 1 Failure, 0 Errors, 0 Skipped.
- Der erwartete Failure betrifft `TextUtilsTest.isPalindrome_caseInsensitive_expectedTrue`.
- JaCoCo 0.8.11 und Surefire 3.2.5 waren bereits im Starterprojekt konfiguriert.

## Behebung des Palindrom-Fehlers

Vorher:

```java
return input.toLowerCase() == reversed.toLowerCase();
```

Nachher:

```java
return input.equalsIgnoreCase(reversed);
```

`==` vergleicht Objektreferenzen. `equalsIgnoreCase` vergleicht den Inhalt ohne
Berücksichtigung der Groß-/Kleinschreibung und benötigt keine localeabhängige
Umwandlung. Der vorhandene Test mit `Anna` prüft den Fehler. Zusätzliche Tests
decken leere Strings, ein Zeichen, weitere Palindrome, Nicht-Palindrome und `null` ab.
Diese Änderung wurde lokal identifiziert; sie ist noch kein dokumentierter SonarCloud-Befund.

## Matrix und Artefakte

- Ubuntu / Java 17
- Ubuntu / Java 21
- Windows / Java 21
- Ausgeschlossen: Windows / Java 17. Spart Runner-Zeit und erhält die Abdeckung
  beider Betriebssysteme und Java-Versionen, verzichtet aber auf diese spezielle Kombination.
- Surefire: `surefire-<os>-java-<version>`; Upload auch bei Testfehlern.
- Coverage: `jacoco-<os>-java-<version>`; enthält XML und HTML.
- Main-Pushes laufen ohne Pfadfilter; PRs filtern auf Quellen, POM und Workflow.

## Lokale Validierung

- Java 17: `mvn --batch-mode --no-transfer-progress clean verify` erfolgreich.
- Java 21: `mvn --batch-mode --no-transfer-progress clean verify` erfolgreich.
- Jeweils 16 Tests, 0 Failures, 0 Errors, 0 Skipped.
- JaCoCo XML und HTML vorhanden; 25 von 47 Zeilen abgedeckt (ca. 53,2 %).
- Workflow mit actionlint 1.7.12 ohne Fehler validiert.
- GitHub Actions auf die verifizierten Commit-SHAs von checkout v7,
  setup-java v6 und upload-artifact v7 festgelegt.

## Noch offen für den finalen PDF-Bericht

- Erfolgreiche GitHub-Actions-Matrix mit Run-URL und Screenshot.
- Drei Surefire-Artefakte mit Screenshot.
- SonarCloud-Projekt, Organization-/Project-Key und Repository-Secret konfigurieren.
- Automatic Analysis deaktivieren und erste CI-Analyse ausführen.
- Mindestens zwei tatsächliche SonarCloud-Befunde vor der Behebung mit Regel/Ort sichern.
- Befunde beheben, Vorher/Nachher dokumentieren und erneut analysieren.
- SonarCloud-Dashboard, Coverage und Quality Gate mit Screenshots belegen.
- Finalen PDF-Bericht aus den verifizierten Ergebnissen erstellen.

Die Aufgabenstellung nennt den 6. November 2025 als Abgabedatum; das tatsächliche
Abgabedatum für das Studienjahr 2026/27 ist noch zu bestätigen.
