package subway.domain.calculator;

import subway.domain.line.LineStatus;
import subway.domain.section.ContainingSections;
import subway.domain.section.Distance;
import subway.domain.section.Section;
import subway.domain.station.Station;

import java.util.ArrayList;
import java.util.List;

import static subway.domain.line.LineStatus.INITIAL;

public class AddCalculator {

    private final ContainingSections containingSections;

    public AddCalculator(ContainingSections containingSections) {
        this.containingSections = containingSections;
    }

    public Changes addSection(LineStatus lineStatus, Section newSection) {
        if (lineStatus == INITIAL && containingSections.isEmpty()) {
            return new Changes(newSection.getLineId(), new ArrayList<>(), new ArrayList<>(),
                    List.of(newSection.getUpStation(), newSection.getDownStation()), new ArrayList<>(),
                    List.of(newSection), new ArrayList<>());
        }
        if (containingSections.isInsertSectionUpEndCase(newSection)) {
            return new Changes(newSection.getLineId(), new ArrayList<>(), new ArrayList<>(),
                    List.of(newSection.getUpStation()), new ArrayList<>(),
                    List.of(newSection), new ArrayList<>());
        }
        if (containingSections.isInsertSectionDownEndCase(newSection)) {
            return new Changes(newSection.getLineId(), new ArrayList<>(), new ArrayList<>(),
                    List.of(newSection.getDownStation()), new ArrayList<>(),
                    List.of(newSection), new ArrayList<>());
        }
        Section targetSection = containingSections.getTargetSection(newSection);
        return insertBetweenTargetSection(targetSection, newSection);
    }

    private Changes insertBetweenTargetSection(Section targetSection, Section newSection) {
        if (isSameSection(targetSection, newSection)) {
            throw new IllegalArgumentException("이미 포함되어 있는 구간입니다.");
        }
        if (isInsertBetweenUpside(targetSection, newSection)) {
            return getChangesWhenInsertBetweenUpside(targetSection, newSection);
        }
        if (isInsertBetweenDownside(targetSection, newSection)) {
            return getChangesWhenInsertBetweenDownside(targetSection, newSection);
        }
        return getChangesWhenNothingChanges(newSection);
    }

    private boolean isSameSection(Section targetSection, Section newSection) {
        return isInsertBetweenUpside(targetSection, newSection) &&
                isInsertBetweenDownside(targetSection, newSection);
    }

    private boolean isInsertBetweenUpside(Section targetSection, Section newSection) {
        return isSameStation(targetSection.getUpStation(), newSection.getUpStation());
    }

    private Changes getChangesWhenInsertBetweenUpside(Section targetSection, Section newSection) {
        Section modifiedSection = new Section(null, newSection.getDownStation(),
                targetSection.getDownStation(), subtractDistanceOf(targetSection, newSection));
        return new Changes(newSection.getLineId(), new ArrayList<>(), new ArrayList<>(),
                List.of(newSection.getDownStation()), new ArrayList<>(),
                List.of(newSection, modifiedSection), List.of(targetSection));
    }

    private Distance subtractDistanceOf(Section currentSection, Section otherSection) {
        Distance currentDistance = currentSection.getDistance();
        Distance otherSectionDistance = otherSection.getDistance();
        return currentDistance.subtract(otherSectionDistance);
    }

    private boolean isInsertBetweenDownside(Section targetSection, Section newSection) {
        return isSameStation(targetSection.getDownStation(), newSection.getDownStation());
    }

    private Changes getChangesWhenInsertBetweenDownside(Section targetSection, Section newSection) {
        Section modifiedSection = new Section(null, targetSection.getUpStation(),
                newSection.getUpStation(), subtractDistanceOf(targetSection, newSection));
        return new Changes(newSection.getLineId(), new ArrayList<>(), new ArrayList<>(),
                List.of(newSection.getUpStation()), new ArrayList<>(),
                List.of(modifiedSection, newSection), List.of(targetSection));
    }

    private Changes getChangesWhenNothingChanges(Section newSection) {
        return new Changes(newSection.getLineId(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
    }

    private boolean isSameStation(Station station1, Station station2) {
        return station1.getLineName().equals(station2.getLineName()) &&
                station1.getName().equals(station2.getName());
    }
}
