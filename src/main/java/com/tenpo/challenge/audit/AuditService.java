package com.tenpo.challenge.audit;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuditService {
    private final AuditRepository auditRepository;

    public List<Entry> getEntries(int pageNumber, int pageSize, String sortBy){
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Entry> pagedResult = auditRepository.findAll(paging);
        return pagedResult.hasContent() ? pagedResult.getContent() : List.of();
    }

    public void save(Entry entry) {
        auditRepository.save(entry);
    }
}
