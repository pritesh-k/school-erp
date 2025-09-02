//package com.schoolerp.mapper;
//
//import com.schoolerp.dto.request.FeeStructureItemRequest;
//import com.schoolerp.dto.response.FeeStructureItemResponse;
//import com.schoolerp.entity.FeeHead;
//import com.schoolerp.entity.FeeStructureItem;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Named;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring", uses = {FeeHeadMapper.class})
//public interface FeeStructureItemMapper {
//
//    // Entity to Response DTO
//    @Mapping(source = "feeHead", target = "feeHeadId", qualifiedByName = "feeHeadToId")
//    @Mapping(source = "feeHead", target = "feeHeadName", qualifiedByName = "feeHeadToName")
//    FeeStructureItemResponse toResponse(FeeStructureItem item);
//
//    // List mappings
//    List<FeeStructureItemResponse> toResponseList(List<FeeStructureItem> items);
//    List<FeeStructureItem> toEntityList(List<FeeStructureItemRequest> requests);
//
//    // Custom mapping methods
//    @Named("feeHeadToId")
//    default Long feeHeadToId(FeeHead feeHead) {
//        return feeHead != null ? feeHead.getId() : null;
//    }
//
//    @Named("feeHeadToName")
//    default String feeHeadToName(FeeHead feeHead) {
//        return feeHead != null ? feeHead.getName() : null;
//    }
//
//    @Named("idToFeeHead")
//    default FeeHead idToFeeHead(Long feeHeadId) {
//        if (feeHeadId == null) return null;
//        FeeHead feeHead = new FeeHead();
//        feeHead.setId(feeHeadId);
//        return feeHead;
//    }
//}
