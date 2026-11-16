# WS3: kas padaryta ir kaip naudoti su IntelliJ IDEA

## 1. Kas tai yra

Tai maža REST interneto paslauga. Ji turi du adresus:

| Adresas | Atsakymas |
| --- | --- |
| `http://localhost:8081/api/students` | visų studentų JSON sąrašas |
| `http://localhost:8081/api/students/1` | vienas studentas |

`GET` reiškia „perskaityti“. Šiame darbe sąmoningai nėra duomenų keitimo: užduotis prašo sukurti JAX-RS paslaugą ir išbandyti užklausas, tad dviejų GET veiksmų pakanka. Duomenys saugomi programos atmintyje; nieko nereikia diegti ar konfigūruoti duomenų bazei.

Jei suprantate PHP: `StudentResource` yra panašu į maršrutų valdiklį, `Student` ir `Subject` – į duomenų modelius, `InMemoryStudentRepository` – į duomenų šaltinį, o `Application` paleidžia HTTP serverį.

## 2. Projekto atidarymas IntelliJ IDEA

1. Atidarykite **IntelliJ IDEA** ir rinkitės **File → Open**.
2. Pasirinkite **visą** `/Users/vaidotas/Projects/ws3` aplanką, ne atskirą `.java` failą. Paspauskite **Open**.
3. Jei IntelliJ klausia, rinkitės **Trust Project** ir leiskite įkelti **Gradle** projektą. Palaukite, kol apačioje baigsis Gradle sinchronizavimas. Pirmą kartą reikės interneto bibliotekoms atsisiųsti.
4. Jei prašo JDK: **File → Project Structure → Project → SDK** pasirinkite **Java 21**. Jei jo nėra, tame pačiame SDK pasirinkimo meniu naudokite **Download JDK** ir pasirinkite 21 versiją.
5. **Settings → Build, Execution, Deployment → Build Tools → Gradle → Gradle JVM** taip pat pasirinkite **Java 21**. Mac sistemoje „Settings“ gali būti **IntelliJ IDEA → Settings**.

Gradle atskirai diegti nereikia: projektas turi Gradle wrapperį. Viską toliau paleisite IntelliJ mygtukais.

## 3. Testai ir JavaDoc IntelliJ aplinkoje

1. Dešinėje atidarykite **Gradle** langą. Jei jo nematote, rinkitės **View → Tool Windows → Gradle**.
2. Išskleiskite `student-jaxrs-service → Tasks → verification` ir dukart spauskite **check**.
3. Apačioje **Run** lange laukite `BUILD SUCCESSFUL`. Tai reiškia, kad kodas susikompiliavo, praėjo 10 testų ir sugeneruotas JavaDoc.
4. JavaDoc pradinis puslapis yra `docs/javadoc/index.html`. IntelliJ **Project** lange susiraskite šį failą ir atidarykite naršyklėje.

Jei **Gradle** lange užduočių nesimato, paspauskite **Reload All Gradle Projects** (apskritų rodyklių mygtukas) ir palaukite sinchronizavimo.

## 4. Serverio paleidimas ir užklausos IntelliJ aplinkoje

1. Gradle lange išskleiskite `student-jaxrs-service → Tasks → application` ir dukart spauskite **run**.
2. Apačioje **Run** lange matysite `Service: http://localhost:8081/api/students` ir `WADL: http://localhost:8081/api/application.wadl`. **Palikite šią užduotį veikti**, kol bandote API. Ilgai veikiantis `run` yra normalu: serveris laukia užklausų.
3. IntelliJ **Project** lange atidarykite `requests.http`. Virš kiekvienos `GET` eilutės spauskite žalią **Run** rodyklę. IntelliJ HTTP Client parodys atsakymo kodą ir turinį.
4. Pirmos dvi užklausos turi grąžinti `200 OK` su JSON, trečia – `404 Not Found`. Baigę spauskite raudoną **Stop** mygtuką **Run** lange.

Jei prie `GET` eilučių nėra žalių rodyklių, IntelliJ **Settings → Plugins** patikrinkite, ar įjungtas **HTTP Client** papildinys. Tuos pačius adresus galite atidaryti ir naršyklėje.

Jei matote `Address already in use`, 8081 prievadą jau naudoja kita programa. IntelliJ **Run → Edit Configurations** atidarykite Gradle `run` konfigūraciją ir užduoties argumentuose prie `run` pridėkite `--args=8090`. Tada visuose žemiau esančiuose adresuose, `requests.http`, `openapi.json` ir Postman kolekcijoje `8081` pakeiskite į `8090`.

Pirmojo studento atsakyme matysite, pavyzdžiui:

```json
{"id":1,"name":"Aistė","average":8.7,"active":true,"group":"A","subjects":[{"name":"Matematika","credits":6},{"name":"Programavimas","credits":8}]}
```

## 5. Kodo sandara ir kodėl taip

| Failas | Paskirtis |
| --- | --- |
| `Student.java` | `int`, `String`, `float`, `boolean`, `char` ir `List<Subject>` laukai – visi užduotyje prašomi tipai |
| `Subject.java` | priklausomas objektas su pavadinimu ir kreditais |
| `StudentRepository.java` | siaura sąsaja duomenims skaityti |
| `InMemoryStudentRepository.java` | du pavyzdiniai studentai be duomenų bazės |
| `StudentResource.java` | JAX-RS `@Path`, `@GET`, `@PathParam`, `@Produces` anotacijos |
| `Application.java` | paleidžia Jersey ir Grizzly HTTP serverį |
| `CorsFilter.java` | leidžia Swagger Editor naršyklei kreiptis į vietinę API |

Java failus IntelliJ **Project** lange rasite `src/main/java/lt/example/studentapi`, testus – `src/test/java/lt/example/studentapi`. Pavadinimai laikosi Java taisyklių: paketas mažosiomis raidėmis, klasės `PascalCase`, metodai ir laukai `camelCase`. Komentarai prie viešų klasių ir metodų sudaro JavaDoc.

SOLID čia pritaikytas paprastai: modeliai saugo duomenis, saugykla juos pateikia, HTTP klasė tvarko tik užklausas; `StudentResource` priklauso nuo mažos `StudentRepository` sąsajos. Dėl to duomenų šaltinį galima pakeisti nekeičiant HTTP maršrutų. Testai dengia modelius, saugyklą, resursą, CORS filtrą ir veikiančio serverio JSON bei WADL atsakymus.

## 6. SoapUI ir WADL

1. Paleiskite serverį IntelliJ **Gradle → Tasks → application → run** užduotimi ir palikite ją veikti.
2. Naršyklėje atidarykite `http://localhost:8081/api/application.wadl`. Tai **Jersey automatiškai sugeneruotas WADL** – XML formato API aprašas.
   To paties WADL kopija `studentai.wadl` įdėta į projektą, kad ją galėtumėte atsidaryti ir be veikiančio serverio.
3. SoapUI rinkitės naują **REST Project** ir kaip URL įveskite tą WADL adresą. Jei programa prašo įprasto endpointo, įveskite `http://localhost:8081/api/students` ir WADL atidarykite atskirai.
4. Pasirinkite `GET /students`, spauskite siuntimo mygtuką; tikėkitės `200` ir JSON masyvo. Tada išbandykite `GET /students/1` ir `GET /students/999`.

WADL yra gyvas serverio išteklius: jei serveris išjungtas, URL neveiks. `?detail=true` adreso gale pateikia išsamesnį WADL.

## 7. WADL → OpenAPI JSON ir Swagger Editor

`openapi.json` yra šio WADL **OpenAPI 3.0.3 atitikmuo**. WADL nurodo du `GET` kelius, `id` parametrą ir JSON atsakymus. Jersey sugeneruotame WADL modelių schema tuščia (`<grammars/>`), todėl `Student` ir `Subject` laukų schemos OpenAPI faile papildytos pagal Java klases. Tai svarbu: vien automatinis konverteris šių laukų iš tokio WADL neišskaitys.

IntelliJ **Project** lange atidarykite `studentai.wadl` ir `openapi.json`. Norėdami pademonstruoti konvertavimo įrankį, įkelkite `studentai.wadl` į [APIMATIC Transformer](https://www.apimatic.io/transformer) ir pasirinkite OpenAPI JSON išvestį. [APIMATIC dokumentacijoje](https://docs.apimatic.io/v4/define-validate/transform-formats/) WADL nurodytas kaip palaikomas įvesties formatas.

Palyginkite konvertuotus kelius su `openapi.json`. Jei įrankis paprašo paskyros, vertinimui jau yra paruoštas vietinis OpenAPI failas. Pagal poreikį papildykite konvertuoto failo modelių schemas iš pateikto `openapi.json`.

Atidarykite [Swagger Editor](https://editor.swagger.io/). IntelliJ atidarykite `openapi.json`, nukopijuokite jo turinį ir įklijuokite vietoj pavyzdinio teksto. Dešinėje matysite du `GET` veiksmus. Paspauskite vieną, tada **Try it out → Execute**. Serveris turi veikti IntelliJ. Jei naršyklė blokuoja HTTP užklausą iš HTTPS puslapio, tą patį atsakymą patikrinkite IntelliJ `requests.http` arba Postman.

## 8. Postman

Postman programoje rinkitės **Import** ir iš IntelliJ projekto aplanko `/Users/vaidotas/Projects/ws3` pasirinkite `postman_collection.json`. Kolekcijoje yra trys paruoštos `GET` užklausos. Kol serveris veikia IntelliJ, paspauskite **Send** kiekvienai jų; tikėtini HTTP kodai `200`, `200`, `404`. Jei pakeitėte prievadą, kolekcijos kintamąjį `baseUrl` pakeiskite į naują adresą.

## 9. Vertinimo žingsnių atitikimas

1. Naujas Java/Gradle projektas – `build.gradle`, `settings.gradle`, `gradlew`.
2. Skirtingi laukų tipai ir priklausomų objektų sąrašas – `Student`, `Subject`.
3. JAX-RS anotacijos – `StudentResource`.
4. Publikuotas endpointas – `Application`, IntelliJ Gradle `run` užduotis.
5. SoapUI ir WADL – 6 skyrius, gyvas `/api/application.wadl`.
6. OpenAPI JSON – `openapi.json`, 7 skyrius.
7. Swagger Editor – 7 skyrius.
8. Postman – `postman_collection.json`, 8 skyrius.

Reikalavimai: formatavimas ir Java pavadinimai matomi kode; testai ir JavaDoc tikrinami IntelliJ Gradle `check` užduotimi; atsakomybių atskyrimas ir priklausomybė nuo sąsajos paaiškinti 5 skyriuje.
