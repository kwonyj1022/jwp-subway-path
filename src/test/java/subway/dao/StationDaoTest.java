package subway.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import subway.entity.StationEntity;

import java.util.Optional;

import static fixtures.StationFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql({"/test-schema.sql", "/test-data.sql"})
class StationDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private StationDao stationDao;

    @BeforeEach
    void setUp() {
        this.stationDao = new StationDao(jdbcTemplate);
    }

    @Test
    @DisplayName("Station을 저장한다.")
    void insertTest() {
        // given
        StationEntity entityToInsert = ENTITY_강변역_INSERT;

        // when
        StationEntity insertedStationEntity = stationDao.insert(entityToInsert);

        // then
        assertThat(insertedStationEntity).isEqualTo(ENTITY_강변역_FIND);
    }

    @Test
    @DisplayName("받은 역 이름과 노선 이름에 해당하는 행을 조회한다.")
    void findByStationNameAndLineNameTest() {
        // given
        Long stationId = STATION_건대역_ID;
        Long lineId = LINE2_ID;

        // when
        Optional<StationEntity> findStationEntity = stationDao.findByStationIdAndLineId(stationId, lineId);

        // then
        assertThat(findStationEntity).contains(ENTITY_건대역_FIND);
    }
}