package com.schoolerp.dto.response;
import com.schoolerp.dto.BaseDTO;

import java.math.BigDecimal;
public class FeeTypeResponseDto extends BaseDTO {
    private String name;
    private BigDecimal defaultAmount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDefaultAmount() {
        return defaultAmount;
    }

    public void setDefaultAmount(BigDecimal defaultAmount) {
        this.defaultAmount = defaultAmount;
    }
}