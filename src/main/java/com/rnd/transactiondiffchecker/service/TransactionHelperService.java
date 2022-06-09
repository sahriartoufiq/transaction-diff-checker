package com.rnd.transactiondiffchecker.service;

import com.rnd.transactiondiffchecker.dto.TransactionDetailDTO;
import com.rnd.transactiondiffchecker.dto.UnmatchedRecord;
import com.rnd.transactiondiffchecker.util.TransactionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionHelperService {

    public List<TransactionDetailDTO> getUnmatchedTrxDetailList(List<TransactionDetailDTO> dataList,
                                                                Map<String, TransactionDetailDTO> trxIdDataMap) {

        return dataList.stream()
                .filter(trxData -> {
                    var key = TransactionUtils.getTrxKeyWithTrxId(trxData);

                    if (trxIdDataMap.containsKey(key)) {
                        trxIdDataMap.remove(key);

                        return false;
                    }

                    return true;
                })
                .collect(Collectors.toList());
    }

    public List<UnmatchedRecord> getUnmatchedRecordList(List<TransactionDetailDTO> unmatchedTrxDetailListList,
                                                        List<TransactionDetailDTO> otherPartyDataList) {

        var otherPartyDataMap = getDataMapByProbableKey(otherPartyDataList);

        return unmatchedTrxDetailListList.stream()
                .map(transaction -> UnmatchedRecord.builder()
                        .transactionId(transaction.getTransactionID())
                        .transactionDescription(transaction.getTransactionDescription())
                        .transactionDate(transaction.getTransactionDate())
                        .transactionAmount(transaction.getTransactionAmount())
                        .walletReference(transaction.getWalletReference())
                        .probableMatchTrxId(otherPartyDataMap
                                .get(TransactionUtils.getTrxKeyWithoutTrxId(transaction)))
                        .build())
                .collect(Collectors.toList());
    }

    public Map<String, TransactionDetailDTO> getDataMap(List<TransactionDetailDTO> trxDataList) {
        return trxDataList
                .stream()
                .collect(Collectors
                        .toMap(TransactionUtils::getTrxKeyWithTrxId,
                                trxDetail -> trxDetail,
                                (trx1, trx2) -> trx1));
    }

    public Map<String, String> getDataMapByProbableKey(List<TransactionDetailDTO> trxDataList) {
        return trxDataList
                .stream()
                .collect(Collectors
                        .toMap(TransactionUtils::getTrxKeyWithoutTrxId,
                                TransactionDetailDTO::getTransactionID,
                                (trx1, trx2) -> trx1));
    }
}
