package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameServiceImpl implements RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    public RuleNameServiceImpl(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    @Override
    public List<RuleName> getAllRuleName() {
        return ruleNameRepository.findAll();
    }

    @Override
    public RuleName saveRuleName(RuleName ruleName) {
        ruleNameRepository.save(ruleName);
        return ruleName;
    }

    @Override
    public Optional<RuleName> getRuleNameById(Integer id) {
        Optional<RuleName> rating = ruleNameRepository.findById(id);
        return rating;
    }

    @Override
    public RuleName updateRuleName(Integer id, RuleName updatedRuleName) {
        Optional<RuleName> ruleName = ruleNameRepository.findById(id);

        if (!ruleName.isPresent()) {
            throw new IllegalArgumentException("RuleName Not Exist");
        }

        RuleName existingRuleName = ruleName.get();
        existingRuleName.setName(updatedRuleName.getName());
        existingRuleName.setDescription(updatedRuleName.getDescription());
        existingRuleName.setJson(updatedRuleName.getJson());
        existingRuleName.setSqlPart(updatedRuleName.getSqlPart());
        existingRuleName.setSqlStr(updatedRuleName.getSqlStr());
        existingRuleName.setTemplate(updatedRuleName.getTemplate());

        ruleNameRepository.save(existingRuleName);
        return existingRuleName;
    }

    @Override
    public RuleName deleteRuleNameById(Integer id) {
        Optional<RuleName> ruleName = ruleNameRepository.findById(id);

        if (!ruleName.isPresent()) {
            throw new IllegalArgumentException("CurvePoint Not Exist");
        }
        ruleNameRepository.deleteById(id);
        return ruleName.get();
    }

}
