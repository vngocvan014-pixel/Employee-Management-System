package com.ems.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ems.dto.PositionDTO;
import com.ems.repository.PositionRepository;

@Service
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionService(
            PositionRepository positionRepository) {

        this.positionRepository =
                positionRepository;
    }

    // =====================================================
    // GET ALL
    // =====================================================

    public List<PositionDTO> getAllPositions() {

        return positionRepository
                .findAllPositions();
    }

    // =====================================================
    // GET BY ID
    // =====================================================

    public PositionDTO getPositionById(Long id) {

        return positionRepository
                .findPositionById(id);
    }

    // =====================================================
    // CREATE
    // =====================================================

    public PositionDTO createPosition(
            PositionDTO position) {

        positionRepository
                .insertPosition(position);

        return positionRepository
                .findAllPositions()
                .stream()
                .filter(p ->
                        p.getPositionCode()
                                .equals(
                                        position.getPositionCode()
                                )
                )
                .findFirst()
                .orElse(null);
    }

    // =====================================================
    // UPDATE
    // =====================================================

    public PositionDTO updatePosition(
            Long id,
            PositionDTO position) {

        PositionDTO existing =
                positionRepository
                        .findPositionById(id);

        if (existing == null) {
            return null;
        }

        int result =
                positionRepository.updatePosition(
                        id,
                        position
                );

        if (result == 0) {
            return null;
        }

        return positionRepository
                .findPositionById(id);
    }

    // =====================================================
    // DELETE
    // =====================================================

    public boolean deletePosition(Long id) {

        int result =
                positionRepository
                        .deletePosition(id);

        return result > 0;
    }
}

