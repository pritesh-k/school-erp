package com.schoolerp.utils;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.List;

@Component
public class CsvUtil {
    public <T> List<T> parseCsv(MultipartFile file, Class<T> clazz) {
        try {
            return new CsvToBeanBuilder<T>(new InputStreamReader(file.getInputStream()))
                    .withType(clazz)
                    .build()
                    .parse();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid CSV", e);
        }
    }
}