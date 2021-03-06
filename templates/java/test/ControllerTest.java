package g-alot{basePackage}.test.unit.controller;

import g-alot{basePackage}.model.g-alot{Entity};
import g-alot{basePackage}.service.g-alot{Entity}Service;
import g-alot{basePackage}.service.g-alot{Entity}ServiceImpl;
import g-alot{basePackage}.repository.g-alot{Entity}Repository;
import g-alot{basePackage}.controller.g-alot{Entity}Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class g-alot{Entity}ControllerTest {

    @Mock
    private g-alot{Entity}Repository repository;

    private g-alot{Entity}Controller controller;

    private static Long ID_CACHE = 456L;

    public static void printSetters(String args) {
        System.out.println("g-alot{Entity} g-alot{entity} = new g-alot{Entity}()");
        Arrays.stream(g-alot{Entity}.class.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("set"))
                .forEach(method -> {
                    System.out.println("g-alot{entity}." + method.getName() + "(null)");
                });
    }

    @BeforeEach
    public void beforeEach() {
        final g-alot{Entity}ServiceImpl service = new g-alot{Entity}ServiceImpl(repository);
        this.controller = new g-alot{Entity}Controller(service);
    }

    public static g-alot{Entity} createValidRecord_withId() {
        final g-alot{Entity} g-alot{entity} = createValidRecord_withOutId();
        g-alot{entity}.setId(ID_CACHE++);
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
        verify(repository, times(0))
                .save(g-alot{entity});
    }

    @Test
    void create_withEmptyId() {
        final g-alot{Entity} g-alot{entity} = createValidRecord_withOutId();
        final ResponseEntity<g-alot{Entity}> response = assertDoesNotThrow(() -> controller.create(g-alot{entity}));
        verify(repository, times(1))
                .save(g-alot{entity});
    }

    @Test
    void findAll() {
        final ResponseEntity<List<g-alot{Entity}>> response = assertDoesNotThrow(() -> controller.findAll());
        verify(repository, times(1))
                .findAll();
    }

    @Test
    void findAll_Paged() {
        final int PAGE_SIZE = 10;
        final int PAGE_INDEX = 1;
        final List<g-alot{Entity}> records = Arrays.asList(
                createValidRecord_withId(),
                createValidRecord_withId(),
                createValidRecord_withId(),
                createValidRecord_withId()
        );

        final PageImpl<g-alot{Entity}> page = Mockito.mock(PageImpl.class);
        when(page.getContent()).thenReturn(records);
        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        final ResponseEntity<List<g-alot{Entity}>> all = assertDoesNotThrow(() ->
                controller.findAll(PAGE_INDEX, PAGE_SIZE)
        );

        verify(repository, times(1))
                .findAll(PageRequest.of(PAGE_INDEX, PAGE_SIZE));

        assertThat(all.getBody(), is(records));
    }

    @Test
    void findById_availableId() {
        final ResponseEntity<Optional<g-alot{Entity}>> response = assertDoesNotThrow(() -> controller.findById(1L));
        verify(repository, times(1))
                .findById(anyLong());
    }

    @Test
    void findById_notAvailableId() {
        final ResponseEntity<Optional<g-alot{Entity}>> response = assertDoesNotThrow(() -> controller.findById(1L));
        verify(repository, times(1))
                .findById(anyLong());
    }

    @Test
    void save_existingRecord() {
        final g-alot{Entity} g-alot{entity} = createValidRecord_withId();
        final ResponseEntity<g-alot{Entity}> response = assertDoesNotThrow(() -> controller.update(g-alot{entity}, g-alot{entity}.getId()));
        verify(repository, times(1))
                .save(g-alot{entity});
    }

    @Test
    void save_notExistingRecord() {
        final g-alot{Entity} g-alot{entity} = createValidRecord_withOutId();
        final ResponseEntity<g-alot{Entity}> response = assertDoesNotThrow(() -> controller.update(g-alot{entity}, g-alot{entity}.getId()));
        verify(repository, times(1))
                .save(g-alot{entity});
    }

    @Test
    void save_notInvalidRecord() {
        final g-alot{Entity} g-alot{entity} = createEmptyRecord();
        final ResponseEntity<g-alot{Entity}> response = assertDoesNotThrow(() -> controller.update(g-alot{entity}, g-alot{entity}.getId()));
        verify(repository, times(1))
                .save(g-alot{entity});
    }

    @Test
    void delete_existingRecord() {
        assertDoesNotThrow(() -> controller.delete(1L));
        verify(repository, times(1))
                .deleteById(anyLong());
    }

    @Test
    void delete_notExistingRecord() throws NoSuchMethodException {
        assertDoesNotThrow(() -> controller.delete(1L));
        verify(repository, times(1))
                .deleteById(anyLong());
    }
}
