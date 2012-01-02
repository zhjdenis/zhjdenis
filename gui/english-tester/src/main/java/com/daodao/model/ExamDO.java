package com.daodao.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "exam", catalog = "app")
public class ExamDO implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String description;
	private String options;
	private Date date;
	private Integer remain;
	private Integer correct;
	private Integer wrong;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the options
	 */
	@Column(name = "options")
	public String getOptions() {
		return options;
	}

	/**
	 * @param options
	 *            the options to set
	 */
	public void setOptions(String options) {
		this.options = options;
	}

	/**
	 * @return the date
	 */
	@Column(name = "date")
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the remain
	 */
	@Column(name = "remain")
	public Integer getRemain() {
		return remain;
	}

	/**
	 * @param remain
	 *            the remain to set
	 */
	public void setRemain(Integer remain) {
		this.remain = remain;
	}

	/**
	 * @return the correct
	 */
	@Column(name = "correct")
	public Integer getCorrect() {
		return correct;
	}

	/**
	 * @param correct
	 *            the correct to set
	 */
	public void setCorrect(Integer correct) {
		this.correct = correct;
	}

	/**
	 * @return the wrong
	 */
	@Column(name = "wrong")
	public Integer getWrong() {
		return wrong;
	}

	/**
	 * @param wrong
	 *            the wrong to set
	 */
	public void setWrong(Integer wrong) {
		this.wrong = wrong;
	}

}
