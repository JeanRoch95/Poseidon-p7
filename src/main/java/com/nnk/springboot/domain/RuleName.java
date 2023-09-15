package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "rulename")
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id", unique = true, nullable = false)
    private Integer id;

    @Column(name="name")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(name="description")
    @NotBlank(message = "Description is mandatory")
    private String description;

    @Column(name="json")
    @NotBlank(message = "Json is mandatory")
    private String json;

    @Column(name="template")
    @NotBlank(message = "template is mandatory")
    private String template;

    @Column(name="sql_str")
    @NotBlank(message = "sqlStr is mandatory")
    private String sqlStr;

    @Column(name="sql_part")
    @NotBlank(message = "sqlPart is mandatory")
    private String sqlPart;

    public RuleName() {
    }

    public RuleName(Integer id, String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSqlStr() {
        return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    public String getSqlPart() {
        return sqlPart;
    }

    public void setSqlPart(String sqlPart) {
        this.sqlPart = sqlPart;
    }}
