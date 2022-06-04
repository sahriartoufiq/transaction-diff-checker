package com.rnd.transactiondiffchecker.controller;

import com.rnd.transactiondiffchecker.dto.response.TrxComparisonResponse;
import com.rnd.transactiondiffchecker.service.TrxComparisonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
@RestController
@RequiredArgsConstructor
public class TrxComparisonController {

    private final TrxComparisonService trxComparisonService;

    @PostMapping("/api/transactions/compare")
    private TrxComparisonResponse compareTransaction(@RequestParam("clientReport") MultipartFile clientReport,
                                                     @RequestParam("orgReport") MultipartFile orgReport) throws IOException {

        return trxComparisonService.getTrxComparisonResponse(clientReport, orgReport);
    }
}
