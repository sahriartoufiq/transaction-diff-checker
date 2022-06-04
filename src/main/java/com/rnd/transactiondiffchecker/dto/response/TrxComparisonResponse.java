package com.rnd.transactiondiffchecker.dto.response;

import com.rnd.transactiondiffchecker.dto.TrxComparisonDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrxComparisonResponse {

    private TrxComparisonDTO clientResult;
    private TrxComparisonDTO orgResult;
}
