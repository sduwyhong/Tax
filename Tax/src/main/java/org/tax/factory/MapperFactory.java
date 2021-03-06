package org.tax.factory;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.tax.dao.TaxAnswerMapper;
import org.tax.dao.TaxExpertMapper;
import org.tax.dao.TaxFavouriteAnswerMapper;
import org.tax.dao.TaxFavouriteMapper;
import org.tax.dao.TaxInvitationMapper;
import org.tax.dao.TaxMessageMapper;
import org.tax.dao.TaxMessageReplyMapper;
import org.tax.dao.TaxProMapper;
import org.tax.dao.TaxQuestionMapper;
import org.tax.dao.TaxQuestionProMapper;
import org.tax.dao.TaxShareMapper;
import org.tax.dao.TaxUserMapper;
import org.tax.dao.TaxUserProMapper;

@Data
public class MapperFactory {

	@Autowired
	private TaxUserMapper taxUserMapper;
	@Autowired
	private TaxQuestionMapper taxQuestionMapper;
	@Autowired
	private TaxShareMapper taxShareMapper;
	@Autowired
	private TaxExpertMapper taxExpertMapper;
	@Autowired
	private TaxProMapper taxProMapper;
	@Autowired
	private TaxAnswerMapper taxAnswerMapper;
	@Autowired
	private TaxFavouriteMapper taxFavouriteMapper;
	@Autowired
	private TaxFavouriteAnswerMapper taxFavouriteAnswerMapper;
	@Autowired
	private TaxUserProMapper taxUserProMapper;
	@Autowired
	private TaxInvitationMapper taxInvitationMapper;
	@Autowired
	private TaxMessageMapper taxMessageMapper;
	@Autowired
	private TaxMessageReplyMapper taxMessagereplyMapper;
	@Autowired
	private TaxQuestionProMapper taxQuestionProMapper;
//	public TaxUserMapper getTaxUserMapper() {
//		return taxUserMapper;
//	}
//
//	public TaxQuestionMapper getTaxQuestionMapper() {
//		return taxQuestionMapper;
//	}
//
//	public TaxShareMapper getTaxShareMapper() {
//		return taxShareMapper;
//	}
//	
//	public TaxExpertMapper getTaxExpertMapper() {
//		return taxExpertMapper;
//	}
//
//	public TaxProMapper getTaxProMapper() {
//		return taxProMapper;
//	}
//
//	public TaxAnswerMapper getTaxAnswerMapper() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
}
