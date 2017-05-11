#Ucitavamo biblioteke
library(polycor)

library(MuMIn)

#Ucitavamo dataset
data = read.csv("PolovniAutomobili data.csv")

attach(data)

#Ispisujemo tabelu korelacija svih atributa u nasem datasetu
hetcor(data)

#Posto primecujemo veliku korelaciju izmedju KS i kW ispitujemo je dodatno 
#preko plot-a i posle toga kreiramo novi dataset i izbacujemo kW iz tog dataseta
plot(KS ~ kW)

dataBK = data

dataBK$kW = NULL

#Ponavljamo proces i pronalazimo visoku korelaciju izmedju Kubikaze i KS
# ispitujemo je dodatno preko plota i izbacujemo Kubikazu iz dataseta
hetcor(data)

plot(Kubikaza ~ KS)

dataBK$Kubikaza = NULL

plot(Poreklo ~ Registracija)

dataBK$Poreklo = NULL

hetcor(dataBK)

#Kada znamo da nema previsoke korelacije izmedju nezavisnih promenljivih, kreiramo
#prvi pun regresioni model, proveravamo i zapisujemo njegov AICc i izabiramo nezavisnu promenljivu 
#koju cemo izbaciti na osnovu p vrednosti

model = lm(Cena ~ Stanje + Tip + Godiste + Kilometraza + KS + BrojCilindara + Menjac + Boja + Registracija + Ostecenje, data = dataBK)

summary(model)

#Ovo je metoda za izvlacenje dve najvece P vrednosti (dve, u slucaju da Intercept ima najvecu vrednost)
get2TopPValues <- function (model) {
  if (class(model) == "lm")
    pvalues <- summary(model)$coefficients[,4]
  toptwo <- tail(order(pvalues), 2)
  first <- pvalues[toptwo[2]]
  second <- pvalues[toptwo[1]]
  returnVal <- c(first, second)
  return(returnVal)
}

AICc(model) #72951.87

get2TopPValues(model)

#Izbacujemo Boju iz modela i pravimo novi model i ponavljamo ceo proces

model1 = lm(Cena ~ Stanje + Tip + Godiste + Kilometraza + KS + BrojCilindara + Menjac + Registracija + Ostecenje, data = dataBK)

AICc(model1) #73100.15

get2TopPValues(model1) #Izbacujemo registraciju

#Izbacujemo Registraciju iz modela i pravimo novi model i ponavljamo ceo proces
model2 = lm(Cena ~ Stanje + Tip + Godiste + Kilometraza + KS + BrojCilindara + Menjac + Ostecenje, data = dataBK)

AICc(model2) #73097.56

get2TopPValues(model2) #Izbacujemo Tip motora

#Izbacujemo Tip motora iz modela i pravimo novi model i ponavljamo ceo proces
model3 = lm(Cena ~ Stanje + Godiste + Kilometraza + KS + BrojCilindara + Menjac + Ostecenje, data = dataBK)

AICc(model3) #73623.56

get2TopPValues(model3) #Izbacujemo Menjac

#Izbacujemo Menjac iz modela i pravimo novi model i ponavljamo ceo proces
model4 = lm(Cena ~ Stanje + Godiste + Kilometraza + KS + BrojCilindara + Ostecenje, data = dataBK)

AICc(model4) #73621.55

get2TopPValues(model4) #Izbacujemo Ostecenje

#Izbacujemo Ostecenje iz modela i pravimo novi model i ponavljamo ceo proces
model5 = lm(Cena ~ Stanje + Godiste + Kilometraza + KS + BrojCilindara, data = dataBK)

AICc(model5) #73623.15

get2TopPValues(model5) #Izbacujemo Kilometrazu

#Izbacujemo Kilometrazu iz modela i pravimo novi model i ponavljamo ceo proces
model6 = lm(Cena ~ Stanje + Godiste + KS + BrojCilindara, data = dataBK)

AICc(model6) #73775.58

get2TopPValues(model6) #Izbacujemo BrojCilindara

#Izbacujemo BrojCilindara iz modela i pravimo novi model i ponavljamo ceo proces
model7 = lm(Cena ~ Stanje + Godiste + KS, data = dataBK)

AICc(model7) #73884.49

get2TopPValues(model7) #Izbacujemo Stanje

#Izbacujemo Stanje iz modela i pravimo novi model i ponavljamo ceo proces
model8 = lm(Cena ~ Godiste + KS, data = dataBK)

AICc(model8) #74201.03

get2TopPValues(model8) #Izbacujemo Godiste

#Izbacujemo Godiste iz modela i pravimo novi model i ponavljamo ceo proces
model9 = lm(Cena ~ KS, data = dataBK)

AICc(model9) #75070.41

get2TopPValues(model9) #Nema vise varijabli za izbacivanje

#Najbolji model je pocetni, sa AICc koeficijentom 72951.87

summary(model)





