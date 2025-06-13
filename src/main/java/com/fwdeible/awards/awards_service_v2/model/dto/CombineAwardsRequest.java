package com.fwdeible.awards.awards_service_v2.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CombineAwardsRequest {
    private List<Long> awardIds;
}
