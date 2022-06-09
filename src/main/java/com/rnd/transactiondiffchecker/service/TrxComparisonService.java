package com.rnd.transactiondiffchecker.service;

import com.rnd.transactiondiffchecker.dto.TransactionDetailDTO;
import com.rnd.transactiondiffchecker.dto.TrxComparisonDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class TrxComparisonService {

    private final TransactionHelperService helperService;

    public TrxComparisonDTO getTrxComparisonDTO(List<TransactionDetailDTO> dataList,
                                                List<TransactionDetailDTO> otherPartyDataList) {

        var trxIdDataMap = helperService.getDataMap(otherPartyDataList);
        var unmatchedTrxDetailListList = helperService.getUnmatchedTrxDetailList(dataList, trxIdDataMap);
        var totalRecords = dataList.size();
        var unMatchedRecords = unmatchedTrxDetailListList.size();

        return TrxComparisonDTO.builder()
                .totalRecords(totalRecords)
                .matchingRecords(totalRecords - unMatchedRecords)
                .unmatchedRecords(unMatchedRecords)
                .unmatchedRecordList(helperService.getUnmatchedRecordList(unmatchedTrxDetailListList, otherPartyDataList))
                .build();
    }
}
