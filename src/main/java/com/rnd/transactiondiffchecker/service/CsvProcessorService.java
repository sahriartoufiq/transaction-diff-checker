package com.rnd.transactiondiffchecker.service;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@Service
public class CsvProcessorService {

    public <T> List<T> getRecordList(MultipartFile file, Class<T> recordType) throws IOException {
        var buffer = new BufferedReader(new InputStreamReader(file.getInputStream()));

        return new CsvToBeanBuilder<T>(buffer)
                .withType(recordType)
                .build()
                .parse();
    }
}
