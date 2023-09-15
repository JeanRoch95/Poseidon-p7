package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointServiceImpl implements CurvePointService {

    private final CurvePointRepository curvePointRepository;

    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    @Override
    public List<CurvePoint> getAllCurvePoint() {
        return curvePointRepository.findAll();
    }

    @Override
    public CurvePoint saveCurvePoint(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
        return curvePoint;
    }

    @Override
    public Optional<CurvePoint> getCurvePointById(Integer id) {
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
        return curvePoint;
    }

    @Override
    public CurvePoint updateCurvePoint(Integer id, CurvePoint updatedCurve) {
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);

        if (!curvePoint.isPresent()) {
            throw new IllegalArgumentException("BidList Not Exist");
        }

        CurvePoint existingCurvePoint = curvePoint.get();
        existingCurvePoint.setCurveId(updatedCurve.getCurveId());
        existingCurvePoint.setTerm(updatedCurve.getTerm());
        existingCurvePoint.setValue(updatedCurve.getValue());

        curvePointRepository.save(existingCurvePoint);
        return existingCurvePoint;
    }

    @Override
    public CurvePoint deleteCurvePointdById(Integer id) {
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);

        if (!curvePoint.isPresent()) {
            throw new IllegalArgumentException("CurvePoint Not Exist");
        }
        curvePointRepository.deleteById(id);
        return curvePoint.get();
    }
}
