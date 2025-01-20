package org.fundacionjala.virtualassistant.repository;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestEntityRepositoryTest {
  private RequestEntityRepository repository;

  @BeforeEach
  void setUp() {
    repository = mock(RequestEntityRepository.class);
  }

  @Test
  void repositoryLoads() {
    assertNotNull(repository);
  }

  @Test
  void addUserWithMock() {
    RequestEntity request = new RequestEntity();
    when(repository.save(any(RequestEntity.class))).thenReturn(request);

    assertEquals(repository.save(request), request);
  }
}