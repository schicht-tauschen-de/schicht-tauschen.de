package de.schichttauschen.web.data.vo.rest;

import de.schichttauschen.web.data.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestDepartment {
    private UUID id;
    private String name;
    private String description;

    public static RestDepartment fromDepartment(Department department) {
        return RestDepartment.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .build();
    }
}
