package com.rnd.transactiondiffchecker.service;

import com.rnd.transactiondiffchecker.dto.response.ReconciliationResponse;
import com.rnd.transactiondiffchecker.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReconciliationService {

    private final FileIOService fileIOService;
    private final TrxComparisonService trxComparisonService;

    public ReconciliationResponse getTrxComparisonResponse(MultipartFile clientReport,
                                                           MultipartFile orgReport) {

        try {
            var clientReportDataList = fileIOService.getTransactionDetailList(clientReport);
            var orgReportDataList = fileIOService.getTransactionDetailList(orgReport);

            return ReconciliationResponse.builder()
                    .orgResult(trxComparisonService.getTrxComparisonDTO(orgReportDataList, clientReportDataList))
                    .clientResult(trxComparisonService.getTrxComparisonDTO(clientReportDataList, orgReportDataList))
                    .build();
        } catch (IOException ex) {
            throw new ApiException("Invalid file content!");
        }
    }
}
