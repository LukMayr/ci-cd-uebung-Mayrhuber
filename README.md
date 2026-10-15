# CI/CD Übung – Java Basisprojekt (Ü1 mit Anleitungen)

Dieses Repository ist der Startpunkt für **Übung 1** und wird in **allen folgenden Übungen** weiterverwendet
(CI mit GitHub Actions → SonarCloud → Docker → Security-Scan).

---

## Voraussetzungen (VS Code)
- **Extension Pack for Java** (Language Support, Debugger, Test Runner, Maven).
- **JDK 17 oder neuer** aktiv: *Java: Configure Java Runtime* (getestet mit JDK 17, 21, 25 und 27).
- **Maven 3.9 oder neuer**, Version prüfen mit `mvn -v` (getestet mit 3.9.11 und 3.9.16).
- Öffne **den Projektroot** (Ordner mit `pom.xml`, nicht nur `src/`).
- Tests ausführen: *Test Explorer* oder per Terminal:
  ```bash
  mvn -q -DskipTests=false test
  ```

---

## Build & Test (lokal)
```bash
mvn -q -DskipTests=false test
```
Coverage-Report (für Übung 2 / SonarCloud): `target/site/jacoco/index.html`

> **Hinweis:** `TextUtilsTest` enthält einen bewusst fehlschlagenden Test (`isPalindrome_caseInsensitive_expectedTrue`), der erst in Übung 2 behoben wird. Bis dahin endet `mvn test` mit **1 Failure** und `BUILD FAILURE`, und der Coverage-Report wird nicht erzeugt. Den Report gibt es trotzdem mit:
> ```bash
> mvn test -Dmaven.test.failure.ignore=true
> ```

---

## Projektstruktur
- `src/main/java`: `App`, `Calculator`, `TextUtils`, `NumberUtils`
- `src/test/java`: `CalculatorTest`, `TextUtilsTest` (enthält TODOs & einen bewusst fehlschlagenden Test)

---

## Übung 1 – Aufgaben (24 Punkte)
- Autor: @LukMayr
- Besonderheit
  - seit 2021 auf GitHub.
  - in allen meinen Praktika mit Github/Gitlab extensive gearbeitet.

### (1) Repository einrichten (4 P.) – **Fork & Clone / Template / ZIP**
> Lege **dein eigenes Repo** mit dem Namen **`ci-cd-uebung-[Nachname]`** an und bringe das Starterprojekt hinein. Wähle **eine** der Varianten.

**Variante A – Fork**  

> **Wichtig beim Forken:**
> - Ein Fork eines öffentlichen Repos ist immer öffentlich und kann nicht auf privat gestellt werden. Wer ein privates Repo möchte, verwendet Variante B oder C.
> - Beim Pull Request in Teil (4) schlägt GitHub als Ziel das Kurs-Repo `mrckurz/cicd-BA-uebung01` vor. Als **base repository** muss der eigene Fork ausgewählt werden, sonst landet der PR im Kurs-Repo.
> - Issues sind in Forks standardmäßig ausgeschaltet. Einschalten unter **Settings → General → Features → Issues**.

1. Öffne das Kurs-Starter-Repo (`https://github.com/mrckurz/cicd-BA-uebung01`).  
2. Klick **Fork** → *Owner*: dein Account → *Name*: `ci-cd-uebung-[Nachname]` → Create fork.  
3. **Deinen Fork klonen** (HTTPS oder SSH):
   ```bash
   # HTTPS
   git clone https://github.com/<dein-user>/ci-cd-uebung-[Nachname].git
   # oder SSH
   # git clone git@github.com:<dein-user>/ci-cd-uebung-[Nachname].git
   cd ci-cd-uebung-[Nachname]
   ```
4. **Upstream einrichten** (Original-Repo als Referenz):
   ```bash
   git remote add upstream https://github.com/mrckurz/cicd-BA-uebung01.git
   git remote -v
   # => origin = dein Fork, upstream = Kurs-Repo
   ```
   `origin` zeigt nach dem Klonen automatisch auf den eigenen Fork, dorthin wird gepusht. `upstream` zeigt auf das Kurs-Repo und dient nur zum Abholen von Änderungen, z. B. aktualisierter Angaben zu den nächsten Übungen. Auf `upstream` kann nicht gepusht werden (keine Schreibrechte).
5. **(Später) Kurs-Änderungen holen**:
   ```bash
   git fetch upstream
   git switch main
   git merge upstream/main   # oder: git rebase upstream/main
   ```
   Alternativ auf GitHub im eigenen Fork: **Sync fork** → **Update branch**, danach lokal `git pull`.

**Variante B – „Use this template“ / Import**  
1. Im Kurs-Repo auf **Use this template** → *Owner*: dein Account → *Name*: `ci-cd-uebung-[Nachname]` → Create.  
   *(Alternativ: GitHub → **Import repository** → URL des Kurs-Repos eintragen.)*  
2. **Klonen**:
   ```bash
   git clone https://github.com/<dein-user>/ci-cd-uebung-[Nachname].git
   cd ci-cd-uebung-[Nachname]
   ```

**Variante C – ZIP/Download in dein leeres Repo pushen**  
1. Lege auf GitHub ein **leeres Repo** `ci-cd-uebung-[Nachname]` an.  
2. Lade das Starterprojekt als ZIP herunter, entpacke es und initialisiere Git:
   ```bash
   cd <entpackter-ordner-mit-pom.xml>
   git init
   git add .
   git commit -m "chore: import starter project"
   git branch -M main
   git remote add origin https://github.com/<dein-user>/ci-cd-uebung-[Nachname].git
   git push -u origin main
   ```

**Erster Check lokal**  
```bash
mvn -v
mvn -q -DskipTests=false test
```

---

### (2) Projektgrundlage + erste Tests (6 P.)
- Das Starterprojekt liegt bereits unter `src/`.  
- **Aufgabe:** Schreibe **mindestens zwei zusätzliche Unit-Test** in `src/test/java` (z. B. weitere Fälle für `Calculator` oder `TextUtils`).  
- Stelle sicher, dass die Tests **lokal ausführbar** sind (`mvn test` oder Test-Explorer).
- Der bewusst fehlschlagende Test in `TextUtilsTest` darf in Übung 1 noch fehlschlagen. Alle anderen Tests, auch die neuen, müssen grün sein.

---

### (3) Branching (6 P.)
- Erstelle einen Branch **`feature/about-me`**.  
- Lege eine Datei **`about-me.md`** im Projektroot an **mit folgendem Inhalt**:
  - **Name**
  - **Studiengang**
  - **Erwartungen** an die LV (kurz)
  - **3 Fakten** über dich
  - Ein kurzer Satz: **„Warum ist CI/CD für mich relevant?“**
- Commit & Push des Branches.

---

### (4) PR-Workflow & Reviews (6 P.)
- Erstelle auf GitHub einen **Pull Request** von `feature/about-me` auf `main` mit *sinnvoller Beschreibung*.  
- **Team-Review** (2–3er Teams, je eigenes Repo): mind. **1 Kommentar**.  
- Adressiere das Feedback und **merge** den PR.

---

### (5) Dokumentation (2 P.)
- Ergänze die `README.md` im Main-Branch um den Abschnitt:
  ```markdown
  ## Übung 1
  - Autor: <dein Name>
  - Besonderheit: <1–2 Sätze zu deiner bisherigen Git-Erfahrung>
  ```
- Reiche den **Repo-Link** in eLearning bis **28.10.2026, 23:55** ein.

---

## Bewertung (Rubrik, 24 Punkte)

| Teilaufgabe | Beschreibung | Punkte |
|---|---|---:|
| Repo einrichten | Repo + Starterprojekt im eigenen Repo, lokal geklont | 4 |
| Projektgrundlage & Tests | mindestens **2 zusätzlichen Tests** geschrieben, Tests laufen lokal (bis auf den bewusst fehlschlagenden Test) | 6 |
| Branching | Branch erstellt, `about-me.md` mit allen geforderten Inhalten | 6 |
| PR & Review | PR mit Beschreibung, 1 Review-Kommentar, Merge | 6 |
| Dokumentation | README-Abschnitt „Übung 1“ ergänzt | 2 |
| **Summe** | | **24** |

---

## Konventionen (für alle Übungen)
- **Branch-Namen:** `feature/<kurz>`, `fix/<issue-id>-<kurz>`
- **Commit-Messages:** *Conventional Commits* (`feat:`, `fix:`, `docs:`, `test:`, `refactor:` …)
- **PR-Qualität:** kleine Diffs, klare Beschreibung, mindestens **1 Review-Kommentar**
- **Issues:** Labels (`bug`, `feat`, `docs`, …), PRs mit Issues verlinken (`Fixes #<nr>`)

---

## Sonar-Hinweise (werden in Übung 2 sichtbar)
- **String-Vergleich mit `==`** in `TextUtils.isPalindrome` (Bug)
- **Leerer `catch`-Block** + zu generische Exception in `TextUtils.safeParseInt`
- **Duplizierter Code** in `Calculator.sumUp` / `addAll`
- **Magic Numbers** (`42`, `3/5/7/13/17`)
- **`System.out.println`** statt Logger in `App`
- **öffentliche, nicht finale statische Felder** (`MAX_OPERANDS`, `DEFAULT_LIMIT`)
- **unbenutztes Feld** in `NumberUtils`

---

## Troubleshooting

### A) Quick Checks (alle Plattformen)
- Projekt-Root geöffnet (Ordner mit `pom.xml`)?  
- `java -version` und `mvn -v` liefern Versionen?  
- In `pom.xml` stehen `junit-jupiter-api` **und** `junit-jupiter-engine`; Surefire 3.x aktiv?  
- Tests lokal grün:
  ```bash
  mvn -q -DskipTests=false test
  ```
- `git remote -v`: **origin** → eigenes Repo; **upstream** (falls vorhanden) nur zum Lesen.

### B) Windows – Installation & PATH
- Installieren (PowerShell):
  ```powershell
  winget install Git.Git
  winget install Apache-Maven.Apache-Maven
  winget install EclipseAdoptium.Temurin.17.JDK
  java -version
  mvn -v
  setx JAVA_HOME "C:\Program Files\Eclipse Adoptium\jdk-17"
  $env:PATH += ";C:\Program Files\Eclipse Adoptium\jdk-17\bin"
  ```
- VS Code → „**Java: Configure Java Runtime**“ → **JDK 17** wählen.  
- Lange Pfade: `git config --global core.longpaths true`.

### C) Linux – Installation & PATH
- Debian/Ubuntu:
  ```bash
  sudo apt update && sudo apt install -y openjdk-17-jdk maven git
  ```
- Fedora:
  ```bash
  sudo dnf install -y java-17-openjdk-devel maven git
  ```
- Arch:
  ```bash
  sudo pacman -S jdk17-openjdk maven git
  ```
- JAVA_HOME/PATH setzen:
  ```bash
  java -version && mvn -v
  echo 'export JAVA_HOME=$(dirname $(dirname $(readlink -f $(which java))))' >> ~/.bashrc
  echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc && source ~/.bashrc
  ```

### D) VS Code – Tests werden nicht erkannt
- **Extension Pack for Java** installieren (inkl. Test Runner).  
- Projekt neu laden: „**Java: Clean Java Language Server Workspace**“.  
- POM prüfen:
  ```xml
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.10.2</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.10.2</version>
    <scope>test</scope>
  </dependency>

  <!-- unter <build><plugins> -->
  <plugin>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.2.5</version>
  </plugin>
  ```

### E) Git & PR – häufige Fehler
- **HTTPS + 2FA:** statt Passwort **Personal Access Token** verwenden.  
- **SSH:** Key erstellen & bei GitHub hinterlegen:
  ```bash
  ssh-keygen -t ed25519 -C "<deine-mail>"
  eval "$(ssh-agent -s)" && ssh-add ~/.ssh/id_ed25519
  cat ~/.ssh/id_ed25519.pub  # Key bei GitHub > Settings > SSH Keys hinzufügen
  ```
- **Falscher Push-Remote:**  
  ```bash
  git config remote.pushDefault origin
  ```
- **PR „Update branch“ / Konflikte:**
  ```bash
  git fetch origin
  git switch feature/about-me
  git merge origin/main   # oder: git rebase origin/main
  # Konflikte in VS Code lösen → mvn test → commit → push
  ```
- **non-fast-forward beim Push:** `git pull --rebase`, dann `git push`.  
- **PR blockiert:** Branch-Protection → Approval/Checks (CI) nötig.

### F) Zeilenenden & Encoding
```bash
# Windows:
git config --global core.autocrlf true
# macOS/Linux:
git config --global core.autocrlf input
```

### G) Netzwerk & Proxy
- Env-Variablen setzen:
  ```powershell
  $env:HTTP_PROXY="http://proxy:3128"; $env:HTTPS_PROXY="http://proxy:3128"
  ```
  ```bash
  export HTTP_PROXY=http://proxy:3128
  export HTTPS_PROXY=http://proxy:3128
  ```
- Maven-Proxy in `~/.m2/settings.xml`:
  ```xml
  <settings>
    <proxies>
      <proxy>
        <active>true</active>
        <protocol>http</protocol>
        <host>proxy</host><port>3128</port>
      </proxy>
    </proxies>
  </settings>
  ```

### H) Diagnose-Kommandos
```bash
java -version    | mvn -v
git status       | git log --oneline --graph --decorate -10
git remote -v    | git config --get remote.pushDefault
git branch -vv   | git rev-parse --abbrev-ref HEAD
mvn -q -DskipTests=false test
```

---


## Ausblick
- **Übung 2 (4.11.)**: GitHub Actions – Build & Tests automatisiert ausführen; SonarCloud integrieren.
