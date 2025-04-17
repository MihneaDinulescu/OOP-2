# Vehicle Rental System

## Descriere
Acesta este un sistem de închiriere vehicule care permite gestionarea vehiculelor, rezervărilor, clienților, angajaților și locațiilor. Sistemul permite utilizatorilor să facă rezervări de vehicule disponibile, să efectueze plăți, să adauge recenzii și să vizualizeze detalii ale rezervărilor. De asemenea, include managementul vehiculelor, locațiilor și angajaților, oferind un sistem complet pentru gestionarea unei firme de închirieri vehicule.

## Funcționalități
1. Căutare vehicul disponibil  
2. Verificare disponibilitate vehicul  
3. Adăugare vehicul în sistem  
4. Asociere vehicul cu rezervare  
5. Calcul preț închiriere / tip vehicul  
6. Adăugare locație în sistem  
7. Adăugare rezervare în sistem  
8. Afișare detalii rezervare  
9. Asocierea recenziei la rezervare  
10. Efectuare plată  
11. Adăugare angajat în sistem  
12. Sortare clienți în ordine alfabetică  

## Clasele din Sistem
1. **Angajat**  
   Reprezintă un angajat al companiei de închiriere vehicule, având informații precum ID, nume, prenume, telefon, email, funcție și locație asociată. De asemenea, un angajat poate avea vehicule alocate.

2. **Bicicleta**  
   Reprezintă o bicicletă disponibilă pentru închiriere, care moștenește comportamentul unui vehicul. Are un preț de închiriere specific pe zi.

3. **Camion**  
   Reprezintă un camion disponibil pentru închiriere, care moștenește comportamentul unui vehicul. Are un preț de închiriere specific pe zi.

4. **Client**  
   Reprezintă un client al sistemului de închiriere, care poate face rezervări. Are informații precum ID, nume, prenume, număr de telefon, email și o listă de rezervări făcute.

5. **Locatie**  
   Reprezintă o locație a companiei de închirieri, incluzând detalii precum ID, țară, oraș, stradă și număr stradă. De asemenea, poate avea angajați asociați.

6. **Masina**  
   Reprezintă o mașină disponibilă pentru închiriere, care moștenește comportamentul unui vehicul. Are un preț de închiriere specific pe zi.

7. **Recenzie**  
   Reprezintă o recenzie a unei rezervări, inclusiv ratingul în stele, data recenziei și rezervarea asociată.

8. **Rentabil**  
   Interfață care definește metoda `calculeazaPretInchiriere(int zile)`, utilizată pentru a calcula prețul de închiriere în funcție de tipul vehiculului.

9. **Rezervare**  
   Reprezintă o rezervare făcută de un client, cu detalii precum ID, clientul care a făcut rezervarea, avansul plătit, metoda de plată și lista de vehicule rezervate.

10. **RezervareVehicul**  
    Reprezintă un tabel intermediar între **Vehicul** și **Rezervare**, care leagă aceste două entități. Această clasă gestionează relația mulți-la-mulți dintre vehicule și rezervări, stocând informațiile despre vehiculele rezervate într-o anumită perioadă (start și end date), asociate unei rezervări.

11. **Vehicul**  
    Clasa de bază pentru toate tipurile de vehicule (biciclete, mașini, camioane). Contine informații precum ID vehicul, număr de înmatriculare, model, angajat asociat și culoare. 

12. **VehiculIndisponibilException**  
    Excepție aruncată atunci când un vehicul nu este disponibil în perioada dorită pentru închiriere.
    
14. **ServiceManager**  
    Clasa singleton care oferă metodele de interacțiune cu sistemul, inclusiv pentru verificarea disponibilității vehiculului, adăugarea vehiculului în sistem, gestionarea rezervărilor și efectuarea plăților. Este responsabilă de logica de afaceri și de gestionarea entităților în cadrul sistemului.


