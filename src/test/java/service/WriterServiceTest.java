package service;

import junit.framework.TestCase;
import model.Post;
import model.Writer;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repository.hibernateImpl.WriterRepositoryImpl;

import java.util.Collections;
import java.util.List;

@RunWith(value = MockitoJUnitRunner.class)
public class WriterServiceTest extends TestCase {
    private final WriterService writerService = new WriterService();
    private final Writer writer = new Writer(1L, "test", "test");
    private final List<Writer> writerList = Collections.singletonList(writer);

    @Mock
    private WriterRepositoryImpl writerRepository;

    @Mock
    private Session session;

    @Mock
    private Query query;

    @Mock
    private Post post;

    @Before
    public void setUp() {
        writerService.setWriterRepository(writerRepository);
    }

    @Test
    public void testSave() {
        when(writerRepository.save(any())).thenReturn(writer);
        assertEquals(writerService.save(writer), writer);

        when(query.getSingleResult()).thenReturn(post);
        assertEquals(query.getSingleResult(), post);
    }

    @Test
    public void testUpdate() {
        when(writerRepository.update(any())).thenReturn(writer);
        assertEquals(writerService.update(writer), writer);

        session.update(writer);
        verify(session).update(writer);
    }

    @Test
    public void testGetById() {
        when(writerRepository.getById(anyLong())).thenReturn(writer);
        assertEquals(writerService.getById(1L), writer);

        when(session.get(same(Writer.class), anyLong())).thenReturn(writer);
        session.get(Writer.class, 1L);
        verify(session).get(Writer.class, 1L);

        when(session.get(same(Writer.class), anyLong())).thenReturn(null);
        assertNull(session.get(Writer.class, 1L));
    }

    @Test
    public void testDeleteById() {
        doNothing().when(writerRepository).deleteById(anyLong());
        writerService.deleteById(1L);
        verify(writerRepository).deleteById(1L);
    }

    @Test
    public void testGetAll() {
        when(writerRepository.getAll()).thenReturn(writerList);
        assertEquals(writerService.getAll(), writerList);

        when(writerRepository.getAll()).thenReturn(null);
        assertNull(writerService.getAll());
    }
}