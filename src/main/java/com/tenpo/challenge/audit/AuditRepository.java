package com.tenpo.challenge.audit;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface AuditRepository extends PagingAndSortingRepository<Entry, Long> {
}
