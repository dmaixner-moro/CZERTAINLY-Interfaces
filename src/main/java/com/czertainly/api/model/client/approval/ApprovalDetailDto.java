package com.czertainly.api.model.client.approval;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ApprovalDetailDto extends ApprovalDto {

    @Schema(
            description = "UUID of the creator",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String creatorUuid;

    @Schema(
            description = "Expiration in hours",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int expiry;

    @Schema(
            description = "Description of the Approval",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String description;

    @Schema(
            description = "List of Approval steps related to this Approval",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<ApprovalDetailStepDto> approvalSteps;

    public ApprovalDetailDto(final ApprovalDto approvalDto) {
        super(approvalDto);
    }
}
