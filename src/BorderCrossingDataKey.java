package insight;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author pg
 */
// use this object as a key
public class BorderCrossingDataKey {

    private String border;
    private LocalDateTime crossedDate;
    private String travelSource;

    /**
     * @return the border
     */
    public String getBorder() {
        return border;
    }

    /**
     * @param border the border to set
     */
    public void setBorder(String border) {
        this.border = border;
    }

    /**
     * @return the crossedDate
     */
    public LocalDateTime getCrossedDate() {
        return crossedDate;
    }

    /**
     * @param crossedDate the crossedDate to set
     */
    public void setCrossedDate(LocalDateTime crossedDate) {
        this.crossedDate = crossedDate;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return travelSource;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String travelSource) {
        this.travelSource = travelSource;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.border);
        hash = 79 * hash + Objects.hashCode(this.crossedDate);
        hash = 79 * hash + Objects.hashCode(this.travelSource);
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
        final BorderCrossingDataKey other = (BorderCrossingDataKey) obj;
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
}
