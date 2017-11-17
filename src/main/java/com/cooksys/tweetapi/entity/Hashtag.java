package com.cooksys.tweetapi.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Hashtag {

    //TODO: case-insensitive specified here?
    @Id
    private String label;

    private Timestamp firstUsed;

    private Timestamp lastUsed;

    public Hashtag() {
    }

    public Hashtag(String label) {
        this.label = label;
        this.firstUsed = new Timestamp(System.currentTimeMillis());
        this.lastUsed = this.firstUsed;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Timestamp getFirstUsed() {
        return firstUsed;
    }

    public void setFirstUsed(Timestamp firstUsed) {
        this.firstUsed = firstUsed;
    }

    public Timestamp getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Timestamp lastUsed) {
        this.lastUsed = lastUsed;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((label == null) ? 0 : label.hashCode());
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
        Hashtag other = (Hashtag) obj;
        if (label == null) {
            if (other.label != null)
                return false;
        } else if (!label.equals(other.label))
            return false;
        return true;
    }


}
