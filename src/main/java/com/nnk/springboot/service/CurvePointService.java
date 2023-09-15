package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;

import java.util.List;
import java.util.Optional;

public interface CurvePointService {

    List<CurvePoint> getAllCurvePoint();

    CurvePoint saveCurvePoint(CurvePoint curvePoint);

    Optional<CurvePoint> getCurvePointById(Integer id);

    CurvePoint updateCurvePoint(Integer id, CurvePoint updatedBid);

    CurvePoint deleteCurvePointdById(Integer id);
}
