/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsoup;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Dusan
 */
public class Main {

    public static void main(String[] args) throws IOException {
////        Document doc = Jsoup.connect("http://facebook.com").get();
////        String title = doc.title();
////        System.out.println("Title: "+title);
////        
////        Elements links = doc.select("a[href]");
////        
////        for(Element e : links) {
////            System.out.println("\nLink: "+e.attr("href"));
////            System.out.println("Text: "+e.text());
////        }
////        
////        System.out.println("Getting all the images...");
////        
////        Elements images = doc.getElementsByTag("img");
////        
////        for(Element img : images) {
////            System.out.println("\nsrc: "+img.attr("abs:src"));
////        }
//          
//          File file = new File("raw.html");
//          
//          Document doc = Jsoup.parse(file,"utf-8");
//          
//          Element tagMetaCharset = new Element(Tag.valueOf("meta"), "");
//          tagMetaCharset.attr("charset", "utf-8");
//          doc.head().appendChild(tagMetaCharset);
//          
//          Element tagPDescription = new Element(Tag.valueOf("p"), "");
//          tagPDescription.text("It is a very powerful HTML parser! I love it so much... because it is very easy");
//          doc.body().appendChild(tagPDescription);
//          
//          tagPDescription.before("<p>Author: M Abubakar Afzal</p>");
//          
//          Element tagPAuthor = doc.body().select("p:contains(Author)").first();
//          tagPAuthor.attr("align", "center");
//          
//          doc.body().addClass("content");
//          
//          System.out.println("Transfering....");
//          
//          FileWriter fw = new FileWriter("updated.html");
//          fw.write(doc.toString());
//          fw.close();

        int brojac = 0;
        ArrayList<Bike> bikes = new ArrayList<>();
        String fajl = "";
        for (int i = 1; i < 199; i++) {
            Document doc = Jsoup.connect("https://www.polovniautomobili.com/motori/pretraga?page=" + i + "&sort=renewDate_desc&city_distance=0&showOldNew=all&without_price=1").timeout(6000).get();
            Elements eleFeatured = doc.select("div#search_featured_classified");
            for (Element e : eleFeatured.select("a.featured_title")) {

                Document doc0 = Jsoup.connect("https://www.polovniautomobili.com" + e.attr("href")).timeout(6000).get();
                Elements error = doc0.select("div.error-holder");
                if (error.isEmpty()) {
                    Bike b = new Bike();
                    Elements cena = doc0.select("div.price");
                    for (Element ele : cena.select("span")) {
                        b.setPrice(ele.text());
                    }
                    Elements info = doc0.select("div.basic-info");
                    int iterator = 0;
                    for (Element ele : info.select("ul")) {
                        Elements item = ele.select("li");
                        for (Element li : item) {
                            switch (iterator) {
                                case 0:
                                    b.setPolovan(li.text());
                                    iterator++;
                                    break;
                                case 1:
                                    b.setMarka(li.text());
                                    iterator++;
                                    break;
                                case 2:
                                    b.setTip(li.text());
                                    iterator++;
                                    break;
                                case 3:
                                    b.setModel(li.text());
                                    iterator++;
                                    break;
                                case 4:
                                    b.setGodiste(li.text());
                                    iterator++;
                                    break;
                                case 5:
                                    if (b.getPolovan().equalsIgnoreCase("Polovan motor")) {
                                        b.setKilometraza(li.text());
                                    } else {
                                        b.setKilometraza("0 km");
                                    }
                                    iterator++;
                                    break;
                                default:
                                    break;

                            }
                        }
                    }
                    Elements detalji = doc0.select("div.detailed-info");
                    for (Element ele : detalji.select("ul")) {
                        Elements item = ele.select("li");
                        for (Element li : item) {
                            if (li.text().startsWith("Kubik")) {
                                b.setKubikaza(li.text());
                            }
                            if (li.text().startsWith("Snaga")) {
                                b.setSnaga(li.text());
                            }
                            if (li.text().startsWith("Broj")) {
                                b.setBrojCilindara(li.text());
                            }
                            if (li.text().startsWith("Menja")) {
                                b.setMenjac(li.text());
                            }
                            if (li.text().startsWith("Boja")) {
                                b.setBoja(li.text());
                            }
                            if (li.text().startsWith("Registrovan")) {
                                b.setRegistrovanDo(li.text());
                            }
                            if (li.text().startsWith("Poreklo")) {
                                b.setPoreklo(li.text());
                            }
                            if (li.text().startsWith("Tip")) {
                                b.setHladjenje(li.text());
                            }
                            if (li.text().startsWith("Ošteće")) {
                                b.setOstecenje(li.text());
                            }
                        }
                    }
                    if (!b.getTip().equalsIgnoreCase("Motorne sanke") && !b.getTip().equalsIgnoreCase("ATV / Quad") && !b.getTip().equalsIgnoreCase("Ostalo")) {
                        bikes.add(b);

                        brojac++;
                    }
                }

            }
            Elements eleList = doc.select("ul#searchlist-items");
            for (Element el : eleList.select("a[href]")) {
                if (el.attr("href").startsWith("/motori/") && !el.attr("title").isEmpty()) {
                    try {
                        Document doc1 = Jsoup.connect("https://www.polovniautomobili.com" + el.attr("href")).timeout(6000).get();
                        Elements error = doc1.select("div.error-holder");
                        if (error.isEmpty()) {
                            Bike b = new Bike();
                            Elements cena = doc1.select("div.price");
                            for (Element e : cena.select("span")) {
                                b.setPrice(e.text());
                            }
                            Elements info = doc1.select("div.basic-info");
                            int iterator = 0;
                            for (Element e : info.select("ul")) {
                                Elements item = e.select("li");
                                for (Element li : item) {
                                    switch (iterator) {
                                        case 0:
                                            b.setPolovan(li.text());
                                            iterator++;
                                            break;
                                        case 1:
                                            b.setMarka(li.text());
                                            iterator++;
                                            break;
                                        case 2:
                                            b.setTip(li.text());
                                            iterator++;
                                            break;
                                        case 3:
                                            b.setModel(li.text());
                                            iterator++;
                                            break;
                                        case 4:
                                            b.setGodiste(li.text());
                                            iterator++;
                                            break;
                                        case 5:
                                            if (b.getPolovan().equalsIgnoreCase("Polovan motor")) {
                                                b.setKilometraza(li.text());
                                            } else {
                                                b.setKilometraza("0 km");
                                            }
                                            iterator++;
                                            break;
                                        default:
                                            break;

                                    }
                                }
                            }
                            Elements detalji = doc1.select("div.detailed-info");
                            int it = 0;
                            for (Element e : detalji.select("ul")) {
                                Elements item = e.select("li");
                                for (Element li : item) {
                                    if (li.text().startsWith("Kubik")) {
                                        b.setKubikaza(li.text());
                                    }
                                    if (li.text().startsWith("Snaga")) {
                                        b.setSnaga(li.text());
                                    }
                                    if (li.text().startsWith("Broj")) {
                                        b.setBrojCilindara(li.text());
                                    }
                                    if (li.text().startsWith("Menja")) {
                                        b.setMenjac(li.text());
                                    }
                                    if (li.text().startsWith("Boja")) {
                                        b.setBoja(li.text());
                                    }
                                    if (li.text().startsWith("Registrovan")) {
                                        b.setRegistrovanDo(li.text());
                                    }
                                    if (li.text().startsWith("Poreklo")) {
                                        b.setPoreklo(li.text());
                                    }
                                    if (li.text().startsWith("Tip")) {
                                        b.setHladjenje(li.text());
                                    }
                                    if (li.text().startsWith("Ošteće")) {
                                        b.setOstecenje(li.text());
                                    }
                                }
                            }
                            if (!b.getTip().equalsIgnoreCase("Motorne sanke") && !b.getTip().equalsIgnoreCase("ATV / Quad") && !b.getTip().equalsIgnoreCase("Ostalo")) {
                                bikes.add(b);

                                brojac++;
                            }
                        }

                    } catch (Exception e) {

                    }
                }
            }
        }
        System.out.println(brojac);
        System.out.println(bikes.size());
        for (Bike b : bikes) {
            fajl = fajl + b.toString() + "\n";
        }
        FileWriter writer = new FileWriter("PolovniAutomobili.csv");
        writer.write(fajl);
        writer.close();
        System.out.println("GOTOVO!");
        System.out.println(fajl);
    }
}
