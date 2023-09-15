package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.impl.CurvePointServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class CurvePointServiceTest {

    @Mock
    private CurvePointService curvePointService;

    @Mock
    private CurvePointRepository curvePointRepository;

    @BeforeEach
    public void setUpBeforeEachTest() {
        curvePointService = new CurvePointServiceImpl(curvePointRepository);
    }

    @Test
    public void testGetAllCurvePoint() {
        CurvePoint point1 = new CurvePoint();
        CurvePoint point2 = new CurvePoint();
        when(curvePointRepository.findAll()).thenReturn(Arrays.asList(point1, point2));

        List<CurvePoint> result = curvePointService.getAllCurvePoint();

        assertEquals(2, result.size());
    }

    @Test
    public void testSaveCurvePoint() {
        CurvePoint point = new CurvePoint();
        when(curvePointRepository.save(point)).thenReturn(point);

        CurvePoint result = curvePointService.saveCurvePoint(point);

        assertEquals(point, result);
    }

    @Test
    public void testGetCurvePointById() {
        CurvePoint point = new CurvePoint();
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(point));

        Optional<CurvePoint> result = curvePointService.getCurvePointById(1);

        assertTrue(result.isPresent());
        assertEquals(point, result.get());
    }

    @Test
    public void testGetCurvePointById_NotFound() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        Optional<CurvePoint> result = curvePointService.getCurvePointById(1);

        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdateCurvePoint() {
        CurvePoint existingPoint = new CurvePoint();
        CurvePoint updatedPoint = new CurvePoint();
        updatedPoint.setTerm(10.0);

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(existingPoint));
        when(curvePointRepository.save(existingPoint)).thenReturn(existingPoint);

        CurvePoint result = curvePointService.updateCurvePoint(1, updatedPoint);

        assertEquals(10, result.getTerm());
    }

    @Test
    public void testUpdateCurvePoint_NotFound() {
        CurvePoint updatedPoint = new CurvePoint();

        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            curvePointService.updateCurvePoint(1, updatedPoint);
        });
    }

    @Test
    public void testDeleteCurvePointById() {
        CurvePoint point = new CurvePoint();
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(point));

        CurvePoint result = curvePointService.deleteCurvePointdById(1);

        assertEquals(point, result);
    }

    @Test
    public void testDeleteCurvePointById_NotFound() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            curvePointService.deleteCurvePointdById(1);
        });
    }

}
