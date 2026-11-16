# WS3 – kaip paleisti ir parodyti

Reikia **IntelliJ IDEA** ir **Java 21**. Gradle atskirai diegti nereikia.

1. IntelliJ IDEA pasirink **File → Open** ir atidaryk `/Users/vaidotas/Projects/ws3` aplanką. Patvirtink **Trust Project** ir palauk, kol baigsis Gradle įkėlimas.
2. Dešinėje atidaryk **Gradle → Tasks → verification → check**. Dukart spausk **check**. `BUILD SUCCESSFUL` reiškia, kad testai praėjo ir JavaDoc sugeneruotas (`docs/javadoc/index.html`).
3. Gradle lange pasirink **Tasks → application → run**. Palik veikti: serveris pasiekiamas adresu `http://localhost:8081/api/students`.
4. IntelliJ atidaryk `requests.http` ir paspausk žalias rodykles prie trijų užklausų. Atsakymai turi būti `200`, `200` ir `404`.
5. WADL atidaryk adresu `http://localhost:8081/api/application.wadl`. SoapUI sukurk **REST Project** ir išbandyk `GET /students`.
6. Į **APIMATIC Transformer** įkelk `studentai.wadl`; **Swagger Editor** įklijuok `openapi.json`; į **Postman** importuok `postman_collection.json` ir išsiųsk užklausas.
7. Baigęs IntelliJ **Run** lange spausk raudoną **Stop** mygtuką.

Išsamesnis paaiškinimas: `/Users/vaidotas/Projects/PAAISKINIMAS-LT.md`.
