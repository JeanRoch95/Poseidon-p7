package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;

import java.util.List;
import java.util.Optional;

public interface RuleNameService {

    List<RuleName> getAllRuleName();

    RuleName saveRuleName(RuleName ruleName);

    Optional<RuleName> getRuleNameById(Integer id);

    RuleName updateRuleName(Integer id, RuleName updatedRuleName);

    RuleName deleteRuleNameById(Integer id);
}
