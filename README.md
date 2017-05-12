# Predviđanje cene polovnih automobila

##	O projektu
Tema ovog projekta je predviđanje cene motora u oglasima na osnovu karakteristika motora. Podaci korišćeni u okviru ovog projekta su dobijeni sa sajta za prodaju polovnih motornih vozila 
[PolovniAutomobili.com](https://www.polovniautomobili.com/motori), odakle su preuzeti podaci o oglasima za motore. Izvršena je analiza atributa motora koji potencijalno mogu da utiču na cenu i nad njima je kreiran model linearne regresije koji pokazuje na koji način i u kojoj meri određena karakteristika motora utiče na cenu.


##	Linerna regresija
Linearna regresija predstavlja osnovni tip regresije. Generalna ideja regresije je da odgovori na dva skupa pitanja: 
1. da li skup nezavisnih promenljivih obavlja dobar posao u predviđanju zavisne promenljive? Da li model koji koristi nezavisne promenljive uzima u obzir varijabilnost u promenama zavisne promenljive? 
2. Koje konkretne nezavisne promenljive su značajni prediktori zavisne promenljive i na koji način one, određene vrednošću i znakom beta koeficijenata, utiču na zavisnu promenljivu. 

Ovi regresioni koeficijenti se koriste da objasne odnos između jedne zavisne varijable i jedne ili više nezavisnih varijabli. Najprostiji oblik jednačine sa jednom zavisnom i jednom nezavisnom promenljivom je definisan formulom 
```
y = c + b*x
```

gde je:   
y - zavisna promenljiva,  
c - konstanta (vrednost zavisne promenljive u slučaju da je nezavisna promenljiva jednaka nuli),   
b - regresioni koeficijent nezavisne promenljive,  
x = nezavisna promenljiva [1].    
  
Akaike informacioni kriterijum (poznatiji kao **AIC)** je kriterijum za izbor između statističkih ili ekonometrijskih modela. AIC je u suštini procenjena mera kvaliteta svakog od raspoloživih modela koji su međusobno povezani datasetom nad kojim su kreirani, što ga čini idealnim metodom za selekciju modela [2].  
  
Iako bi AIC trebalo da rezultuje selekciji štedljivog modela, ovo neće uvek biti slučaj. U stvari, u slučaju da mu je pružena ta opcija, AIC će preferirati model sa n parametara u odnosu na bilo koji drugi model. Ovo može predstavljati problem kada  je broj parametara u modelu koji se razmatra veći od (grubo) 30% veličine uzorka. Kako bi uklonili ovaj nedostatak Hurvich i Tsai (1989) su uveli  korigovanu verziju, AICc [3].

##	Podaci 
Podaci koji su korišćeni u okviru ovog projekta su dobijeni su sa sajta [PolovniAutomobili.com](https://www.polovniautomobili.com/motori), odnosno iz dela sajta koji je namenjen prodaji motora, na dan 12/04/2017.
Preuzeti su svi aktivni oglasi koji se odnose na motore i sa svakog oglasa su preuzeti podaci o sledećim karakteristikama: 
- Cena (koja će u ovom projektu predstavljati zavisnu promenljivu)
- Stanje motora
- Marka motora
- Tip
- Model
- Godište
- Kilometraža
- Kubikaža
- Snaga morora u kW
- Snaga morora u KS
- Broj cilindara
- Vrsta menjača
- Boja
- Datum registracije
- Poreklo
- Informacije o oštećenju

Pošto na samom sajtu ne postoji opcija za direktno preuzimanje ovih podataka, podaci su izvučeni iz HTML-a stranice svakog oglasa posebno. Podaci su sačuvani u CSV formatu u fajlu [PolovniAutomobili data.csv](https://github.com/DooMy991/PolovniAutomobili/blob/master/PolovniAutomobili%20data.csv).

Primer podataka iz ovog dataseta dat je u Listingu 1.

```
Cena,Stanje,Tip,Godiste,Kilometraza,Kubikaza,kW,KS,BrojCilindara,Menjac,Boja,Registracija,Poreklo,Ostecenje
999,Polovan motor,Scooter,2002,16000,198,14,19,1, Automatski, Plava, Nije registrovan, Na ime kupca, Nije ostecen
650,Polovan motor,Sport / Super sport,1994,74000,600,74,100,4, Manuelni,Zuta, Nije registrovan, Strane tablice, Nije ostecen

```
*Listing 1 - Primer podataka dataseta*

##	Realizacija projekta i tumačenje rezultata
Za analizu podataka i kreiranje modela linearne regresije, korišćen je programski jezik R. Biblioteke koje su korišćene su [polycor](https://cran.r-project.org/web/packages/polycor/index.html), koja se koristi za računanje korelacije između nezavisnih promenljivih koje nisu istog tipa (int i Factor tipa) i biblioteka [MuMIn](https://cran.r-project.org/web/packages/MuMIn/index.html) koja se koristi za određivanje vrednosti AICc kriterijuma za modele.
Zatim ćemo učitati dataset iz "PolovniAutomobili data.csv" fajla i nazvati ga **data**. 

```R
library(polycor)

library(MuMIn)

data = read.csv("PolovniAutomobili data.csv")
```
*Listing 2 - Učitavanje biblioteka*

Pre kreiranja modela linearne regresije, na osnovu tabele korelacija za sve varijable iz dataseta biće uklonjene one varijable koje imaju korelacioni koeficijent veći od 0.8. Tabela korelacije je prikazana u Listingu 3.

```R
                  Cena     Stanje        Tip    Godiste Kilometraza   Kubikaza         kW         KS BrojCilindara     Menjac       Boja Registracija    Poreklo
Cena                 1 Polyserial Polyserial    Pearson     Pearson    Pearson    Pearson    Pearson       Pearson Polyserial Polyserial   Polyserial Polyserial
Stanje         -0.3787          1 Polychoric Polyserial  Polyserial Polyserial Polyserial Polyserial    Polyserial Polychoric Polychoric   Polychoric Polychoric
Tip             0.1369   0.001672          1 Polyserial  Polyserial Polyserial Polyserial Polyserial    Polyserial Polychoric Polychoric   Polychoric Polychoric
Godiste         0.4052    -0.7142    0.06987          1     Pearson    Pearson    Pearson    Pearson       Pearson Polyserial Polyserial   Polyserial Polyserial
Kilometraza   -0.08274     0.4886     0.1309    -0.3364           1    Pearson    Pearson    Pearson       Pearson Polyserial Polyserial   Polyserial Polyserial
Kubikaza        0.6242   -0.08509     0.2178      0.073       0.203          1    Pearson    Pearson       Pearson Polyserial Polyserial   Polyserial Polyserial
kW              0.5347   -0.06243     0.3675    0.07408      0.1771     0.8489          1    Pearson       Pearson Polyserial Polyserial   Polyserial Polyserial
KS              0.5343   -0.06164     0.3672    0.07333      0.1776     0.8487          1          1       Pearson Polyserial Polyserial   Polyserial Polyserial
BrojCilindara   0.3021    0.04373     0.3647   -0.04875       0.222     0.6921     0.7814     0.7814             1 Polyserial Polyserial   Polyserial Polyserial
Menjac            0.22     0.1719    -0.0699    -0.4058       0.386     0.6673     0.7486     0.7495        0.6464          1 Polychoric   Polychoric Polychoric
Boja          -0.08572     -0.262    0.01253    0.03579    -0.04686   -0.08157   -0.05377   -0.05415      -0.07683   -0.07184          1   Polychoric Polychoric
Registracija   -0.1433     0.6337   -0.02691    -0.2091      0.1137    -0.1477    -0.1471    -0.1468      -0.08755    -0.1885    -0.1051            1 Polychoric
Poreklo        -0.1362     0.7379     0.0703     -0.407      0.1961   0.001853   0.005401    0.00586       0.05693    0.06214      -0.19        0.828          1
Ostecenje      -0.3634     0.4405    0.04098     -0.245     0.04219    -0.1584   -0.07265   -0.07234       -0.0537   -0.06062   -0.07853      -0.1547   -0.03149
```
*Listing 3 - Tabela korelacija svih varijabli iz dataseta*

Iz ove tabele se može zaključiti da su nezavisne promenljive "KS", "kW", "Kubikaza" međusobno visoko korelisane, kao i "Registracija" i "Poreklo". Na osnovu postojećeg dataseta će biti kreiran novi dataset i iz njega izbaciti nezavisne promenljive "kW", "Kubikaza" i "Poreklo" (u narednim listinzima nazvan **dataBK**) koji nema visoko korelisane varijable.

Prilikom kreiranja modela linearne regersije, korišćen je *algoritam eliminacije unazad* (eng. "backward elimination algorithm") [4]. Algoritam predviđa da se prvo kreira *potpuni model* linearne regresije koji kao kao nezavisne promenljive ima sve varijable iz dataseta (izuzev zavisne varijable, u ovom modelu *Cena*). U svakoj iteraciji se računa vrednost AICc koeficijenta i nakon toga se izbacuje nezavisna promenljiva koja ima najveću P vrednost. Postupak se ponavlja sve dok se ne dođe do modela sa jednom nezavisnom promenljivom. Na kraju, bira se onaj model koji ima najmanju vrednost AICc koeficijenta u odnosu na ostale modele.

U Listingu 4 prikazane su naredbe za kreiranje punog modela, za izračunavanje AICc koeficijenta, kao i funkcija koja pronalazi nezavisnu promenljivu sa najvećom P vrednošću.

```R
model = lm(Cena ~ Stanje + Tip + Godiste + Kilometraza + KS + BrojCilindara + Menjac + Boja + Registracija + Ostecenje, data = dataBK)

get2TopPValues <- function (model) {
  if (class(model) == "lm")
    pvalues <- summary(model)$coefficients[,4]
  toptwo <- tail(order(pvalues), 2)
  first <- pvalues[toptwo[2]]
  second <- pvalues[toptwo[1]]
  returnVal <- c(first, second)
  return(returnVal)
}

AICc(model)
[1] 72951.87

get2TopPValues(model)
   BojaBez Boja Braon 
 0.8693501  0.8631903 

```
*Listing 4 - Kreiranje punog modela, izračunavanje AICc koeficijenta i određivanje nezavisne promenljive koju treba izbaciti iz modela*

Nakon primene *algoritma eliminacije unazad* zaključuje se da je prvi, potpuni model, optimalan, jer ima najmanju vrednost AICc koeficijenta od svih modela. U Listingu 5 je prikazan opis ovog modela.

```R
summary(model)

Call:
lm(formula = Cena ~ Stanje + Tip + Godiste + Kilometraza + KS + 
    BrojCilindara + Menjac + Boja + Registracija + Ostecenje, 
    data = dataBK)

Residuals:
    Min      1Q  Median      3Q     Max 
-9203.7 -1100.3  -148.6   751.7 23657.6 

Coefficients:
                                           Estimate Std. Error t value Pr(>|t|)    
(Intercept)                              -2.282e+05  1.237e+04 -18.443  < 2e-16 ***
StanjePolovan motor                      -3.368e+03  2.049e+02 -16.439  < 2e-16 ***
TipCombination / Sidecar                 -2.884e+03  9.886e+02  -2.917  0.00356 ** 
TipEnduro / Cross                        -1.757e+03  1.804e+02  -9.743  < 2e-16 ***
TipMini bike (pocket bike)               -3.847e+03  8.835e+02  -4.354 1.37e-05 ***
TipMoped                                 -1.415e+03  2.558e+02  -5.532 3.38e-08 ***
TipNaked                                 -1.823e+03  1.939e+02  -9.402  < 2e-16 ***
TipOldtimer                               1.297e+03  4.455e+02   2.911  0.00362 ** 
TipScooter                               -3.225e+03  2.562e+02 -12.589  < 2e-16 ***
TipSport / Super sport                   -2.620e+03  1.748e+02 -14.986  < 2e-16 ***
TipSupermoto                             -2.340e+03  3.401e+02  -6.881 6.90e-12 ***
TipTouring                                1.570e+02  2.204e+02   0.713  0.47616    
Godiste                                   1.181e+02  6.127e+00  19.273  < 2e-16 ***
Kilometraza                              -6.583e-03  1.144e-03  -5.755 9.35e-09 ***
KS                                        5.247e+01  1.420e+00  36.942  < 2e-16 ***
BrojCilindara                            -5.785e+02  5.259e+01 -11.001  < 2e-16 ***
Menjac Manuelni                          -1.443e+03  2.159e+02  -6.684 2.65e-11 ***
Boja Bordo                               -4.974e+02  3.382e+02  -1.471  0.14145    
Boja Braon                               -1.128e+02  6.548e+02  -0.172  0.86319    
Boja Crna                                -7.940e+02  1.548e+02  -5.129 3.05e-07 ***
Boja Crvena                              -8.366e+01  1.642e+02  -0.510  0.61033    
Boja Kameleon                            -1.455e+03  7.683e+02  -1.893  0.05839 .  
Boja Krem                                -1.541e+03  1.694e+03  -0.910  0.36302    
Boja LjubiÄasta                         -7.842e+02  5.408e+02  -1.450  0.14713    
Boja NarandÅ¾asta                        -1.164e+03  2.832e+02  -4.110 4.04e-05 ***
Boja Plava                               -1.030e+03  1.680e+02  -6.132 9.52e-10 ***
Boja Siva                                -9.612e+02  1.759e+02  -5.465 4.92e-08 ***
Boja SmeÄ‘a                               5.726e+03  2.500e+03   2.291  0.02202 *  
Boja Srebrna                             -4.830e+02  3.242e+02  -1.490  0.13634    
Boja Teget                               -1.057e+03  3.472e+02  -3.045  0.00234 ** 
Boja Tirkiz                              -8.573e+02  9.136e+02  -0.938  0.34815    
Boja Zelena                              -9.649e+02  2.293e+02  -4.207 2.65e-05 ***
Boja Zlatna                              -1.093e+03  5.659e+02  -1.931  0.05355 .  
BojaBez                                  -1.040e+02  6.322e+02  -0.164  0.86935    
BojaSarena                               -2.069e+03  2.067e+02 -10.010  < 2e-16 ***
BojaZuta                                 -1.055e+03  2.521e+02  -4.185 2.92e-05 ***
Registracija Registrovan                  1.028e+02  8.525e+01   1.206  0.22806    
Registracija Trajno registrovan           1.163e+02  1.568e+02   0.742  0.45837    
Ostecenje ostecen - nije u voznom stanju -1.130e+03  4.570e+02  -2.474  0.01341 *  
Ostecenje ostecen - u voznom stanju      -6.424e+02  4.556e+02  -1.410  0.15863    
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 2386 on 3924 degrees of freedom
  (7 observations deleted due to missingness)
Multiple R-squared:  0.5756,    Adjusted R-squared:  0.5714 
F-statistic: 136.5 on 39 and 3924 DF,  p-value: < 2.2e-16

```
*Listing 5 - Statistički parametri odabranog regresionog modela*

Iz date summary tabele analiziraćemo sledeće parametre: Residuals, Residual standard error, Multiple R-squared i Ajusted R-squared i F-statistic.

**Residuals** - Reziduali predstavljaju razliku između pravih vrednosti i vrednosti koje je dati model predvideo. Ovi parametri nam pokazuju minimalnu i maksimalnu vrednost, medijanu i prvi i treći kvartil reziduala. Reziduali se kreću od -9269.6 do 23689.3, a medijana im je -159.8. S obzirom da je medijana relativno blizu nuli, to može biti pokazatelj da je model dobar.

**Residual standard error** - Ovo je veoma bitan parametar jer pokazuje koliko će u proseku vrednost promenljive "Cena" odstupati od linije koja predstavlja regresioni model.

**Multiple R-squared i Ajusted R-squared** - Ova dva parametra se prvenstveno koriste za upoređivanje modela. S obzirom da je korišćen AICc koeficijent za upoređivanje modela, ova dva parametra se zanemaruju.

**F-statistic** - Ovaj parametar predstavlja Fišerovu statistiku. On zavisi od broja opservacija i broja nezavisnih promenljivih (stepeni slobode se određuju kada se od broja opservacija oduzme broj nezavisnih promenljivih) i poželjno je da ima što veću vrednost. U našem slučaju ovaj parametar ima vrednost od 136.5 uz 3924 stepeni slobode što može značiti da je model dobar s obzirom da ima vrednost koja je dosta veća od 1.

**Analiza modela** - Najznačajniji prediktori cene su:
- Stanje
- Godiste
- Kilometraza 
- KS
- BrojCilindara
- Menjac
Tu bi se takođe mogla ubrojati i faktorska promenljiva Tip s obzirom da su svi tipovi motora, osim Touring tipa, značajni. Ostali prediktori ne utiču značajnije na cenu.

Kratko ćemo analizirati svaki od značajnih prediktora:
**Stanje** - Na osnovu procenjene vrednosti koja iznosi -3.368e+03 možemo zaključiti da ovaj prediktor, u slučaju da ima vrednost "Polovan motor", utiče negativno na cenu (što zaključujemo iz negativnog predznaka). U slučaju da dva motora imaju potpuno iste vrednosti ostalih nezavisnih promenljivih i da se razlikuju samo u Stanju (jedan motor je nov, drugi polovan), polovan motor bi bio jeftiniji za nešto više od 3360 evra.

**Godiste** - Na osnovu procenjene vrednosti koja iznosi 1.181e+02 možemo zaključiti da ovaj prediktor pozitivno utiče na cenu sa porastom godišta proizvodnje. U slučaju da dva motora imaju potpuno iste vrednosti ostalih nezavisnih promenljivih i da se razlikuju samo u godistu proizvodnje (jedan motor je godinu dana mlađi od drugog), mlađi motor bi bio skuplji za nešto više od 118 evra.

**Kilometraza** - Na osnovu procenjene vrednosti koja iznosi -6.583e-03 možemo zaključiti da ovaj prediktor negativno utiče na cenu (što zaključujemo iz negativnog predznaka). U slučaju da dva motora imaju potpuno iste vrednosti ostalih nezavisnih promenljivih i da se razlikuju samo po pređenoj kilometraži, u slučaju da je jedan motor prešao 10000 kilometara više od drugog, prvi će biti jeftiniji za oko 66 evra.

**KS** - Na osnovu procenjene vrednosti koja iznosi 5.247e+01 možemo zaključiti da ovaj prediktor pozitivno utiče na cenu. U slučaju da dva motora imaju potpuno iste vrednosti ostalih nezavisnih promenljivih i da se razlikuju samo po broju konjskih snaga (recimo da jedan motor ima 100 konjskih snaga više od drugog), motor koji ima 100 konjskih snaga više će biti skuplji za oko 525 evra.

**BrojCilindara** - Na osnovu procenjene vrednosti koja iznosi -5.785e+02 možemo zaključiti da ovaj prediktor negativno utiče na cenu. U slučaju da dva motora imaju potpuno iste vrednosti ostalih nezavisnih promenljivih i da se razlikuju samo po broju cilindara, motor koji ima jedan cilindar više će u proseku biti jeftiniji za oko 580 evra.

**Menjac** - Na osnovu procenjene vrednosti koja iznosi -1.443e+03 možemo zaključiti da ovaj prediktor, u slučaju da ima vrednost "Manuelni", utiče negativno na cenu (što zaključujemo iz negativnog predznaka). U slučaju da dva motora imaju potpuno iste vrednosti ostalih nezavisnih promenljivih i da se razlikuju samo u tipu menjača (jedan motor je automatski, drugi manuelni menjač), motor sa manuelnim menjačem će biti jeftiniji u proseku za 1500 evra.

##	Predlozi poboljšanja modela
Jedna od ideja za poboljšanje ovog modela je dodavanje jos jedne nezavisne promenljive koja bi se odnosila na iskustva korisnika sa konkretnim motorom. S obzirom da se uglavnom radi o polovnim motorima, bilo bi poželjno imati parametar koji bi predstavljao prosečnu ocenu koju je konkretan motor dobio od korisnika koji su bili u kontaktu sa njim. To bi se moglo koristiti kao osnova za određivanje cene koja bi se dalje korigovala na osnovu vrednosti ostalih nezavisnih promenljivih.


##	Literatura
[1] Statistics Solutions. (2013). What is Linear Regression [WWW Document]. Retrieved from [here](http://www.statisticssolutions.com/what-is-linear-regression/).
[2]  [Mike Moffatt](https://www.thoughtco.com/mike-moffatt-1145885), [An Introduction to Akaike's Information Criterion (AIC)](https://www.thoughtco.com/introduction-to-akaikes-information-criterion-1145956)
[3] NYU Stern, [The Corrected AIC (AICc)](http://people.stern.nyu.edu/churvich/Forecasting/Handouts/AICC.pdf)
[4] Kutner, M., Nachtsheim, C., & Neter, J. (2004). Applied linear regression models (4th ed.). New York: McGraw-Hill/Irwin


-	EDX kurs, Analytics Edge, link: https://courses.edx.org/courses/course-v1:MITx+15.071x_3+1T2016/courseware/f8d71d64418146f18a066d7f0379678c/6248c2ecbbcb40cfa613193e8f1873c1/
-	Youtube R tutorial korisnika MarinStatsLectures, link: https://www.youtube.com/playlist?list=PLqzoL9-eJTNBJrvFcN-ohc5G13E7Big0e 
-	Statistics with R (3) - Generalized, linear, and generalized least squares models (LM, GLM, GLS) , link: https://www.youtube.com/watch?v=P-WYkSZp9lY&t=1510s 
