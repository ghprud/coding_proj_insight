/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insight;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author pg
 */
public class FinalOutput {

    private String border;
    private LocalDateTime crossedDate;
    private String travelSource;

    private long value;
    private long average;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.border);
        hash = 53 * hash + Objects.hashCode(this.crossedDate);
        hash = 53 * hash + Objects.hashCode(this.travelSource);
        hash = 53 * hash + (int) (this.value ^ (this.value >>> 32));
        hash = 53 * hash + (int) (this.average ^ (this.average >>> 32));
        return hash;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FinalOutput other = (FinalOutput) obj;
        if (this.value != other.value) {
            return false;
        }
        if (this.average != other.average) {
            return false;
        }
        if (!Objects.equals(this.border, other.border)) {
            return false;
        }
        if (!Objects.equals(this.travelSource, other.travelSource)) {
            return false;
        }
        if (!Objects.equals(this.crossedDate, other.crossedDate)) {
            return false;
        }
        return true;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public LocalDateTime getCrossedDate() {
        return crossedDate;
    }

    public void setCrossedDate(LocalDateTime crossedDate) {
        this.crossedDate = crossedDate;
    }

    public String getTravelSource() {
        return travelSource;
    }

    public void setTravelSource(String travelSource) {
        this.travelSource = travelSource;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getAverage() {
        return average;
    }

    public void setAverage(long average) {
        this.average = average;
    }


}
