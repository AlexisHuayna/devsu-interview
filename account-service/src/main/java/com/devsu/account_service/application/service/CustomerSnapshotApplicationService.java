package com.devsu.account_service.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.account_service.domain.entity.CustomerSnapshot;
import com.devsu.account_service.domain.repository.CustomerSnapshotRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerSnapshotApplicationService {
    
    private final CustomerSnapshotRepository customerSnapshotRepository;

    public void createSnapshot(
        Long customerId,
        String name,
        boolean active
    ){
        if(customerSnapshotRepository.existsByCustomerId(customerId)) {
            return ;
        }

        CustomerSnapshot snapshot = new CustomerSnapshot(
            customerId,
            name,
            active
        );

        customerSnapshotRepository.save(snapshot);
    }

    public void updateSnapshot(
        Long customerId,
        String name,
        boolean active
    ) {
        CustomerSnapshot snapshot = customerSnapshotRepository
                .findByCustomerId(customerId)
                .orElseThrow(
                    () -> new IllegalStateException(
                        "Cutomer snapshot not found"
                    )
                );

        snapshot.update(name, active);
    }

    public void deactivateSnapshot(Long customerId){
        CustomerSnapshot snapshot = customerSnapshotRepository
                .findByCustomerId(customerId)
                .orElseThrow(
                    () -> new IllegalStateException(
                        "Cutomer snapshot not found"
                    )
                );

        snapshot.deactivate();
    }
}
