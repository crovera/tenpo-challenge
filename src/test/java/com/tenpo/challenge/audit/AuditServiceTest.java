package com.tenpo.challenge.audit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuditServiceTest {
    private AuditService auditService;
    private AuditRepository auditRepository;

    @BeforeEach
    public void setup() {
        auditRepository = mock(AuditRepository.class);
        auditService = new AuditService(auditRepository);
    }

    @Test
    void getAuditEvents_success() {
        Entry entry1 = mock(Entry.class);
        Entry entry2 = mock(Entry.class);
        Page<Entry> page = mock(Page.class);

        when(auditRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(page.hasContent()).thenReturn(true);
        when(page.getContent()).thenReturn(List.of(entry1, entry2));

        List<Entry> entries = auditService.getEntries(1, 3, "id");

        assertEquals(2, entries.size());
        assertEquals(entry1, entries.get(0));
        assertEquals(entry2, entries.get(1));
    }

    @Test
    void getAuditEvents_empty() {
        Page<Entry> page = mock(Page.class);

        when(auditRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(page.hasContent()).thenReturn(true);
        when(page.getContent()).thenReturn(List.of());

        List<Entry> entries = auditService.getEntries(1, 3, "id");

        assertEquals(0, entries.size());
    }
}
