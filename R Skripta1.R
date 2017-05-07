#Ucitavamo dataset
data = read.csv("PolovniAutomobili data.csv")

attach(data)

#Pravimo pocetni lm model
model.lm = lm (Cena ~ ., data=data)

#Istrazujemo plot kako bi ustanovili da li su ispunjene pretpostavke linearne regresije
plot(model.lm)

#Posto pretpostavke nisu ispunjene, pravimo glm model
model.glm = glm(Cena ~ ., data=data, family=poisson)

#Izracunavamo AICc za glm model
library(MuMIn)

AICc(model.glm)

#Ispisujemo tabelu korelacija svih atributa u nasem datasetu
library(polycor)

hetcor(data)

#Posto primecujemo veliku korelaciju izmedju KS i kW ispitujemo je dodatno 
#preko plot-a i posle toga izbacujemo kW iz dataseta
plot(KS ~ kW)

data$kW = NULL

#Pravimo novi model bez kW i izracunavamo AICc za isti
model1 = glm(Cena ~ ., data=data, family=poisson)

AICc(model1)

#Ponavljamo proces i pronalazimo visoku korelaciju izmedju Kubikaze i KS
# ispitujemo je dodatno preko plota i izbacujemo KS iz dataseta
hetcor(data)

plot(Kubikaza ~ KS)

data$KS = NULL

#Pravimo novi model bez KS i izracunavamo AICc za isti
model2 = glm(Cena ~ ., data=data, family=poisson)

AICc(model2)

#Proveravamo odnos srednje vrednosti i varijanse Cene
mean(Cena)

var(Cena)

#Zakljucujemo da postoji overdispersion i zbog toga primenjujemo quasipoisson 
#za family parametar
library(arm)

model2.quasi = glm(Cena ~ ., data=data, family=quasipoisson)

c1 = coef(model2)

c_qmle = coef(model2.quasi)

s1 = se.coef(model2)

s_qmle = se.coef(model2.quasi)

cbind(c1,c_qmle,s1,s_qmle)
