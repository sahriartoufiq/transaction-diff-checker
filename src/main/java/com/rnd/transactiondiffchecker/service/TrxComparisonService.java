package com.rnd.transactiondiffchecker.service;

import com.rnd.transactiondiffchecker.dto.TransactionDetailDTO;
import com.rnd.transactiondiffchecker.dto.TrxComparisonDTO;
import com.rnd.transactiondiffchecker.dto.UnMatchedRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrxComparisonService {

    private final HelperService helperService;

    public TrxComparisonDTO getTrxComparisonDTO(List<TransactionDetailDTO> dataList,
                                                List<TransactionDetailDTO> otherPartyDataList) {

        var trxIdDataMap = getDataMap(otherPartyDataList);
        var unMatchedList = getUnMatchedTrxList(dataList, trxIdDataMap);
        var total = dataList.size();
        var unMatched = unMatchedList.size();

        return TrxComparisonDTO.builder()
                .totalRecords(total)
                .matchingRecords(total - unMatched)
                .unmatchedRecords(unMatched)
                .unmatchedRecordList(getUnMatchedTransactionList(unMatchedList, otherPartyDataList))
                .build();
    }

    private List<TransactionDetailDTO> getUnMatchedTrxList(List<TransactionDetailDTO> dataList,
                                                           Map<String, TransactionDetailDTO> trxIdDataMap) {

        return dataList.stream()
                .filter(trxData -> {
                    if (trxIdDataMap.containsKey(helperService.getTrxKeyWithTrxId(trxData))) {
                        trxIdDataMap.remove(trxData.getTransactionID());

                        return false;
                    }

                    return true;
                })
                .collect(Collectors.toList());
    }

    private Map<String, TransactionDetailDTO> getDataMap(List<TransactionDetailDTO> trxDataList) {
        return trxDataList
                .stream()
                .collect(Collectors.toMap(helperService::getTrxKeyWithTrxId, trxDetail -> trxDetail, (o1, o2) -> o1));
    }

    private Map<String, String> getDataMapByProbableKey(List<TransactionDetailDTO> trxDataList) {
        return trxDataList
                .stream()
                .collect(Collectors
                        .toMap(helperService::getTrxKeyWithoutTrxId, TransactionDetailDTO::getTransactionID, (o1, o2) -> o1));
    }

    private List<UnMatchedRecord> getUnMatchedTransactionList(List<TransactionDetailDTO> unMatchedList,
                                                              List<TransactionDetailDTO> otherPartyDataList) {

        var otherPartyDataMap = getDataMapByProbableKey(otherPartyDataList);

        return unMatchedList.stream()
                .map(transaction -> UnMatchedRecord.builder()
                        .transactionId(transaction.getTransactionID())
                        .transactionDate(transaction.getTransactionDate())
                        .transactionAmount(transaction.getTransactionAmount())
                        .walletReference(transaction.getWalletReference())
                        .probableMatchTrxId(otherPartyDataMap
                                .get(helperService.getTrxKeyWithoutTrxId(transaction)))
                        .build())
                .collect(Collectors.toList());
    }
}

