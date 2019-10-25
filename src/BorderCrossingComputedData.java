package insight;

import java.util.Objects;

/**
 *
 * @author pg
 */
public class BorderCrossingComputedData {

    BorderCrossingComputedData(Long totalCrossings, Long average) {
        this.value = totalCrossings;
        this.average = average;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.value);
        hash = 79 * hash + Objects.hashCode(this.average);
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
        final BorderCrossingComputedData other = (BorderCrossingComputedData) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        if (!Objects.equals(this.average, other.average)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BorderCrossingComputedData{" + "value=" + value + ", average=" + average + '}';
    }

    private long value;
    private long average;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getAverage() {
        return average;
    }

    public void setAverage(Long average) {
        this.average = average;
    }

}
