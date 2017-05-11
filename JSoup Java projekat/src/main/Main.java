/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domen.Bike;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import jsoup.JsoupCrawler;
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

        JsoupCrawler jsc = new JsoupCrawler();
        String fajl = jsc.sakupiPodatkeSaSajta();
        jsc.upisiPodatkeUFajl("PolovniAutomobili.csv", fajl);
        
    }
}
