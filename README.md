# WS3 – studentų JAX-RS REST paslauga

Šį projektą atidarykite **IntelliJ IDEA**. Reikia Java 21; Gradle atskirai diegti nereikia, nes projekte yra Gradle wrapperis.

1. IntelliJ IDEA rinkitės **File → Open** ir atidarykite visą `/Users/vaidotas/Projects/ws3` aplanką. Jei klausia, patvirtinkite **Trust Project** ir Gradle projekto įkėlimą. Palaukite, kol baigsis sinchronizavimas.
2. Dešinėje atidarykite **Gradle** langą (jei jo nematote: **View → Tool Windows → Gradle**). Išskleiskite `student-jaxrs-service → Tasks`.
3. Dukart spauskite **verification → check**. Užrašas `BUILD SUCCESSFUL` reiškia, kad testai praėjo ir JavaDoc sugeneruotas.
4. Dukart spauskite **application → run**. Palikite šią užduotį veikti. Paslauga bus adresu <http://localhost:8081/api/students>, WADL – <http://localhost:8081/api/application.wadl>.
5. IntelliJ projekto lange atidarykite `requests.http` ir spauskite žalią rodyklę prie kiekvienos užklausos. Atsakymuose tikėkitės `200`, `200`, `404`.
6. Baigę paspauskite raudoną **Stop** mygtuką IntelliJ **Run** lange.

Jei IntelliJ prašo JDK, **File → Project Structure → Project → SDK** pasirinkite Java 21. Taip pat **Settings → Build, Execution, Deployment → Build Tools → Gradle → Gradle JVM** pasirinkite Java 21.

Visas kodo paaiškinimas ir SoapUI, Swagger Editor bei Postman veiksmai yra [PAAISKINIMAS-LT.md](PAAISKINIMAS-LT.md).
