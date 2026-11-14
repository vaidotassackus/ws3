# Kas padaryta ir kaip pačiam paleisti

## 1. Kas tai yra

Tai maža REST interneto paslauga. Ji turi du adresus:

| Adresas | Atsakymas |
| --- | --- |
| `http://localhost:8081/api/students` | visų studentų JSON sąrašas |
| `http://localhost:8081/api/students/1` | vienas studentas |

`GET` reiškia „perskaityti“. Šiame darbe sąmoningai nėra duomenų keitimo: užduotis prašo sukurti JAX-RS paslaugą ir išbandyti užklausas, tad dviejų GET veiksmų pakanka. Duomenys saugomi programos atmintyje; nieko nereikia diegti ar konfigūruoti duomenų bazei.

Jei suprantate PHP: `StudentResource` yra panašu į maršrutų valdiklį, `Student` ir `Subject` – į duomenų modelius, `InMemoryStudentRepository` – į duomenų šaltinį, o `Application` paleidžia HTTP serverį.

## 2. Ką reikia turėti

Reikia **Java 21**. Patikrinkite terminale:

```bash
java -version
```

Jei komanda nerasta arba versija senesnė nei 21, įdiekite JDK 21. Šiame projekte yra `gradlew` (Gradle wrapper), todėl atskirai diegti Gradle nereikia. Pirmą kartą paleidus jis parsisiųs reikalingas bibliotekas; tam reikia interneto.

Terminale pereikite į aplanką, kuriame yra šis failas. Šiame kompiuteryje:

```bash
cd /Users/vaidotas/Projects/ws3
```

## 3. Paleidimas ir testai

Pirma patikrinkite kodą:

```bash
./gradlew clean check
```

`BUILD SUCCESSFUL` reiškia, kad kodas susikompiliavo, vienetiniai ir HTTP testai praėjo, o JavaDoc buvo sugeneruotas. JavaDoc pradinis puslapis: `docs/javadoc/index.html`. Atidarykite jį naršyklėje kaip failą. Windows sistemoje naudokite `gradlew.bat` vietoj `./gradlew`.

Tada paleiskite paslaugą:

```bash
./gradlew run
```

**Palikite šį terminalą atidarytą**, kol bandysite API. Pamatysite `Service: http://localhost:8081/api/students`. Sustabdymui spauskite `Ctrl+C`.

Jei 8081 prievadas užimtas, galima paleisti kitu, pavyzdžiui 8090:

```bash
./gradlew run --args='8090'
```

Tuomet visuose tolesniuose adresuose `8081` pakeiskite į `8090` (taip pat `openapi.json` ir Postman kolekcijoje).

## 4. Greitas tikrinimas be papildomų programų

Kol serveris veikia, naršyklėje atidarykite `http://localhost:8081/api/students`. Turite matyti du studentus. Vieno įrašo pavyzdys:

```json
{"id":1,"name":"Aistė","average":8.7,"active":true,"group":"A","subjects":[{"name":"Matematika","credits":6},{"name":"Programavimas","credits":8}]}
```

Terminale galima matyti ir HTTP kodą:

```bash
curl -i http://localhost:8081/api/students/1
curl -i http://localhost:8081/api/students/999
```

Pirmu atveju gausite `200 OK`, antru – `404 Not Found`.

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

Java failai yra `src/main/java/lt/example/studentapi`, testai – `src/test/java/lt/example/studentapi`. Pavadinimai laikosi Java taisyklių: paketas mažosiomis raidėmis, klasės `PascalCase`, metodai ir laukai `camelCase`. Komentarai prie viešų klasių ir metodų sudaro JavaDoc.

SOLID čia pritaikytas paprastai: modeliai saugo duomenis, saugykla juos pateikia, HTTP klasė tvarko tik užklausas; `StudentResource` priklauso nuo mažos `StudentRepository` sąsajos. Dėl to duomenų šaltinį galima pakeisti nekeičiant HTTP maršrutų. Testai dengia modelius, saugyklą, resursą, CORS filtrą ir veikiančio serverio JSON bei WADL atsakymus.

## 6. SoapUI ir WADL

1. Paleiskite serverį (`./gradlew run`).
2. Naršyklėje atidarykite `http://localhost:8081/api/application.wadl`. Tai **Jersey automatiškai sugeneruotas WADL** – XML formato API aprašas.
   To paties WADL kopija `studentai.wadl` įdėta į projektą, kad ją galėtumėte atsidaryti ir be veikiančio serverio.
3. SoapUI rinkitės naują **REST Project** ir kaip URL įveskite tą WADL adresą. Jei programa prašo įprasto endpointo, įveskite `http://localhost:8081/api/students` ir WADL atidarykite atskirai.
4. Pasirinkite `GET /students`, spauskite siuntimo mygtuką; tikėkitės `200` ir JSON masyvo. Tada išbandykite `GET /students/1` ir `GET /students/999`.

WADL yra gyvas serverio išteklius: jei serveris išjungtas, URL neveiks. `?detail=true` adreso gale pateikia išsamesnį WADL.

## 7. WADL → OpenAPI JSON ir Swagger Editor

`openapi.json` yra šio WADL **OpenAPI 3.0.3 atitikmuo**. WADL nurodo du `GET` kelius, `id` parametrą ir JSON atsakymus. Jersey sugeneruotame WADL modelių schema tuščia (`<grammars/>`), todėl `Student` ir `Subject` laukų schemos OpenAPI faile papildytos pagal Java klases. Tai svarbu: vien automatinis konverteris šių laukų iš tokio WADL neišskaitys.

Jei reikia pademonstruoti konvertavimo įrankį, įkelkite `studentai.wadl` į [APIMATIC Transformer](https://www.apimatic.io/transformer) ir pasirinkite OpenAPI JSON išvestį. [APIMATIC dokumentacijoje](https://docs.apimatic.io/v4/define-validate/transform-formats/) WADL nurodytas kaip palaikomas įvesties formatas. Jei norite atnaujinti WADL kopiją iš veikiančio serverio:

```bash
curl 'http://localhost:8081/api/application.wadl?detail=true' -o studentai.wadl
```

Palyginkite konvertuotus kelius su `openapi.json`. Jei įrankis paprašo paskyros, vertinimui jau yra paruoštas vietinis OpenAPI failas. Pagal poreikį papildykite konvertuoto failo modelių schemas iš pateikto `openapi.json`.

Atidarykite [Swagger Editor](https://editor.swagger.io/), ištrinkite pavyzdinį tekstą ir įklijuokite `openapi.json` turinį. Dešinėje matysite du `GET` veiksmus. Paspauskite vieną, tada **Try it out** → **Execute**. Serveris turi veikti jūsų kompiuteryje. Jei naršyklė blokuoja HTTP užklausą iš HTTPS Swagger Editor puslapio, naudokite Postman arba vietinį Swagger Editor; tai naršyklės mišraus turinio taisyklė, ne API klaida.

## 8. Postman

Postman programoje rinkitės **Import** ir šio projekto `postman_collection.json`. Kolekcijoje yra trys paruoštos `GET` užklausos. Paspauskite **Send** kiekvienai jų; tikėtini HTTP kodai `200`, `200`, `404`. Jei pakeitėte prievadą, kolekcijos kintamąjį `baseUrl` pakeiskite į naują adresą.

## 9. Vertinimo žingsnių atitikimas

1. Naujas Java/Gradle projektas – `build.gradle`, `settings.gradle`, `gradlew`.
2. Skirtingi laukų tipai ir priklausomų objektų sąrašas – `Student`, `Subject`.
3. JAX-RS anotacijos – `StudentResource`.
4. Publikuotas endpointas – `Application`, `./gradlew run`.
5. SoapUI ir WADL – 6 skyrius, gyvas `/api/application.wadl`.
6. OpenAPI JSON – `openapi.json`, 7 skyrius.
7. Swagger Editor – 7 skyrius.
8. Postman – `postman_collection.json`, 8 skyrius.

Reikalavimai: formatavimas ir Java pavadinimai matomi kode; testai vykdomi per `check`; JavaDoc taip pat generuojamas per `check`; atsakomybių atskyrimas ir priklausomybė nuo sąsajos paaiškinti 5 skyriuje.
