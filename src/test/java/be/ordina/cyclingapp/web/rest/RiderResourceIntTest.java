package be.ordina.cyclingapp.web.rest;

import be.ordina.cyclingapp.CyclingApp;

import be.ordina.cyclingapp.domain.Rider;
import be.ordina.cyclingapp.repository.RiderRepository;
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

/**
 * Test class for the RiderResource REST controller.
 *
 * @see RiderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CyclingApp.class)
public class RiderResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_AMOUNT_OF_VICTORIES = 1;
    private static final Integer UPDATED_AMOUNT_OF_VICTORIES = 2;

    private static final Integer DEFAULT_LENGTH = 1;
    private static final Integer UPDATED_LENGTH = 2;

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRiderMockMvc;

    private Rider rider;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RiderResource riderResource = new RiderResource(riderRepository);
        this.restRiderMockMvc = MockMvcBuilders.standaloneSetup(riderResource)
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
    public static Rider createEntity(EntityManager em) {
        Rider rider = new Rider()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .amountOfVictories(DEFAULT_AMOUNT_OF_VICTORIES)
            .length(DEFAULT_LENGTH);
        return rider;
    }

    @Before
    public void initTest() {
        rider = createEntity(em);
    }

    @Test
    @Transactional
    public void createRider() throws Exception {
        int databaseSizeBeforeCreate = riderRepository.findAll().size();

        // Create the Rider
        restRiderMockMvc.perform(post("/api/riders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rider)))
            .andExpect(status().isCreated());

        // Validate the Rider in the database
        List<Rider> riderList = riderRepository.findAll();
        assertThat(riderList).hasSize(databaseSizeBeforeCreate + 1);
        Rider testRider = riderList.get(riderList.size() - 1);
        assertThat(testRider.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testRider.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testRider.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testRider.getAmountOfVictories()).isEqualTo(DEFAULT_AMOUNT_OF_VICTORIES);
        assertThat(testRider.getLength()).isEqualTo(DEFAULT_LENGTH);
    }

    @Test
    @Transactional
    public void createRiderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = riderRepository.findAll().size();

        // Create the Rider with an existing ID
        rider.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRiderMockMvc.perform(post("/api/riders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rider)))
            .andExpect(status().isBadRequest());

        // Validate the Rider in the database
        List<Rider> riderList = riderRepository.findAll();
        assertThat(riderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRiders() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList
        restRiderMockMvc.perform(get("/api/riders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rider.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].amountOfVictories").value(hasItem(DEFAULT_AMOUNT_OF_VICTORIES)))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)));
    }

    @Test
    @Transactional
    public void getRider() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get the rider
        restRiderMockMvc.perform(get("/api/riders/{id}", rider.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rider.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.amountOfVictories").value(DEFAULT_AMOUNT_OF_VICTORIES))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH));
    }

    @Test
    @Transactional
    public void getNonExistingRider() throws Exception {
        // Get the rider
        restRiderMockMvc.perform(get("/api/riders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRider() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);
        int databaseSizeBeforeUpdate = riderRepository.findAll().size();

        // Update the rider
        Rider updatedRider = riderRepository.findOne(rider.getId());
        // Disconnect from session so that the updates on updatedRider are not directly saved in db
        em.detach(updatedRider);
        updatedRider
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .amountOfVictories(UPDATED_AMOUNT_OF_VICTORIES)
            .length(UPDATED_LENGTH);

        restRiderMockMvc.perform(put("/api/riders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRider)))
            .andExpect(status().isOk());

        // Validate the Rider in the database
        List<Rider> riderList = riderRepository.findAll();
        assertThat(riderList).hasSize(databaseSizeBeforeUpdate);
        Rider testRider = riderList.get(riderList.size() - 1);
        assertThat(testRider.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testRider.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testRider.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testRider.getAmountOfVictories()).isEqualTo(UPDATED_AMOUNT_OF_VICTORIES);
        assertThat(testRider.getLength()).isEqualTo(UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void updateNonExistingRider() throws Exception {
        int databaseSizeBeforeUpdate = riderRepository.findAll().size();

        // Create the Rider

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRiderMockMvc.perform(put("/api/riders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rider)))
            .andExpect(status().isCreated());

        // Validate the Rider in the database
        List<Rider> riderList = riderRepository.findAll();
        assertThat(riderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRider() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);
        int databaseSizeBeforeDelete = riderRepository.findAll().size();

        // Get the rider
        restRiderMockMvc.perform(delete("/api/riders/{id}", rider.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rider> riderList = riderRepository.findAll();
        assertThat(riderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rider.class);
        Rider rider1 = new Rider();
        rider1.setId(1L);
        Rider rider2 = new Rider();
        rider2.setId(rider1.getId());
        assertThat(rider1).isEqualTo(rider2);
        rider2.setId(2L);
        assertThat(rider1).isNotEqualTo(rider2);
        rider1.setId(null);
        assertThat(rider1).isNotEqualTo(rider2);
    }
}
