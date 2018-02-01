package be.ordina.cyclingapp.web.rest;

import be.ordina.cyclingapp.CyclingApp;

import be.ordina.cyclingapp.domain.Race;
import be.ordina.cyclingapp.repository.RaceRepository;
import be.ordina.cyclingapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static be.ordina.cyclingapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import be.ordina.cyclingapp.domain.enumeration.TypeOfRace;
/**
 * Test class for the RaceResource REST controller.
 *
 * @see RaceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CyclingApp.class)
public class RaceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NICKNAME = "AAAAAAAAAA";
    private static final String UPDATED_NICKNAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_RACE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_RACE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_AMOUNT_OF_DAYS = 1;
    private static final Integer UPDATED_AMOUNT_OF_DAYS = 2;

    private static final TypeOfRace DEFAULT_TYPE_OF_RACE = TypeOfRace.CLASSIC_COBBLES;
    private static final TypeOfRace UPDATED_TYPE_OF_RACE = TypeOfRace.ONE_WEEK_STAGE;

    private static final String DEFAULT_YEAR_OF_FIRST_RACE = "AAAAAAAAAA";
    private static final String UPDATED_YEAR_OF_FIRST_RACE = "BBBBBBBBBB";

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRaceMockMvc;

    private Race race;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RaceResource raceResource = new RaceResource(raceRepository);
        this.restRaceMockMvc = MockMvcBuilders.standaloneSetup(raceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Race createEntity(EntityManager em) {
        Race race = new Race()
            .name(DEFAULT_NAME)
            .nickname(DEFAULT_NICKNAME)
            .dateOfRace(DEFAULT_DATE_OF_RACE)
            .amountOfDays(DEFAULT_AMOUNT_OF_DAYS)
            .typeOfRace(DEFAULT_TYPE_OF_RACE)
            .yearOfFirstRace(DEFAULT_YEAR_OF_FIRST_RACE);
        return race;
    }

    @Before
    public void initTest() {
        race = createEntity(em);
    }

    @Test
    @Transactional
    public void createRace() throws Exception {
        int databaseSizeBeforeCreate = raceRepository.findAll().size();

        // Create the Race
        restRaceMockMvc.perform(post("/api/races")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(race)))
            .andExpect(status().isCreated());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeCreate + 1);
        Race testRace = raceList.get(raceList.size() - 1);
        assertThat(testRace.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRace.getNickname()).isEqualTo(DEFAULT_NICKNAME);
        assertThat(testRace.getDateOfRace()).isEqualTo(DEFAULT_DATE_OF_RACE);
        assertThat(testRace.getAmountOfDays()).isEqualTo(DEFAULT_AMOUNT_OF_DAYS);
        assertThat(testRace.getTypeOfRace()).isEqualTo(DEFAULT_TYPE_OF_RACE);
        assertThat(testRace.getYearOfFirstRace()).isEqualTo(DEFAULT_YEAR_OF_FIRST_RACE);
    }

    @Test
    @Transactional
    public void createRaceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = raceRepository.findAll().size();

        // Create the Race with an existing ID
        race.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRaceMockMvc.perform(post("/api/races")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(race)))
            .andExpect(status().isBadRequest());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRaces() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList
        restRaceMockMvc.perform(get("/api/races?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(race.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].nickname").value(hasItem(DEFAULT_NICKNAME.toString())))
            .andExpect(jsonPath("$.[*].dateOfRace").value(hasItem(DEFAULT_DATE_OF_RACE.toString())))
            .andExpect(jsonPath("$.[*].amountOfDays").value(hasItem(DEFAULT_AMOUNT_OF_DAYS)))
            .andExpect(jsonPath("$.[*].typeOfRace").value(hasItem(DEFAULT_TYPE_OF_RACE.toString())))
            .andExpect(jsonPath("$.[*].yearOfFirstRace").value(hasItem(DEFAULT_YEAR_OF_FIRST_RACE.toString())));
    }

    @Test
    @Transactional
    public void getRace() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get the race
        restRaceMockMvc.perform(get("/api/races/{id}", race.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(race.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.nickname").value(DEFAULT_NICKNAME.toString()))
            .andExpect(jsonPath("$.dateOfRace").value(DEFAULT_DATE_OF_RACE.toString()))
            .andExpect(jsonPath("$.amountOfDays").value(DEFAULT_AMOUNT_OF_DAYS))
            .andExpect(jsonPath("$.typeOfRace").value(DEFAULT_TYPE_OF_RACE.toString()))
            .andExpect(jsonPath("$.yearOfFirstRace").value(DEFAULT_YEAR_OF_FIRST_RACE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRace() throws Exception {
        // Get the race
        restRaceMockMvc.perform(get("/api/races/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRace() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);
        int databaseSizeBeforeUpdate = raceRepository.findAll().size();

        // Update the race
        Race updatedRace = raceRepository.findOne(race.getId());
        // Disconnect from session so that the updates on updatedRace are not directly saved in db
        em.detach(updatedRace);
        updatedRace
            .name(UPDATED_NAME)
            .nickname(UPDATED_NICKNAME)
            .dateOfRace(UPDATED_DATE_OF_RACE)
            .amountOfDays(UPDATED_AMOUNT_OF_DAYS)
            .typeOfRace(UPDATED_TYPE_OF_RACE)
            .yearOfFirstRace(UPDATED_YEAR_OF_FIRST_RACE);

        restRaceMockMvc.perform(put("/api/races")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRace)))
            .andExpect(status().isOk());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
        Race testRace = raceList.get(raceList.size() - 1);
        assertThat(testRace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRace.getNickname()).isEqualTo(UPDATED_NICKNAME);
        assertThat(testRace.getDateOfRace()).isEqualTo(UPDATED_DATE_OF_RACE);
        assertThat(testRace.getAmountOfDays()).isEqualTo(UPDATED_AMOUNT_OF_DAYS);
        assertThat(testRace.getTypeOfRace()).isEqualTo(UPDATED_TYPE_OF_RACE);
        assertThat(testRace.getYearOfFirstRace()).isEqualTo(UPDATED_YEAR_OF_FIRST_RACE);
    }

    @Test
    @Transactional
    public void updateNonExistingRace() throws Exception {
        int databaseSizeBeforeUpdate = raceRepository.findAll().size();

        // Create the Race

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRaceMockMvc.perform(put("/api/races")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(race)))
            .andExpect(status().isCreated());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRace() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);
        int databaseSizeBeforeDelete = raceRepository.findAll().size();

        // Get the race
        restRaceMockMvc.perform(delete("/api/races/{id}", race.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Race.class);
        Race race1 = new Race();
        race1.setId(1L);
        Race race2 = new Race();
        race2.setId(race1.getId());
        assertThat(race1).isEqualTo(race2);
        race2.setId(2L);
        assertThat(race1).isNotEqualTo(race2);
        race1.setId(null);
        assertThat(race1).isNotEqualTo(race2);
    }
}
