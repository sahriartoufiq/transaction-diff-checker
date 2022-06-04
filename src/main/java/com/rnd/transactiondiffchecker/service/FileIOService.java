package com.rnd.transactiondiffchecker.service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.rnd.transactiondiffchecker.dto.TransactionDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@Service
public class FileIOService {

    public List<TransactionDetailDTO> getTransactionDetailList(MultipartFile file) throws IOException {
        var buffer = new BufferedReader(new InputStreamReader(file.getInputStream()));

        return new CsvToBeanBuilder<TransactionDetailDTO>(buffer)
                .withType(TransactionDetailDTO.class)
                .build()
                .parse();
    }
}
