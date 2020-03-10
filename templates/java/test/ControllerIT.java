package g-alot{basePackage}.test.it.controller;

import g-alot{basePackage}.controller.g-alot{Entity}Controller;
import g-alot{basePackage}.model.g-alot{Entity};
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hibernate.internal.SessionImpl;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class g-alot{Entity}ControllerIT {

    private final int RECORD_COUNT = 3;

    private static final Long AVAILABLE_ID = 5L;
    private static final Long NOT_AVAILABLE_ID = 5555L;

    @Autowired
    private g-alot{Entity}Controller controller;

    @PersistenceContext
    private EntityManager em;

    public void dbUnitImport(EntityManager em, String dbUnitInputFile) {
        try {
            IDatabaseConnection connection = new DatabaseConnection(((SessionImpl) (em.getDelegate())).connection());
            connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());

            FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
            flatXmlDataSetBuilder.setColumnSensing(true);
            InputStream dataSet = g-alot{Entity}ControllerIT.class.getClassLoader().getResourceAsStream(dbUnitInputFile);
            IDataSet dataset = flatXmlDataSetBuilder.build(dataSet);
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);
        } catch (DatabaseUnitException | SQLException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void initDatabase() {
        dbUnitImport(em, "data.xml");
        /*
        <?xml version='1.0' encoding='UTF-8'?>
        <dataset>
            <DUMMY ID="5"/>
            <DUMMY ID="6"/>
            <DUMMY ID="7"/>
        </dataset>
        */
    }
    ///////////////////////////////////

    public static g-alot{Entity} createValidRecord_withId() {
        final g-alot{Entity} g-alot{entity} = createEmptyRecord();
        g-alot{entity}.setId(AVAILABLE_ID);
        return g-alot{entity};
    }

    public static g-alot{Entity} createValidRecord_withOutId() {
        final g-alot{Entity} g-alot{entity} = createEmptyRecord();
        // TODO: add fields
        System.out.println("g-alot{Entity} g-alot{entity} = new g-alot{Entity}()");
        Arrays.stream(g-alot{Entity}.class.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("set"))
                .forEach(method -> {
                    System.out.println("g-alot{entity}." + method.getName() + "(null)");
                });
        return g-alot{entity};
    }

    public static g-alot{Entity} createEmptyRecord() {
        return new g-alot{Entity}();
    }

    @Test
    void create_withId_exceptionThrown() {
        final g-alot{Entity} g-alot{entity} = createValidRecord_withId();
        assertThrows(IllegalArgumentException.class, () -> controller.create(g-alot{entity}));

        final int size = controller.findAll().getBody().size();
        assertThat(size, is(RECORD_COUNT));
    }

    @Test
    void create_withEmptyId() {
        final g-alot{Entity} g-alot{entity} = createValidRecord_withOutId();
        final ResponseEntity<g-alot{Entity}> response = assertDoesNotThrow(() -> controller.create(g-alot{entity}));
        assertThat(response.getBody().getId(), notNullValue());

        final int size = controller.findAll().getBody().size();
        assertThat(size, is(RECORD_COUNT+1));
    }

    @Test
    void findAll() {
        final ResponseEntity<List<g-alot{Entity}>> response = assertDoesNotThrow(() -> controller.findAll());
        MatcherAssert.assertThat(response.getBody().size(), Matchers.is(RECORD_COUNT));
    }

    @Test
    void findAll_Paged_PageTwo_sizeTen() {
        final int PAGE_SIZE = 10;
        final int PAGE_INDEX = 1;

        final PageImpl<g-alot{Entity}> page = Mockito.mock(PageImpl.class);

        final ResponseEntity<List<g-alot{Entity}>> all = assertDoesNotThrow(() ->
                controller.findAll(PAGE_INDEX, PAGE_SIZE)
        );
        assertThat(all.getBody().size(), is(0));
    }
    @Test
    void findAll_Paged_PageOne_sizeTen() {
        final int PAGE_SIZE = 10;
        final int PAGE_INDEX = 0;

        final PageImpl<g-alot{Entity}> page = Mockito.mock(PageImpl.class);

        final ResponseEntity<List<g-alot{Entity}>> all = assertDoesNotThrow(() ->
                controller.findAll(PAGE_INDEX, PAGE_SIZE)
        );
        assertThat(all.getBody().size(), is(RECORD_COUNT));
    }

    @Test
    void findAll_Paged_PageOne_sizeTwo() {
        final int PAGE_SIZE = 2;
        final int PAGE_INDEX = 0;

        final PageImpl<g-alot{Entity}> page = Mockito.mock(PageImpl.class);

        final ResponseEntity<List<g-alot{Entity}>> all = assertDoesNotThrow(() ->
                controller.findAll(PAGE_INDEX, PAGE_SIZE)
        );
        assertThat(all.getBody().size(), is(2));
    }

    @Test
    void findById_availableId() {

        final Optional<g-alot{Entity}> g-alot{entity} = controller.findById(AVAILABLE_ID).getBody();
        assertThat(g-alot{entity}.isPresent(), is(true));
        assertThat(g-alot{entity}.get().getId(), is(AVAILABLE_ID));
    }

    @Test
    void findById_notAvailableId() {
        final Optional<g-alot{Entity}> g-alot{entity} = controller.findById(NOT_AVAILABLE_ID).getBody();
        assertThat(g-alot{entity}.isPresent(), is(false));
    }

    @Test
    void save_existingRecord() {
        final g-alot{Entity} g-alot{entity} = createValidRecord_withId();
        final ResponseEntity<g-alot{Entity}> response = controller.update(g-alot{entity}, g-alot{entity}.getId());

        assertThat(response, notNullValue());

        final int size = controller.findAll().getBody().size();
        assertThat(size, is(RECORD_COUNT));
    }

    @Test
    void save_notExistingRecord() {
        final g-alot{Entity} g-alot{entity} = createValidRecord_withOutId();
        final ResponseEntity<g-alot{Entity}> response = assertDoesNotThrow(() -> controller.update(g-alot{entity}, NOT_AVAILABLE_ID));

        assertThat(response, notNullValue());
        assertThat(response.getBody().getId(), notNullValue());
        assertThat(response.getBody().getId(), not(NOT_AVAILABLE_ID));

        final int size = controller.findAll().getBody().size();
        assertThat(size, is(RECORD_COUNT+1));
    }

    @Test
    void save_notInvalidRecord() {
        final g-alot{Entity} g-alot{entity} = createValidRecord_withOutId();
        final ResponseEntity<g-alot{Entity}> response = assertDoesNotThrow(() -> controller.update(g-alot{entity}, g-alot{entity}.getId()));

        assertThat(response, notNullValue());
        assertThat(response.getBody().getId(), is(g-alot{entity}.getId()));
    }

    @Test
    void delete_existingRecord() {
        assertDoesNotThrow(() -> controller.delete(AVAILABLE_ID));
        final ResponseEntity<List<g-alot{Entity}>> all = controller.findAll();
        MatcherAssert.assertThat(all.getBody().size(), Matchers.is(RECORD_COUNT-1));
    }

    @Test
    void delete_notExistingRecord() {
        assertThrows(EmptyResultDataAccessException.class, () -> controller.delete(NOT_AVAILABLE_ID));
        final ResponseEntity<List<g-alot{Entity}>> all = controller.findAll();
        MatcherAssert.assertThat(all.getBody().size(), Matchers.is(RECORD_COUNT));
    }
}

