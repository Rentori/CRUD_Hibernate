package service;

import junit.framework.TestCase;
import model.Label;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repository.hibernateImpl.LabelRepositoryImpl;

import java.util.Arrays;
import java.util.List;

@RunWith(value = MockitoJUnitRunner.class)
public class LabelServiceTest extends TestCase {
    private final LabelService labelService = new LabelService();
    private final Label label = new Label(1L);
    private final List<Label> labelList = Arrays.asList(label);

    @Mock
    private LabelRepositoryImpl labelRepository;

    @Mock
    private Session session;

    @Before
    public void setUp() {
        labelService.setLabelRepository(labelRepository);
    }

    @Test
    public void testSave() {
        when(labelRepository.save(any())).thenReturn(label);
        assertEquals(labelService.save(new Label(1L)), label);

        session.save(label);
        verify(session).save(label);
    }

    @Test
    public void testUpdate() {
        when(labelRepository.update(any())).thenReturn(label);
        assertEquals(labelService.update(new Label(1L)), label);

        session.update(label);
        verify(session).update(label);
    }

    @Test
    public void testGetById() {
        when(labelRepository.getById(anyLong())).thenReturn(label);
        assertEquals(labelService.getById(1L), label);

        when(session.get(same(Label.class), anyLong())).thenReturn(label);
        session.get(Label.class, 1L);
        verify(session).get(Label.class, 1L);

        when(session.get(same(Label.class), anyLong())).thenReturn(null);
        assertNull(session.get(Label.class, 1L));
    }

    @Test
    public void testDeleteById() {
        doNothing().when(labelRepository).deleteById(anyLong());
        labelService.deleteById(1L);
        verify(labelRepository).deleteById(1L);
    }

    @Test
    public void testGetAll() {
        when(labelRepository.getAll()).thenReturn(labelList);
        assertEquals(labelService.getAll(), labelList);

        when(labelRepository.getAll()).thenReturn(null);
        assertNull(labelService.getAll());
    }
}