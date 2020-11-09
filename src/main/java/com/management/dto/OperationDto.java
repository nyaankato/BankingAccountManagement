package com.management.dto;

import com.management.entity.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationDto {
    private Operation.OperationType type;
    private Date date;

    public OperationDto(Operation operation) {
        this.type = operation.getType();
        this.date = operation.getDate();
    }
}
