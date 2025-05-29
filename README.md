# Sistem de Rezervări Vehicule

## Descriere generală
Acest proiect este un sistem de închiriere vehicule (Vehicle Rental System), dezvoltat în Java, care gestionează relațiile dintre clienți, vehicule, angajați, locații, rezervări și recenzii. Sistemul permite atât înregistrarea și administrarea entităților, cât și generarea unor statistici și rapoarte utile pentru o companie de închirieri auto.

Datele sunt gestionate într-o bază de date relațională, iar accesul se face prin JDBC. Codul este organizat pe principiile OOP, cu servicii singleton pentru logica de business, un sistem de audit pentru trasabilitate și o interfață simplă prin main() care evidențiază funcționalitățile-cheie.

---

## Structura și descrierea entităților

### Client
Reprezintă clienții care pot efectua rezervări.
- Identificator unic client
- Nume și prenume
- Număr de telefon
- Email

### Rezervare
Reprezintă rezervările realizate de clienți.
- Identificator unic rezervare
- Referință către client
- Avans plătit (opțional)
- Metodă de plată

### Vehicul
Reprezintă vehiculele disponibile pentru închiriere.
- Identificator unic vehicul
- Număr de înmatriculare (unic)
- Model vehicul
- Referință către angajat responsabil
- Culoare
- Tip vehicul

### Angajat
Reprezintă angajații care gestionează vehicule și locații.
- Identificator unic angajat
- Nume și prenume
- Număr de telefon
- Email
- Funcție
- Referință către locație

### RezervareVehicul
Leagă o rezervare de vehiculul rezervat, incluzând perioada rezervării.
- Identificator unic rezervare-vehicul
- Referință către rezervare
- Referință către vehicul
- Data de început a rezervării
- Data de sfârșit a rezervării

### Recenzie
Reprezintă feedback-ul oferit pentru o rezervare.
- Identificator unic recenzie
- Număr de stele (1-5)
- Data recenziei
- Referință către rezervare

### Locatie
Reprezintă locațiile unde sunt angajați și vehicule.
- Identificator unic locație
- Țara
- Oraș
- Strada
- Numărul

---

## Tipuri de obiecte în sistem

- **Client** – utilizator care face rezervări  
- **Rezervare** – comandă pentru închirierea unui vehicul  
- **Vehicul** – mijloc de transport închiriabil  
  - **Camion** – vehicul de transport marfă  
  - **Bicicletă** – vehicul ușor, ecologic  
  - **Mașină** – vehicul personal sau de familie  
- **Angajat** – persoană responsabilă de vehicule și locații  
- **RezervareVehicul** – legătura între rezervare și vehicul, cu perioada de închiriere  
- **Recenzie** – feedback acordat unei rezervări  
- **Locație** – punct geografic cu angajați și vehicule  

---

## Funcționalități principale

1. **Top 3 clienți cu cele mai multe rezervări**  
   Afișează primii trei clienți care au efectuat cele mai multe rezervări.

2. **Vehicule disponibile într-o anumită perioadă**  
   Listează vehiculele care nu sunt rezervate între două date specificate.

3. **Angajatul cu cele mai multe vehicule gestionate**  
   Identifică angajatul care are în grijă cele mai multe vehicule.

4. **Mapare client – număr total rezervări**  
   Prezintă o listă a clienților și numărul total de rezervări făcute de fiecare.

5. **Media recenziilor pentru un vehicul**  
   Calculează media rating-urilor pentru un vehicul specific.

6. **Angajați, locații și numărul total de vehicule gestionate**  
   Afișează angajații împreună cu locațiile lor și numărul de vehicule gestionate de fiecare.

7. **Top 5 vehicule cu cea mai mare medie a ratingului (minim 5 recenzii)**  
   Listează cele mai bine cotate 5 vehicule, ținând cont de un număr minim de recenzii.

8. **Clienți cu avans maxim plătit**  
   Afișează clienții și valoarea maximă a avansului plătit într-o rezervare.

9. **Calcul total plată pentru o rezervare**  
   Calculează suma totală de plată aferentă unei rezervări date.

10. **Locații și numărul de rezervări**  
    Afișează locațiile împreună cu numărul de rezervări realizate în fiecare.

11. **Vehicule care nu au fost rezervate niciodată**  
    Listează vehiculele fără nici o rezervare.

12. **Număr de rezervări pe lunile anului**  
    Prezintă distribuția numărului de rezervări pe fiecare lună.

13. **Distribuția vehiculelor pe locații**  
    Arată câte vehicule sunt alocate pentru fiecare locație.

14. **Recenzii negative**  
    Listează recenziile cu rating scăzut.

15. **Locații fără angajați**  
    Afișează locațiile unde nu sunt angajați asignați.

---

