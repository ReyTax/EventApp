# EventApp


****Ce este EventApp?****

EventApp este o aplicatie mobila pentru Android si reprezinta o retea de socializare ce se centreaza pe organizarea de evenimente si interactiunea dintre utilizatori.
Aplicatia pune la dispozitie utilizatorilor diferite functionalitati cum ar fi: crearea unui cont si setarea unui profil, lista de prieteni, chat, crearea si cautarea de evenimente, agenda si altele.
Principalul obiectiv al acestei aplicatii este de a oferii o experienta cat mai placuta, completa si usoara de inteles persoanelor care vor sa participe la evenimente.

****Tehnologii folosite****

Limbajul de programare folosit in cadrul acestei aplicatii este JAVA. Aplicatia a fost dezvoltata folosind Android Studio. Acesta este mediul integrat de dezvoltare oficial pentru Android. Pentru a verifica datele introduse de utilizatori am folosit urmatoarele API-uri:

1. REST API for countries, states and cities pentru a avea acces la o baza de date cu tari, judete si orase.

2. PurgoMalum API pentru a filtra cuvintele ofensive din limba engleza. 

Au fost folosite mai multe servicii Firebase pentru a stoca si administra datele utilizatorilor cat si continutul generat de acestia. Aceste servicii sunt:

1. Firebase Authentication pentru a asigura securitatea datelor de autentificare

2. Firebase Firestore pentru a stoca si administra datele utilizatorilor si ale evenimentelor.

3. Firebase Storage pentru a stoca continut media

****Proiectare si implementare****

**Procesul de autentificare:**

1. Procesul de autetificare implementat este unul standard specific aplicatiilor de tip retele de socializare. Acesta este format din trei componente:	

2. Inregistrare: completarea unor campuri de catre utilizator si trimiterea acestora catre Firebase Authentication pentru a crea un cont personal. In procesul de inregistrare utilizatorul va trebui sa isi confirme contul accesand un link primit pe adresa de email introdusa.

3. Autentificare: utilizatorul va introduce datele personale de autentificare pentru a accesa funtionalitatile aplicatiei.

4. Recuperarea parolei: in cazul in care utilizatorul a uitat parola acesta poate sa o recupereze folosind adresa de email prin care s-a inregistrat.

**Meniuri:**

1. Meniul principal este locul de unde utilizatorul poate sa acceseze principalele functionalitati ale aplicatiei. Acesta se bazeaza pe un FrameLayout si contine 5 butoane: Create Event, Search Event, Friend List, Search User, Calendar.

2. Meniul lateral este un meniu auxiliar ce poate fi accesat de oriunde din aplicatie. Acesta dispune de un toolbar, un buton pentru a face meniul lateral sa apara si un FrameLayout in care sunt incarcate restul activitatiilor.

**Funtionalitati:**

1. Prima funtionalitate ce are legatura cu organizarea de evenimente este activitatea de creare eveniment. Aceasta este formata dintr-o activitate si cinci fragmente. Activitatea este folosita pentru a parcurge fragmentele, iar fragmentele contin campuri ce trebuie completate de catre utilizator si au ca scop colectarea tuturor datelor necesare pentru a crea un anunt despre un eveniment.

2. A doua functionalitate care are legatura cu organizarea evenimentelor este activitatea prin care se pot cauta evenimente. Aceasta este formata dint-un RecyclerView ce primeste informatii din Firebase in timp real si contine lista de evenimente sortate dupa data la care au fost create. Aceste evenimente pot fi accesate, iar utilizatorii isi pot anunta participarea la acestea.

3. Pagina de profil si setari. Fiecare utilizator are un profil propriu ce poate fi modificat. Modificarile respective sunt analizate de API-urile implementate. Pe langa asta API-ul care retuneaza tari mai este utilizat si ca Context Menu pentru a ajuta utilizatorul sa isi gaseasca locatia mai repede.

4. Cautare utilizatori. Pentru a gasi un utilizator a fost implementata o activitate similara celei pentru a cauta evenimente. Prin itermediul ei utilizatorul curent poate vedea profilurile celorlati utilizatori si ii poate adauga la prieteni.

5. Lista de prieteni. Lista de prieteni este formata dintr-o activitate si doua fragmente. Un fragment pentru cereri de prietenie si alt fragment pentru prietenii actuali.

6. Chat. Daca doi utilizatori s-au adaugat in lista de prieteni acestia pot comunica printr-un chat in timp real implementat prin Firebase Firestore.

7. Agenda este o functionalitate prin care ii sunt organizate evenimentele utilizatorului curent pentru ai fi mai usor de urmarit la evenimentele la care participa. Aceasta este formata dintr-o activitate si doua fragmente. Primul fragment contine date despre istoricul evenimentelor la care utilizatorul participa.Al doilea fragment contine un calendar special unde apar notate toate datele importante.



![Procesul de autetificare](https://i.imgur.com/z6G8xYX.png)
![Meniu principal si lateral](https://i.imgur.com/TfbSShA.png)
![Creare si cautare eveniment](https://i.imgur.com/RiPM2uW.png)
