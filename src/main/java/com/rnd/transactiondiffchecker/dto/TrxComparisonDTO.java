package com.rnd.transactiondiffchecker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrxComparisonDTO {

    private int totalRecords;
    private int matchingRecords;
    private int unmatchedRecords;
    private List<UnmatchedRecord> unmatchedRecordList;
}
