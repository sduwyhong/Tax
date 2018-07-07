package org.tax.VO;

import lombok.Data;

import org.tax.model.TaxAnswer;
import org.tax.model.TaxMessage;
import org.tax.model.TaxQuestion;
import org.tax.model.TaxShare;

/**
 * @author wyhong
 * @date 2018-7-7
 */
@Data
public class MyModule {

	private String email;
	private String last_visit;
	private String pro_list;
	private int score;
	private int questionNum;
	private int answerNum;
	private TaxQuestion[] questions;
	private TaxAnswer[] answers;
	private Invitation[] invitation_sents;
	private Invitation[] invitation_receiveds;
	private TaxShare[] shares;
	private Favourite[] favourites;
	private TaxMessage[] message_sents;
	private TaxMessage[] message_receiveds;
}
