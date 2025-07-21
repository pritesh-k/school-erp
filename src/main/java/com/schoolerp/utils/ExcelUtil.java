package com.schoolerp.utils;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Component
public class ExcelUtil {

    public <T> List<T> parseExcel(MultipartFile file, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        try (InputStream is = file.getInputStream()) {
            Workbook wb = WorkbookFactory.create(is);
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> rows = sheet.rowIterator();
            rows.next(); // skip header
            while (rows.hasNext()) {
                Row row = rows.next();
                // reflection-based population omitted for brevity
                // use MapStruct or reflection utils
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid excel file", e);
        }
        return list;
    }
}