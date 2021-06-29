package service;

import junit.framework.TestCase;
import model.Label;
import model.Post;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repository.hibernateImpl.PostRepositoryImpl;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(value = MockitoJUnitRunner.class)
public class PostServiceTest extends TestCase {
    private final PostService postService = new PostService();
    private final Post post = new Post(1L);
    private final List<Post> postList = Collections.singletonList(post);

    @Mock
    private PostRepositoryImpl postRepository;

    @Mock
    private Session session;

    @Mock
    private Query query;

    @Mock
    private Label label;

    @Before
    public void setUp() {
        postService.setPostRepository(postRepository);
    }

    @Test
    public void testSave() {
        when(postRepository.save(any())).thenReturn(post);
        assertEquals(postService.save(new Post(1L)), post);

        when(query.getSingleResult()).thenReturn(label);
        assertEquals(query.getSingleResult(), label);
    }

    @Test
    public void testUpdate() {
        when(postRepository.update(any())).thenReturn(post);
        assertEquals(postService.update(new Post(1L)), post);

        session.update(post);
        verify(session).update(post);
    }

    @Test
    public void testGetById() {
        when(postRepository.getById(anyLong())).thenReturn(post);
        assertEquals(postService.getById(1L), post);

        when(session.get(same(Post.class), anyLong())).thenReturn(post);
        session.get(Post.class, 1L);
        verify(session).get(Post.class, 1L);

        when(session.get(same(Post.class), anyLong())).thenReturn(null);
        assertNull(session.get(Post.class, 1L));
    }

    @Test
    public void testDeleteById() {
        doNothing().when(postRepository).deleteById(anyLong());
        postService.deleteById(1L);
        verify(postRepository).deleteById(1L);
    }

    @Test
    public void testGetAll() {
        when(postRepository.getAll()).thenReturn(postList);
        assertEquals(postService.getAll(), postList);

        when(postRepository.getAll()).thenReturn(null);
        assertNull(postService.getAll());
    }
}