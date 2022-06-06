package com.rnd.transactiondiffchecker.service;

import com.rnd.transactiondiffchecker.dto.TransactionDetailDTO;
import com.rnd.transactiondiffchecker.dto.TrxComparisonDTO;
import com.rnd.transactiondiffchecker.dto.UnMatchedTransaction;
import com.rnd.transactiondiffchecker.dto.response.TrxComparisonResponse;
import com.rnd.transactiondiffchecker.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrxComparisonService {

    private final FileIOService fileIOService;

    public TrxComparisonResponse getTrxComparisonResponse(MultipartFile clientReport,
                                                          MultipartFile orgReport) {

        try {
            var clientReportDataList = fileIOService.getTransactionDetailList(clientReport);
            var orgReportDataList = fileIOService.getTransactionDetailList(orgReport);

            return getTrxComparisonResponse(orgReportDataList, clientReportDataList);
        } catch (IOException ex) {
            throw new ApiException("Invalid file content!");
        }
    }

    private TrxComparisonResponse getTrxComparisonResponse(List<TransactionDetailDTO> clientReportDataList,
                                                           List<TransactionDetailDTO> orgReportDataList) {

        return TrxComparisonResponse.builder()
                .orgResult(getTrxComparisonDTO(orgReportDataList, clientReportDataList))
                .clientResult(getTrxComparisonDTO(clientReportDataList, orgReportDataList))
                .build();
    }

    private TrxComparisonDTO getTrxComparisonDTO(List<TransactionDetailDTO> dataList,
                                                 List<TransactionDetailDTO> otherPartyDataList) {

        var trxIdDataMap = getDataMap(otherPartyDataList);
        var unMatchedList = dataList.stream()
                .filter(trxData -> {
                    if (trxIdDataMap.containsKey(getTrxKeyWithTrxId(trxData))) {
                        trxIdDataMap.remove(trxData.getTransactionID());

                        return false;
                    }

                    return true;
                })
                .collect(Collectors.toList());

        var total = dataList.size();
        var unMatched = unMatchedList.size();

        return TrxComparisonDTO.builder()
                .totalRecords(total)
                .matchingRecords(total - unMatched)
                .unmatchedRecords(unMatched)
                .unmatchedTrxList(getUnMatchedTransactionList(unMatchedList, otherPartyDataList))
                .build();
    }

    private Map<String, TransactionDetailDTO> getDataMap(List<TransactionDetailDTO> trxDataList) {
        return trxDataList
                .stream()
                .collect(Collectors.toMap(this::getTrxKeyWithTrxId, trxDetail -> trxDetail, (o1, o2) -> o1));
    }

    private Map<String, TransactionDetailDTO> getDataMapByProbableKey(List<TransactionDetailDTO> trxDataList) {
        return trxDataList
                .stream()
                .collect(Collectors.toMap(this::getTrxKeyWithoutTrxId, trxDetail -> trxDetail, (o1, o2) -> o1));
    }

    private List<UnMatchedTransaction> getUnMatchedTransactionList(List<TransactionDetailDTO> unMatchedList,
                                                                   List<TransactionDetailDTO> otherPartyDataList) {

        var otherPartyDataMap = getDataMapByProbableKey(otherPartyDataList);

        return unMatchedList.stream()
                .map(transaction -> UnMatchedTransaction.builder()
                        .originalTransaction(transaction)
                        .probableMatchTransaction(otherPartyDataMap
                                .get(getTrxKeyWithoutTrxId(transaction)))
                        .build())
                .collect(Collectors.toList());
    }

    private String getTrxKeyWithTrxId(TransactionDetailDTO transaction) {
        return String.format("%s-%s",
                transaction.getTransactionID(),
                transaction.getTransactionType());
    }

    private String getTrxKeyWithoutTrxId(TransactionDetailDTO transaction) {
        return String.format("%s-%s-%s-%s",
                transaction.getWalletReference(),
                transaction.getTransactionType(),
                transaction.getTransactionAmount(),
                transaction.getTransactionDate());
    }
}
