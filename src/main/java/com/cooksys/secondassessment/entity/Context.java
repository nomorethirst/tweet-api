package com.cooksys.secondassessment.entity;

import java.util.List;

public class Context {
	
	private Tweet target;
	
	private List<Tweet> before;
	
	private List<Tweet> after;
	
	public Context() {}

	public Tweet getTarget() {
		return target;
	}

	public void setTarget(Tweet target) {
		this.target = target;
	}

	public List<Tweet> getBefore() {
		return before;
	}

	public void setBefore(List<Tweet> before) {
		this.before = before;
	}

	public List<Tweet> getAfter() {
		return after;
	}

	public void setAfter(List<Tweet> after) {
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
