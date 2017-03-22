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
public class NewClass {

 //   public static void main(String[] args) throws IOException {
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
        for (int i = 1; i < 8; i++) {
            Document doc = Jsoup.connect("http://www.bikez.com/brand/aprilia_motorcycles.php?page=" + i).timeout(6000).get();
            Elements ele = doc.select("a[href]");
            for (Element e : ele) {
                Bike bike = new Bike();
                if (e.text().startsWith("Aprilia") && !e.text().equalsIgnoreCase("Aprilia") && !e.text().equalsIgnoreCase("Aprilia motorcycle models")) {
                    Document doc1 = Jsoup.connect("http://www.bikez.com" + e.attr("href").substring(2)).timeout(6000).get();
                    Elements table = doc1.select("table.Grid");
                    for (Element row : table.select("tr")) {
                        Elements tds = row.select("td");
                        if (tds.size() > 1) {
                            //System.out.println("Name: "+tds.get(0).text()+"; Value: "+tds.get(1).text());
                            String naziv = tds.get(0).text();
                            switch (naziv) {
                                case "Model:":
                                    bike.setModel(tds.get(1).text());
                                    break;
                                case "Year:":
                                    bike.setYear(tds.get(1).text());
                                    break;
                                case "Category:":
                                    bike.setCategory(tds.get(1).text());
                                    break;
                                case "Rating:":
                                    bike.setRating(tds.get(1).text());
                                    break;
                                case "Displacement:":
                                    bike.setDisplacement(tds.get(1).text());
                                    break;
                                case "Engine type:":
                                    bike.setEngineType(tds.get(1).text());
                                    break;
                                case "Power:":
                                    bike.setPower(tds.get(1).text());
                                    break;
                                case "Top speed:":
                                    bike.setTopSpead(tds.get(1).text());
                                    break;
                                case "Cooling system:":
                                    bike.setCoolingSystem(tds.get(1).text());
                                    break;
                                case "Gearbox:":
                                    bike.setGearBox(tds.get(1).text());
                                    break;
                                case "Front brakes:":
                                    bike.setFrontBrakes(tds.get(1).text());
                                    break;
                                case "Dry weight:":
                                    bike.setDryWeight(tds.get(1).text());
                                    break;
                                case "Fuel capacity:":
                                    bike.setFuelCapacity(tds.get(1).text());
                                    break;
                                case "Default":
                                    break;

                            }
                        }
                    }

                    bikes.add(bike);

                    brojac++;
                }
            }
        }
        for (Bike b : bikes) {
            fajl = fajl + b.toString() + "\n";
        }
        FileWriter writer = new FileWriter("BikeData.txt");
        writer.write(fajl);
        writer.close();
        System.out.println("DONE!");
        System.out.println(fajl);
        System.out.println("brojac = " + brojac);
    }
}
