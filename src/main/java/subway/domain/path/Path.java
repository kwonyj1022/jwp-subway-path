package subway.domain.path;

import java.util.List;
import java.util.Objects;

public class Path {

    private final List<String> stationNames;
    private final int distance;

    public Path(List<String> stationNames, int distance) {
        this.stationNames = stationNames;
        this.distance = distance;
    }

    public List<String> getStationNames() {
        return stationNames;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Path path = (Path) o;
        return distance == path.distance && Objects.equals(stationNames, path.stationNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationNames, distance);
    }

    @Override
    public String toString() {
        return "Path{" +
                "stationNames=" + stationNames +
                ", distance=" + distance +
                '}';
    }
}