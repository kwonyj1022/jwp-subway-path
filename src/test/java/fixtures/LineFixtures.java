package fixtures;

import subway.domain.line.Line;
import subway.dto.LineFindResponse;
import subway.entity.LineEntity;

import java.util.List;

public class LineFixtures {

    /**
     * data
     */
    public static final long LINE2_ID = 1L;
    public static final long LINE7_ID = 2L;
    public static final String LINE2_NAME = "2호선";
    public static final String LINE7_NAME = "7호선";

    /**
     * domain
     */
    public static final Line LINE2 = new Line(LINE2_ID, LINE2_NAME);
    public static final Line LINE2_TO_INSERT = new Line(null, LINE2_NAME);
    public static final Line LINE7 = new Line(LINE7_ID, LINE7_NAME);

    /**
     * entity
     */
    public static final LineEntity LINE2_INSERT_ENTITY = new LineEntity(null, LINE2_NAME);
    public static final LineEntity LINE7_INSERT_ENTITY = new LineEntity(null, LINE7_NAME);
    public static final LineEntity LINE2_FIND_ENTITY = new LineEntity(LINE2_ID, LINE2_NAME);
    public static final LineEntity LINE7_FIND_ENTITY = new LineEntity(LINE7_ID, LINE7_NAME);

    /**
     * response
     */
    public static final LineFindResponse LINE2_노선도 = new LineFindResponse(LINE2_NAME, List.of("잠실역", "강변역", "건대역"));
    public static final LineFindResponse LINE7_노선도 = new LineFindResponse(LINE7_NAME, List.of("온수역", "대림역", "논현역", "장암역"));
    public static final List<LineFindResponse> ALL_노선도 = List.of(LINE2_노선도, LINE7_노선도);
}
