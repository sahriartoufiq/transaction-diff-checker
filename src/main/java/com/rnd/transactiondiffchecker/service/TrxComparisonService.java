package com.rnd.transactiondiffchecker.service;

import com.rnd.transactiondiffchecker.dto.response.TrxComparisonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrxComparisonService {

    private final FileIOService fileIOService;

    public TrxComparisonResponse getTrxComparisonResponse(MultipartFile clientReport,
                                                          MultipartFile orgReport) throws IOException {

        var clientReportDetailList = fileIOService.getTransactionDetailList(clientReport);

        clientReportDetailList.forEach(System.out::println);
        return null;
    }
}
