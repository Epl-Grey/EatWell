package com.example.myapplication;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class YandexImagesParser {
    public static String parseFirstImage(String query) throws IOException {
        String url = "https://yandex.com/images/search?text=" + query;

        Document document = Jsoup.connect(url).get();

        Elements images = document.select("img.serp-item__thumb");
        if (images.size() > 0) {
            Element firstImage = images.get(0);
            String imageUrl = firstImage.attr("src");

            // Если ссылка на картинку не полная (относительная), добавьте полный путь
            if (!imageUrl.startsWith("http")) {
                imageUrl = "https:" + imageUrl;
            }

            return imageUrl;
        }

        return null;
    }
}
