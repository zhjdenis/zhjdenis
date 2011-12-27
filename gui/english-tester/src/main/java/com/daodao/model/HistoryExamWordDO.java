package com.daodao.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "history_exam_word", catalog = "app")
public class HistoryExamWordDO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String en;
    private String zh;
    private String type;
    private String source;
    private ExamDO exam;
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "en")
    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    @Column(name = "zh")
    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the exam
     */
    @ManyToOne(cascade={CascadeType.ALL})  
    public ExamDO getExam()
    {
        return exam;
    }

    /**
     * @param exam the exam to set
     */
    public void setExam(ExamDO exam)
    {
        this.exam = exam;
    }

    
   

}
