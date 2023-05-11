package subway.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import subway.application.LineService;

import static fixtures.LineFixtures.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LineController.class)
class LineControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LineService lineService;

    @Test
    @DisplayName("GET /lines/{id} uri로 요청하면 id에 해당하는 노선의 정보를 반환한다.")
    void findLineByIdTest() throws Exception {
        // given
        Long lineId = 1L;
        when(lineService.findLineById(lineId)).thenReturn(LINE2_노선도);

        // when, then
        mockMvc.perform(get("/lines/" + lineId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lineName").value(is(LINE2_NAME)))
                .andExpect(jsonPath("$.stationNames").value(is(LINE2_노선도.getStationNames())));
    }
}