package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.impl.RuleNameServiceImpl;
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

public class RuleNameServiceTest {

    @Mock
    private RuleNameService ruleNameService;

    @Mock
    private RuleNameRepository ruleNameRepository;

    @BeforeEach
    public void setUpBeforeEachTest() {
        ruleNameService = new RuleNameServiceImpl(ruleNameRepository);
    }

    @Test
    public void testGetAllRuleName() {
        RuleName rule1 = new RuleName();
        RuleName rule2 = new RuleName();
        when(ruleNameRepository.findAll()).thenReturn(Arrays.asList(rule1, rule2));

        List<RuleName> result = ruleNameService.getAllRuleName();

        assertEquals(2, result.size());
    }

    @Test
    public void testSaveRuleName() {
        RuleName rule = new RuleName();
        when(ruleNameRepository.save(rule)).thenReturn(rule);

        RuleName result = ruleNameService.saveRuleName(rule);

        assertEquals(rule, result);
    }

    @Test
    public void testGetRuleNameById() {
        RuleName rule = new RuleName();
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(rule));

        Optional<RuleName> result = ruleNameService.getRuleNameById(1);

        assertTrue(result.isPresent());
        assertEquals(rule, result.get());
    }

    @Test
    public void testGetRuleNameById_NotFound() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        Optional<RuleName> result = ruleNameService.getRuleNameById(1);

        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdateRuleName() {
        RuleName existingRule = new RuleName();
        RuleName updatedRule = new RuleName();
        updatedRule.setName("New Rule");

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(existingRule));
        when(ruleNameRepository.save(existingRule)).thenReturn(existingRule);

        RuleName result = ruleNameService.updateRuleName(1, updatedRule);

        assertEquals("New Rule", result.getName());
    }

    @Test
    public void testUpdateRuleName_NotFound() {
        RuleName updatedRule = new RuleName();

        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            ruleNameService.updateRuleName(1, updatedRule);
        });
    }

    @Test
    public void testDeleteRuleNameById() {
        RuleName rule = new RuleName();
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(rule));

        RuleName result = ruleNameService.deleteRuleNameById(1);

        assertEquals(rule, result);
    }

    @Test
    public void testDeleteRuleNameById_NotFound() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            ruleNameService.deleteRuleNameById(1);
        });
    }
}
