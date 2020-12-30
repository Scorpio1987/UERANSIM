package tr.havelsan.ueransim.app.common;

public class GnbId {
    public final int value;

    public GnbId(int value) {
        if (value < 0)
            throw new IllegalArgumentException("Invalid GNB ID value.");
        this.value = value;
    }

    @Override
    public String toString() {
        return "GnbId{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GnbId gnbId1 = (GnbId) o;
        return value == gnbId1.value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
