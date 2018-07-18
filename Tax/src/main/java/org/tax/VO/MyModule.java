package org.tax.VO;

import java.util.Date;
import java.util.List;

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
	private Date last_visit;
	private String pro_list;
	private long score;
	private long questionNum;
	private long answerNum;
	private List<QuestionBrief> questions;//标题、收藏数、回答数、是否悬赏、浏览数、日期
	private List<AnswerVO> answers;//问题标题、回答内容、问题浏览数、回答点赞数
	private List<InvitationVO> invitation_sents;//问题标题、日期、被邀请人
	private List<InvitationVO> invitation_receiveds;//问题标题、邀请人
	private List<TaxShare> shares;//问题标题、日期、浏览数、收藏数
	private List<TaxQuestion> favourites;//问题标题、日期、浏览数、收藏数
	private List<MessageVO> message_sents;//问题内容、接收人
	private List<MessageVO> message_receiveds;//问题内容、发出人
}
