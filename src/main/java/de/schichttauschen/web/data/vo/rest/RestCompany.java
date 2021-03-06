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
public class RestCompany {
    private UUID id;
    private String name;
    private RestDepartment department;

    public static RestCompany fromDepartment(Department department) {
        return RestCompany.builder()
                .id(department.getCompany().getId())
                .name(department.getCompany().getName())
                .department(RestDepartment.fromDepartment(department))
                .build();
    }
}
