package com.cooksys.secondassessment.entity;

import java.util.List;

import com.cooksys.secondassessment.dto.TweetDTO;

public class Context {
	
	private TweetDTO target;
	
	private List<TweetDTO> before;
	
	private List<TweetDTO> after;
	
	public Context() {}

	public Context(TweetDTO target, List<TweetDTO> before, List<TweetDTO> after) {
	    super();
	    this.target = target;
	    this.before = before;
	    this.after = after;
	}

	public TweetDTO getTarget() {
	    return target;
	}

	public void setTarget(TweetDTO target) {
	    this.target = target;
	}

	public List<TweetDTO> getBefore() {
	    return before;
	}

	public void setBefore(List<TweetDTO> before) {
	    this.before = before;
	}

	public List<TweetDTO> getAfter() {
	    return after;
	}

	public void setAfter(List<TweetDTO> after) {
	    this.after = after;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((target.getId() == null) ? 0 : target.getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Context other = (Context) obj;
		if (target.getId() == null) {
			if (other.target.getId() != null)
				return false;
		} else if (!target.getId().equals(other.target.getId()))
			return false;
		return true;
	}
	
	

}
