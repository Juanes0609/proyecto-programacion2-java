package co.edu.uniquindio.logisticsapp.util.mapper;

import co.edu.uniquindio.logisticsapp.dto.TariffDTO;
import co.edu.uniquindio.logisticsapp.model.Tariff;

public class TariffMapper {
    /**
     * Convierte una entidad Tariff en un TariffDTO.
     */
    public static TariffDTO entityToDto(Tariff tariff) {
        if (tariff == null) {
            return null;
        }

        TariffDTO dto = new TariffDTO();
        dto.setBaseRate(tariff.getBaseRate());
        dto.setWeightRate(tariff.getWeightRate());
        dto.setDistanceRate(tariff.getDistanceRate());
        dto.setPriorityRate(tariff.getPriorityRate());
        
        return dto;
    }

    /**
     * Convierte un TariffDTO de la interfaz en una entidad Tariff para ser usada o guardada.
     */
    public static Tariff dtoToEntity(TariffDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Tariff(
            dto.getBaseRate(),
            dto.getWeightRate(),
            dto.getDistanceRate(),
            dto.getPriorityRate()
        );
    }
}
