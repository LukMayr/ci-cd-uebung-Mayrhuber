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
Der erste SonarCloud-Scan auf dem unveränderten Main-Branch bestätigt den Fehler:
`java:S4973`, Issue `AaGr8PdCU3o20iwK5k0w`, Zeile 8.
Die unbearbeiteten API-Ergebnisse liegen in `docs/evidence/baseline-issues.json`.

## Behebung der doppelten Summenberechnung

SonarCloud: `java:S4144`, Issue `AaGr8PdBU3o20iwK5k0v`,
`Calculator.java`, Zeile 42. `addAll` enthielt dieselbe Schleife wie `sumUp`.
Die Methode delegiert nun an `sumUp`, sodass nur eine Implementierung gepflegt wird.
Die vorhandenen Tests prüfen beide Methoden gegen die konkreten Ergebnisse 10 und
-150, einschließlich `null`-Einträgen und negativen Zahlen.

Vorher:

```java
public int addAll(List<Integer> nums) {
    int s = 0;
    for (Integer n : nums) {
        if (n != null) {
            s += n;
        }
    }
    return s;
}
```

Nachher:

```java
public int addAll(List<Integer> nums) {
    return sumUp(nums);
}
```

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
- JaCoCo XML und HTML vorhanden; nach Entfernung der duplizierten Schleife
  20 von 42 Zeilen abgedeckt (ca. 47,6 %).
- Workflow mit actionlint 1.7.12 ohne Fehler validiert.
- GitHub Actions auf die verifizierten Commit-SHAs von checkout v7,
  setup-java v6 und upload-artifact v7 festgelegt.

## Hosted CI und SonarCloud-Baseline

- [CI-Run #1](https://github.com/LukMayr/ci-cd-uebung-Mayrhuber/actions/runs/42037328862):
  alle drei Matrix-Jobs erfolgreich, drei Surefire- und drei JaCoCo-Artefakte vorhanden.
- SonarCloud wurde im PR erwartungsgemäß übersprungen; der Job ist für Main-Pushes konfiguriert.
- [SonarCloud-Projekt](https://sonarcloud.io/project/overview?id=LukMayr_ci-cd-uebung-Mayrhuber):
  Organisation `lukmayr`, initiale Analyse vom 5. November 2026, 13:40:47 UTC.
- Baseline: 11 offene Issues (1 Bug, 10 Code Smells), kein Coverage-Wert,
  Quality-Gate-Status `NONE`. Das ist noch kein erfolgreicher CI-Scan.
- Die beiden oben dokumentierten Befunde sind behoben; die Bestätigung durch einen
  weiteren SonarCloud-Scan steht noch aus.

## Noch offen für den finalen PDF-Bericht

- Erfolgreiche GitHub-Actions-Matrix mit Run-URL und Screenshot.
- Drei Surefire-Artefakte mit Screenshot.
- Repository-Secret `SONAR_TOKEN` konfigurieren. Die öffentlichen Schlüssel stehen im POM.
- Automatic Analysis deaktivieren und erste CI-Analyse ausführen.
- Die zwei behobenen SonarCloud-Befunde durch erneute Analyse verifizieren.
- SonarCloud-Dashboard, Coverage und Quality Gate mit Screenshots belegen.
- Finalen PDF-Bericht aus den verifizierten Ergebnissen erstellen.

Die Aufgabenstellung nennt den 6. November 2025 als Abgabedatum; das tatsächliche
Abgabedatum für das Studienjahr 2026/27 ist noch zu bestätigen.
