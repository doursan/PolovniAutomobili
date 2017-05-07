# Ucitavanje dataset-a (ne zaboraviti promeniti direktorijum workspace-a)

data = read.csv("PolovniAutomobili bez mm.csv")


# Pravljenje train i test seta

presek = round(0.9*nrow(data))

dataTrain = data[1:presek,]

dataTest = data[-(1:presek),]


# Proveravanje strukture train seta

str(dataTrain)


#Kreiranje regresionog modela

model = lm(Cena ~ . , data=dataTrain, na.action = na.exclude)


#Instalacija paketa

install.packages("polycor")

library(polycor)


# Odredjivanje korelacija dataset-a

hetcor(dataTrain)


# Liear regression assumtions

plot(model)