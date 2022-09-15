package mediSsok.mediSsokspring.service;

import mediSsok.mediSsokspring.dto.medicines.MedicinesInfoResponseDto;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Crawling {

    /**
     * 조회할 URL셋팅 및 Document 객체 로드하기
     */
    private String url = "https://nedrug.mfds.go.kr/pbp/CCBBB01/getItemDetail?itemSeq=";

    public MedicinesInfoResponseDto process(Long id) {
        url += id;
        Connection conn = Jsoup.connect(url);

        //Jsoup 커넥션 생성
        Document document = null;
        try {
            document = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        MedicinesInfoResponseDto list = getDataList(document);
        return list;
    }


    /**
     * data가져오기
     */
    private MedicinesInfoResponseDto getDataList(Document document) {
        MedicinesInfoResponseDto dto = new MedicinesInfoResponseDto();

        // 성상
        Elements appearances = document.select(".dr_table_type1 tbody tr td");
        String appearance = appearances.get(1).text();
        System.out.println("성상 : " + appearance);
        dto.setAppearance(appearance);

        // 성분정보
        List<String> ingredient = new ArrayList<>();
        Elements ingredientElements = document.select("#scroll_02 div p");

        for (Element element : ingredientElements){
            String ingredientString;

            String text = element.text();
            int nameInt = text.indexOf("성분명");
            int amountInt = text.indexOf("분량");
            int unitInt = text.indexOf("단위");

            String name = text.substring(nameInt + 6 , amountInt - 2);
            String amount = text.substring(amountInt + 5 , unitInt - 3);

            ingredientString = name + amount + "mg";
            ingredient.add(ingredientString);
        }
        System.out.println("성분정보 : " + ingredient);
        dto.setIngredient(ingredient);

        // 저장방법
        Elements saves = document.select("#scroll_07 table tbody tr td");
        String save = saves.get(0).text();
        System.out.println("저장방법 : " + save);
        dto.setSave(save);

        // 효능효과
        Elements effect = document.select("#_ee_doc");
        System.out.println(effect.html());
        dto.setEffect(effect.html());

        // 용법용량
        Elements intake = document.select("#_ud_doc");
        System.out.println(intake.html());
        dto.setIntake(intake.html());
        return dto;
    }

}