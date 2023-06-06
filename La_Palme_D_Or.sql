create schema LaPalmedOr;
use LaPalmedOr;

CREATE TABLE Waiter
(
    CF_waiter VARCHAR(16) PRIMARY KEY NOT NULL,
    Name VARCHAR(32) NOT NULL,
    Surname VARCHAR(32) NOT NULL,
    Number_orders int DEFAULT(0) NOT NULL
);
    
CREATE TABLE Cashier
(
    CF_cashier VARCHAR(16) PRIMARY KEY NOT NULL,
    Name VARCHAR(32) NOT NULL,
    Surname VARCHAR(32) NOT NULL
);

CREATE TABLE Customer
(
    CF_customer VARCHAR(16) PRIMARY KEY NOT NULL,
    Name VARCHAR(32), 
    Surname VARCHAR(32) NOT NULL,
    CF_waiter VARCHAR(16) NOT NULL,
    FOREIGN KEY (CF_waiter) REFERENCES Waiter(CF_waiter) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Receipt 
(
    SerialNumber_recipt INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    Date DATE NOT NULL,
    Tot float NOT NULL,
    Details TEXT(1024),
    CF_cashier VARCHAR(16) NOT NULL,
    CF_customer VARCHAR(16) NOT NULL,
    FOREIGN KEY (CF_cashier) REFERENCES Cashier(CF_cashier) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY (CF_customer) REFERENCES Customer(CF_customer) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Reservation
(
    Id_reservation INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    NumTable INT NOT NULL,
    Date DATETIME NOT NULL,
    Seated INT DEFAULT(0),
    InDoor TINYINT DEFAULT(1),
    CF_customer VARCHAR(16) NOT NULL,
    FOREIGN KEY (CF_customer) REFERENCES Customer (CF_customer) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE PhoneNumber
(
    Number VARCHAR(11) PRIMARY KEY NOT NULL,
    CF_customer VARCHAR(16) NOT NULL,
    FOREIGN KEY (CF_customer) REFERENCES Customer (CF_customer) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE CustomerOrder
(
    Id_customerOrder INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    CF_waiter VARCHAR(16) NOT NULL,
    CF_customer VARCHAR(16) NOT NULL,
    FOREIGN KEY (CF_waiter) REFERENCES Waiter(CF_waiter) ON UPDATE cascade ON DELETE cascade,
	FOREIGN KEY (CF_customer) REFERENCES Customer(CF_customer) ON UPDATE cascade ON DELETE cascade
);


CREATE TABLE Dish
(
    Id_dish INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    Name VARCHAR(64) NOT NULL,
    Price FLOAT NOT NULL,
    Description VARCHAR(256),
    Allergens VARCHAR(64)
);

CREATE TABLE Chef
(
    CF_chef VARCHAR(16) PRIMARY KEY NOT NULL,
    Name VARCHAR(32),
    Surname VARCHAR(16) NOT NULL,
    MichelinStar INT DEFAULT(0)
);

CREATE TABLE ComposedBy
(
    Id_customerOrder INT NOT NULL,
    Id_dish INT NOT NULL,
    Description VARCHAR(128),
    FOREIGN KEY (Id_customerOrder) REFERENCES CustomerOrder(Id_customerOrder) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY (Id_dish) REFERENCES Dish(Id_dish) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE MadeBy
(
    Id_customerOrder INT NOT NULL,
    CF_chef VARCHAR(16) NOT NULL,
    FOREIGN KEY (Id_customerOrder) REFERENCES CustomerOrder (Id_customerOrder) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY (CF_chef) REFERENCES Chef(CF_chef) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Certification
(
    Name VARCHAR(128) PRIMARY KEY NOT NULL,
    Institution VARCHAR(128) NOT NULL,
    Description VARCHAR(256) NOT NULL,
    Date_of_certification DATE NOT NULL,
    CF_chef VARCHAR(16) NOT NULL,
    FOREIGN KEY (CF_chef) REFERENCES Chef (CF_chef)
);

INSERT INTO Waiter(CF_waiter, Name, Surname, Number_orders)
VALUES  
	("BNCPLA95M13F839H", "Adriano", "Verde", 7),
	("AAAAAA01A01888O", "Paolo", "Bianchi", 0),
	("RSSLGU87S07F205G", "Luigi", "Rossi", 0),
	("RNAGNN86H06A944W", "Giovanni", "Rana", 0),
	("FRRLCU80A01G702S", "Luca", "Ferrari", 0),
	("GLLRRT02P29L781J", "Roberto", "Gallo", 3),
	("FNTMHL89A07H501Q", "Michele", "Fontana", 0),
	("TTTFNC83A16D643M", "Francesco", "Totti", 0),
	("MRTMLN91R46L049I", "Melania", "Moretti", 2),
	("BRBNGL80A41E715L", "Angela", "Barbieri", 0),
	("CRTRSO92E58D969E", "Rosa", "Carota", 6),
	("CNCLCU87P05G388L", "Luca", "Cinacchi", 0),
	("NPLCRI97A08F839X", "Ciro", "Napoli", 0),
	("LNENNA91R24C351K", "Anna", "Leone", 0),
	("PRSSMN87L60G762W", "Simona", "Parisi", 0),
	("DRCGNN71H63G224I", "Giovanna", "D'Arco", 0),
	("DNGCLD80A41E791U", "Claudia", "De Angelis", 0),
	("MRTMRC80A01H703S", "Marco", "Martinelli", 0)
;

INSERT INTO Customer(CF_customer, Name, Surname, CF_waiter)
VALUES
	("CRUGLL01P60E791X", "Giallo", "Cuore", "BRBNGL80A41E715L"),
    ("BSLMTT04D09B963F", "Mattia", "Basile", "GLLRRT02P29L781J"),
    ("RSSMRA80A01G224W", "Mario", "Rossi", "LNENNA91R24C351K"),
    ("RSSMRA80A41F205B", "Maria", "Rossi", "MRTMRC80A01H703S"),
    ("PRRPLA80A41A783Z", "Paola", "Perrotta", "CNCLCU87P05G388L")
;

INSERT INTO Cashier(CF_cashier, Name, Surname)
VALUES
	("CLLMMM02B48E791O", "Maria Immacolata", "Colella"),
    ("SRRCRL01E31L245B", "Carlo", "Sorrentino")
;

INSERT INTO Dish(Name, Price, Description, Allergens)
VALUES
	("Gnocchi alla sorrentina", 18.00, "Gnocchi gratinati al forno, filanti e gustosi con pomodoro, grana e mozzarella!", "uova, latte, sesamo, glutine"),
    ("Spaghetti alla nerano", 16.00, "Gli spaghetti alla Nerano sono un primo piatto realizzato con le zucchine fritte e provolone del Monaco.", "glutine, latte"),
    ("Impepata di cozze", 14.00, "L'impepata di cozze è una ricetta della tradizione marinara napoletana.", "molluschi"),
    ("Babà al rum", 20.00, "I babà al rum, o babbà, sono ​dolci della pasticceria napoletana tradizionale", "alcol, glutiine, latte"),
    ("Scialatielli ai frutti di mare", 18.00, "Un primo piatto ai frutti di mare semplice e adatto a tutte le stagioni.", "molluschi, glutine"),
    ("Gamberi di Sicilia", 15.00, "Gamberi di Sicilia, tartare di mozzarella, verdure in agrodolce, colatura di cime di rapa.", "crostacei, latte, glutine"),
	("Linguine di Gragnano", 16.00, "Linguine di Gragnano, calamaretti, salsa al pane di segale", "crostacei, glutine"),
    ("Spaghetto allo zafferano", 18.00, "Spaghetto allo zafferano, ricci di mare, quinoa croccante.", "glutine, pesce"),
    ("Plin di anatra", 40.00, "Plin di anatra, zuppetta di fegato grasso, latte di bufala e lampone. ", "latte, glutine"),
    ("Sgombro", 28.00, "Sgombro, avocado, caviale e champagne.", "pesce, alcol"),
    ("Piccione al Banyuls", 36.00, "Piccione, fegato grasso al gruè di cacao, salsa al Banyuls.", "alcol, glutine, arachidi"),
    ("Rombo chiodato", 31.00, "Rombo chiodato, zucca, spinaci e nocciole.", "glutine, frutta a guscio"),
    ("Trancetto esotico", 21.00, "Dolce al cioccolato e frutti esotici.", "frutta a guscio, latte, glutine, uova"),
    ("Cheesecake rafano e lampone", 17.00, "Lampone, rafano e lievito madre.", "latte, glutine, frutta a guscio")
;

INSERT INTO Chef(CF_chef, Name, Surname, MichelinStar)
VALUES
	("CNNNNN75D16F839V", "Antonino", "Cannavacciuolo", 4),
    ("BRBBRN62A12F083G", "Bruno", "Barbieri", 7),
    ("CRCCRL65R08D136M", "Carlo", "Cracco", 1),
    ("LCTGRG63D07L682R", "Giorgio", "Locatelli", 1),
    ("BRTNRC79A01G713I", "Enrico", "Bartolini", 9),
    ("RMSGDN66S08L736A", "Gordon", "Ramsay", 16)

;

INSERT INTO CustomerOrder( CF_waiter, CF_customer)
VALUES
    ("BNCPLA95M13F839H", "CRUGLL01P60E791X"),
    ("GLLRRT02P29L781J", "BSLMTT04D09B963F"),
    ("CRTRSO92E58D969E", "RSSMRA80A01G224W"),
    ("BNCPLA95M13F839H", "RSSMRA80A01G224W"),
    ("GLLRRT02P29L781J", "BSLMTT04D09B963F"),
    ("AAAAAA01A01888O", "CRUGLL01P60E791X"),
    ("PRSSMN87L60G762W", "RSSMRA80A01G224W"),
    ("LNENNA91R24C351K", "RSSMRA80A01G224W"),
    ("LNENNA91R24C351K", "BSLMTT04D09B963F"),
    ("PRSSMN87L60G762W", "CRUGLL01P60E791X"),
    ("GLLRRT02P29L781J", "RSSMRA80A41F205B"),
    ("MRTMRC80A01H703S", "RSSMRA80A41F205B"),
    ("MRTMRC80A01H703S", "BSLMTT04D09B963F"),
    ("MRTMRC80A01H703S", "RSSMRA80A41F205B"),
    ("MRTMRC80A01H703S", "RSSMRA80A41F205B"),
    ("BNCPLA95M13F839H", "RSSMRA80A01G224W"),
    ("NPLCRI97A08F839X", "BSLMTT04D09B963F"),
    ("CRTRSO92E58D969E", "CRUGLL01P60E791X")
;



INSERT INTO MadeBy(Id_customerOrder, CF_chef)
VALUES
    (1, "CNNNNN75D16F839V"),
    (2, "LCTGRG63D07L682R"),
    (3, "BRBBRN62A12F083G"),
    (4, "CRCCRL65R08D136M"),
    (5, "LCTGRG63D07L682R"),
    (6, "BRBBRN62A12F083G"),
    (7, "CNNNNN75D16F839V"),
    (8, "LCTGRG63D07L682R"),
    (9, "BRBBRN62A12F083G"),
    (10, "CNNNNN75D16F839V"),
    (11, "LCTGRG63D07L682R"),
    (12, "RMSGDN66S08L736A"),
    (13, "LCTGRG63D07L682R"),
    (14, "LCTGRG63D07L682R"),
    (15, "BRBBRN62A12F083G"),
    (16, "BRBBRN62A12F083G"),
    (17, "RMSGDN66S08L736A"),
    (18, "RMSGDN66S08L736A")
;

INSERT INTO Receipt(Date, Tot, Details, CF_cashier,  CF_customer)
VALUES
	('2022-1-31', 51, "Gnocchi alla sorrentina, Spaghetti alla nerano, Cheesecake rafano e lampone", "SRRCRL01E31L245B", "CRUGLL01P60E791X"),
    ('2022-1-31', 70, "Piccione al Banyuls, Babà al rum, Impepata di cozze", "SRRCRL01E31L245B", "BSLMTT04D09B963F"),
    ('2022-1-31', 58, "Spaghetto allo zafferano, Plin di anatra", "SRRCRL01E31L245B", "RSSMRA80A01G224W"),
    ('2022-1-31', 51, "Gnocchi alla sorrentina, Spaghetti alla nerano, Cheesecake rafano e lampone", "SRRCRL01E31L245B", "RSSMRA80A41F205B"),
    ('2022-2-1', 70, "Piccione al Banyuls, Babà al rum, Impepata di cozze", "SRRCRL01E31L245B", "PRRPLA80A41A783Z")
;

INSERT INTO Certification(Name, Institution, Description, Date_of_certification, CF_chef)
VALUES
	("Tecniche e rispetto degli ingredienti naturali", "Auguste Escoffier School of Culinary Arts", " Le lezioni sono focalizzate non soltanto sulle tecniche ma anche sul rispetto degli ingredienti, locali e naturali.", '2018-3-2', "CNNNNN75D16F839V"),
    ("Cucina Francese", "Le Cordon Bleu", "Le Cordon Blue è una delle più antiche e conosciute istituzioni culinarie al mondo e custodisce i segreti della cucina francese.", '2009-8-23', "BRBBRN62A12F083G"),
    ("Innovazioni culinarie", " Culinary Institute of Barcelona", " Il metodo si discosta da quello tradizionale di far pratica sulle ricette per lanciarsi nel movimentato mondo delle innovazioni, basandosi su prodotto, contesto e tecnica e sul concetto di problem solving e versatilità.", '2015-4-10', "RMSGDN66S08L736A")
;