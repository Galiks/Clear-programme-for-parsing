package com.turchenkov.parsing.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.turchenkov.parsing.domains.shop.Shop;
import com.turchenkov.parsing.parsingmethods.ParserInterface;
import com.turchenkov.parsing.repository.ShopRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    private List<ParserInterface> parsers;

    @Override
    public void parsingAndSaveInDB() {
        for (ParserInterface parser : parsers) {
            for (Shop shop : parser.parsing()) {
              if (shop != null){
                  shopRepository.save(shop);
              }
            }
        }
    }

    @Override
    public void parsingAndSaveInExcel() {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("Shops");
        int i = 0;
        for (ParserInterface parser : parsers) {
            for (Shop shop : parser.parsing()) {
                if (shop != null){
                    Row row = sheet.createRow(i);
                    Cell name = row.createCell(0);
                    Cell discount = row.createCell(1);
                    Cell label = row.createCell(2);
                    Cell image = row.createCell(3);
                    Cell url = row.createCell(4);
                    name.setCellValue(shop.getName());
                    discount.setCellValue(shop.getDiscount());
                    label.setCellValue(shop.getLabel());
                    image.setCellValue(shop.getImage());
                    url.setCellValue(shop.getPageOnTheSite());
                    i++;
                }
            }
        }
        try {
            ((HSSFWorkbook) book).write(new FileOutputStream("shops.xls"));
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void deleteAllFromDB() {
        shopRepository.deleteAll();
    }

    @Override
    public List<Shop> getListOfShop() {
        return (List<Shop>) shopRepository.findAllByOrderByName();
    }

    @Override
    public List<Shop> update() {
        deleteAllFromDB();
//        parsingAndSaveInDB();
        parsingAndSaveInExcel();
        return getListOfShop();
    }

    @Override
    public List<Shop> orderByDiscount() {
        return (List<Shop>) shopRepository.findAllByOrderByDiscount();
    }

    @Override
    public List<Shop> orderByDiscountDesc() {
        return (List<Shop>) shopRepository.findAllByOrderByDiscountDesc();
    }


}
