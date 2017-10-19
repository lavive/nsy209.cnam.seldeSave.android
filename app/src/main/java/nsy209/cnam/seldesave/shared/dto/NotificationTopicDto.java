package nsy209.cnam.seldesave.shared.dto;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class NotificationTopicDto implements Serializable{

	private static final long serialVersionUID = 1L;

	@Json(name ="id")
	private Long id;

	@Json(name ="topic")
	private String topic;

	@Json(name ="personOriginEvent")
	private PersonDto personOriginEvent;

	@Json(name ="category")
	private String category;
	
	/* getters and setters */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public PersonDto getPersonOriginEvent() {
		return personOriginEvent;
	}

	public void setPersonOriginEvent(PersonDto personOriginEvent) {
		this.personOriginEvent = personOriginEvent;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	@Override
	public String toString() {
		return getTopic()+" "+getPersonOriginEvent()+" "+getCategory();
	}

}
