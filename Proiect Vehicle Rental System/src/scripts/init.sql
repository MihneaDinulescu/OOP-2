CREATE TABLE Client (
                        idClient BIGINT PRIMARY KEY ,
                        nume VARCHAR(100) NOT NULL,
                        prenume VARCHAR(100) NOT NULL,
                        numarTelefon VARCHAR(20),
                        email VARCHAR(100)
);


CREATE TABLE Rezervare (
                           idRezervare BIGINT PRIMARY KEY ,
                           idClient BIGINT NOT NULL,
                           avans DECIMAL(10,2) NULL,
                           metodaPlata VARCHAR(50),
                           FOREIGN KEY (idClient) REFERENCES Client(idClient) ON DELETE CASCADE
);


CREATE TABLE Vehicul (
                         idVehicul BIGINT PRIMARY KEY ,
                         numarInmatriculare VARCHAR(20) NOT NULL UNIQUE,
                         model VARCHAR(100) NOT NULL,
                         idAngajat BIGINT NOT NULL,
                         culoare VARCHAR(50),
                         tipVehicul VARCHAR(50),
                         FOREIGN KEY (idAngajat) REFERENCES Angajat(idAngajat) ON DELETE SET NULL
);


CREATE TABLE Angajat (
                         idAngajat BIGINT PRIMARY KEY ,
                         numeAngajat VARCHAR(100) NOT NULL,
                         prenumeAngajat VARCHAR(100) NOT NULL,
                         nrTelefon VARCHAR(20),
                         emailAngajat VARCHAR(100),
                         functie VARCHAR(100),
                         idLocatie BIGINT,
                         FOREIGN KEY (idLocatie) REFERENCES Locatie(idLocatie)
);


CREATE TABLE RezervareVehicul (
                                  idRezervareVehicul BIGINT PRIMARY KEY ,
                                  idRezervare BIGINT NOT NULL,
                                  idVehicul BIGINT NOT NULL,
                                  startDate DATE NOT NULL,
                                  endDate DATE NOT NULL,
                                  FOREIGN KEY (idRezervare) REFERENCES Rezervare(idRezervare) ON DELETE CASCADE,
                                  FOREIGN KEY (idVehicul) REFERENCES Vehicul(idVehicul) ON DELETE CASCADE
);


CREATE TABLE Recenzie (
                          idRecenzie BIGINT PRIMARY KEY ,
                          nrStele INT NOT NULL CHECK (nrStele >= 1 AND nrStele <= 5),
                          dataRecenzie DATE NOT NULL,
                          idRezervare BIGINT NOT NULL,
                          FOREIGN KEY (idRezervare) REFERENCES Rezervare(idRezervare) ON DELETE CASCADE
);



CREATE TABLE Locatie (
                         idLocatie BIGINT PRIMARY KEY ,
                         tara VARCHAR(100) NOT NULL,
                         oras VARCHAR(100) NOT NULL,
                         strada VARCHAR(150) NOT NULL,
                         nrStrada INT
);

