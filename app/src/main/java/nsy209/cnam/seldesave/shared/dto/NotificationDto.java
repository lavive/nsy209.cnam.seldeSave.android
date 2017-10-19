package nsy209.cnam.seldesave.shared.dto;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NotificationDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Json(name ="id")
	private Long id;

	@Json(name ="title")
	private String title;

	@Json(name ="text")
	private String text;

	@Json(name ="topic")
	private NotificationTopicDto topic;

	@Json(name ="membersToNotify")
	private List<MemberDto> membersToNotify = new ArrayList<MemberDto>();
	
	
	
	/* getters and setters */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public NotificationTopicDto getTopic() {
		return topic;
	}

	public void setTopic(NotificationTopicDto topic) {
		this.topic = topic;
	}

	public List<MemberDto> getMembersToNotify() {
		return membersToNotify;
	}

	public void setMembersToNotify(List<MemberDto> membersToNotify) {
		this.membersToNotify = membersToNotify;
	}

	@Override
	public String toString() {
		return getTitle()+" "+getText()+" "+getTopic();
	}



}
