# Predviđanje cene polovnih automobila

##	O projektu
Ovaj projekat se bavi predviđanjem cene motora na osnovu određenih njihovih karakteristika. Podaci korišćeni u okviru ovog projekta su povučeni sa sajta za prodaju polovnih motornih vozila 
[PolovniAutomobili.com](https://www.polovniautomobili.com/motori) na dan 12/04/2017. Tom prilikom povučeni su podaci o svim postojećim aktivnim oglasima koji se odnose na motore.
Za prikupljanje, izvlačenje i manipulisanje podacima sa ovog sajta korišćcena je [JSOUP](https://jsoup.org/) Java biblioteka koja predstavlja Java HTML Parser. 
Glavni deo ovog projekta urađen je u programskom jeziku R kojim je, metodom linearne regresije, napravljen model koji pokazuje koliko koja (relevantna) karakteristika motora utiče na cenu motora.


##	Linerna regresija
Linearna regresija predstavlja osnovni tip regresije. Generalna ideja regresije je da ispita dve stvari: 1. da li skup nezavisnih promenljivih obavljaju dobar posao u predviđanju
zavisne promenljive? Da li model koji koristi nezavisne promenljive računa na varijabilnost u promenama zavisne promenljive? 2. Koje konkretne nezaavisne promenljive su značajni prediktori 
zavisne promenljive i na koji način one,određene magnitudom i znakom beta koeficijenata, utiču na zavisnu promenljivu. Ovi regresioni koeficijenti se koriste da objasne 
odnos između jedne zavisne varijable i jedne ili više nezavisnih varijabli. Šta je regresiona jednačina koja pokazuje kako se skup nezavisnih promenljivih može koristiti 
za predviđanje zavisne promenljive? Najprostiji oblik jednačine sa jednom zavisnom i jednom nezavisnom promenljivom je definisan formulom y = c + b*x, gde je y = zavisna promenljiva,
c = konstanta (vrednost zavisne promenljive u slučaju da je nezavisna promenljiva jednaka nuli), b = regresioni koeficijent nezavisne promenljive, x = nezavisna promenljiva.

##	Podaci 
Podaci koji su korišćeni u okviru ovog projekta su povučeni su sa sajta [PolovniAutomobili.com](https://www.polovniautomobili.com/motori), tj sa dela sajta koji je namenje prodaji motora, na dan 12/04/2017.
Na taj dan povučeni su svi aktivni oglasi koji se odnose na motore i sa svakog oglasa su povučeni podaci o sledećim karakteristikama: Cena (koja će u ovom projektu predstavljati zavisnu promenljivu),
Stanje motora, Marka motora, Tip, Model, Godiste, Kilometraza, Kubikaza, kW, KS, Broj cilindara, Menjac, Boja, Registracija, Poreklo i Ostecenje. Pošto na samom sajtu ne postoji opcija za preuzimanje ovih podataka,
u tu svrhu je korišćena [JSOUP](https://jsoup.org/) Java biblioteka koja predstavlja Java HTML Parser. Ova biblioteka pruža veoma praktičan API kojim se mogu izvlačiti podaci sa iz HTML-a i manupulisati tim podacima.
Dataset korićen u okviru ovog projekta se možete naći u [PolovniAutomobili data.csv](https://github.com/DooMy991/PolovniAutomobili/blob/master/PolovniAutomobili%20data.csv) fajlu.
Primer podataka iz ovog dataseta dat je u Listingu 1.


```
Cena	Stanje			Marka	Tip	Model						Godiste	Kilometraza	Kubikaza	kW	KS		BrojCilindara	Menjac		Boja	 Registracija		 Poreklo		 Ostecenje
999		Polovan motor	Piaggio	Scooter	beverly 200				2002	16000		198			14	19		1	 			Automatski	Plava	 Nije registrovan	 Na ime kupca	 Nije ostecen
650		Polovan motor	Yamaha	Sport / Super sport	FZR 600R	1994	74000		600			74	100		4				 Manuelni	Zuta	 Nije registrovan	 Strane tablice	 Nije ostecen

```
*Listing 1 - Primer podataka dataseta*

##	Realizacija projekta i tumačenje rezultata
Na početku ovog projekta potrebno je prvo učitati sve R biblioteke koje će se koristiti kasnije. To su biblioteka **polycor**, koju ćemo koristiti za izlistavanje korelacija između nezavisnih promenljivih koje nisu istog tipa (int i Factor tipa) i biblioteka **MuMIn** koju ćemo koristiti za određivanje vrednosti AICc kriterijuma za modele.
Zatim ćemo učitati dataset iz "PolovniAutomobili data.csv" fajla i nazvati ga **data**. 

```R
library(polycor)

library(MuMIn)

data = read.csv("PolovniAutomobili data.csv")
```
*Listing 2 - Priprema projekta*

Pre pravljenja prvog modela izlistaćemo tabelu korelacija za sve varijable iz dataseta pozivanjem hetcor(data) funkcije i ukloniti one varijable koje imaju korelacioni koeficijent veći od 0.8. Tabela korelacije je prikazana u listingu 3.

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
Iz ove tabele vidimo da su nezavisne promenljive "KS", "kW", "Kubikaza" međusobno visoko korelisane, kao i "Registracija" i "Poreklo", tako da ćemo od postojećeg dataseta napraviti novi
dataset i iz njega izbaciti nezavisne promenljive "kW", "Kubikaza" i "Poreklo" i nazvati ga **dataBK**. Ovim ćemo dobiti novi dataset koji nema visoko korelisane varijable i sa kojim možemo dalje raditi.

Na osnovu ovog dataseta napravićemo prvi, potpuni model, koji kao zavisnu promenljivu ima Cenu a kao nezavisne sve ostale varijable iz dataseta. Ideja je da na osnovu P vrednosti izbacujemo jednu po jednu nezavisnu promenljivu, posle svakog izbacivanja pravimo novi model, bez te promenljive, 
dok ne dođemo do modela sa jednom nezavisnom promenljivom, a zatim uporedimo vrednosti AICc koeficijenata svih ovih modela i na osnovu toga zaključimo koji model je optimalan (optimalan je onaj model koji ima najnižu vrednost AICc koeficijenta).

U narednom listingu prikazaću prikazaću naredbe za kreiranje prvog, punog modela, definiciju funkcije kojom ćemo pronalaziti nezavisne promenljive sa najvećom P vrednošću kao i komande kojima ćemo izračunavati AICc koeficijent 
i pronalaziti nezavisnu promenljivu sa najvećom P vrednošću.



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

Kao što se vidi u prethodnom listingu, vrednost AICc koeficijenta je 72951.87 a nezavisne promenljiva sa najvećom P vrednošću je **Boja**. AICc koeficijent ćemo zabeležiti 
jer ćemo ga na kraju upoređivati sa koeficijentima ostalih modela kako bi ustanovili koji je optimalan. Naredni korak je kreiranje novog modela koji ne sadrži nezavisnu promenljivu "Boja",
izračunavanje AICc koeficijenta za taj novi model i određivanje nezavisne promenljive sa najvećom P vrednošću u tom modelu koju ćemo izbaciti iz narednog. To je prikazano u narednom listingu.
 

```R
model1 = lm(Cena ~ Stanje + Tip + Godiste + Kilometraza + KS + BrojCilindara + Menjac + Registracija + Ostecenje, data = dataBK)

AICc(model1)
[1] 73100.15

get2TopPValues(model1)
Registracija Trajno registrovan        Registracija Registrovan 
                      0.3889004                       0.3036739 

```
*Listing 5 - Kreiranje modela1 i analiza*

Kao što se može videti, u ovom modelu nema nezavisne promenljive "Boja". Takođe se može videti da iz narednog modela traba izbaciti nezavisnu promenljivu "Registracija".
AICc koeficijent je blago porastao što nam govori da je novi model slabiji od početnog, punog modela, ali to ćemo detaljnije analizirati kada zavrsimo ceo ovaj proces i kada budemo imali koeficijente svih modela.
Ovaj identičan proces će se ponavljati do trenutka kada model sadrži samo jednu nezavnisnu promenljivu tako da nećemo ovde ispisivati detaljan kod za svaku iteraciju ovog procesa.

U narednom listingu možete videti tabelu koja sadrži AICc koeficijente za svih 10 modela, koliko ih ima od prvog, potpunog modela, do poslednjeg koji sadrži samo jednu promenljivu.
U istoj tabeli možete videti i nezavisnu promenljivu koja je izbačena iz tog modela.

```R
Naziv		AICc		Izbačena

model		72951.87	**Ovo je potpun model**
model1		73100.15	Boja
model2		73097.56	Registracija
model3		73623.56	Tip
model4		73621.55	Menjac
model5		73623.15	Ostecenje
model6		73775.58	Kilometraza
model7		73884.49	BrojCilindara
model8		74201.03	Stanje
model9		75070.41	Godiste

```
*Listing 6 - Tabela AICc koeficijenata i izbačenih nezavisnih promenljivih*

Na osnovu tabele iz prethodnog listinga možemo zaključiti da je ipak prvi, potpuni mode bio najbolji, jer on ima najmanju vrednost AICc koeficijenta od svih modela. U narednom listingu 
ćemo prikazati podatke iz **summary** tabele za potpuni model, pošto smo zaključili da je on optimalan. Na osnovu summary tabele ćemo izvršiti analizu ovog modela.

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
*Listing 7 - Statistički parametri potpunog regresionog modela*

Iz date summary tabele analiziraćemo sledeće parametre: Residuals, Residual standard error, Multiple R-squared i Ajusted R-squared i F-statistic.

**Residuals** - Reziduali su predstavljaju razliku između pravih vrednosti i vrednosti koje je dati model predvideo.Ovi parametri nam pokazuju minimalnu i maksimalnu vrednost, Medijanu, i prvi i treći kvartal reziduala. Reziduali se kreću od -9269.6 do 23689.3 a medijana im je -159.8. S obzirom da je medijana relativno blizu nuli, to može biti pokazatelj da je nam model dobar.

**Residual standard error** - Ovo je veoma bitan parametar jer pokazuje koliko će u proseku vrednost naše promenljive "Cena" odstupati odstupati od linije koja predstavlja naš regresioni model.

**Multiple R-squared i Ajusted R-squared** - Ova dva parametra se prvenstveno koriste za upoređivanje modela. S obzirom da smo koristili AICc koeficijent za upoređivanje modela, ova dva parametra ćemo zanemariti.

**F-statistic** - Ovaj parametar predstavlja Fišerovu statistiku. On zavisi od broja opservacija i broja nezavisnih promenljivih (stepeni slobode se određuju kada se od broja opservacija oduzme broj nezavisnih promenljivih) i poželjno je da ima što veću vrednost. U našem slučaju ovaj parametar ima vrednost od 136.5 uz 3924 stepeni slobode što može značiti da je naš model dobar, s obzirom da ima vrednost koja je dosta veća od 1.


##	Predlozi poboljšanja modela
Jedna od ideja za poboljšanje ovog modela je dodavanje jos jedne nezavisne promenljive koja bi se odnosila na iskustva korisnika sa konkretnim motorom. S obzirom da se uglavnom radi o polovnim motorima, bilo bi poželjno imati parametar koji bi predstavljao prosečnu ocenu koju je konkretan motor dobio od korisnika koji su bili u kontaktu sa njim. To bi moglo koristiti kao osnova za određivanje cene koja bi se dalje korigovala na osnovu vrednosti ostalih nezavisnih promenljivih.
##	Literatura	
-	EDX kurs, Analytics Edge, link: https://courses.edx.org/courses/course-v1:MITx+15.071x_3+1T2016/courseware/f8d71d64418146f18a066d7f0379678c/6248c2ecbbcb40cfa613193e8f1873c1/
-	Youtube R tutorial korisnika MarinStatsLectures, link: https://www.youtube.com/playlist?list=PLqzoL9-eJTNBJrvFcN-ohc5G13E7Big0e 
-	Statistics with R (3) - Generalized, linear, and generalized least squares models (LM, GLM, GLS) , link: https://www.youtube.com/watch?v=P-WYkSZp9lY&t=1510s 