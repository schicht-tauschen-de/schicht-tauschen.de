package de.schichttauschen.web.data.vo.rest;

import de.schichttauschen.web.data.entity.AccountDepartment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestAccountDepartment {
    private RestCompany company;
    private String email;
    private String employeeNumber;

    public static RestAccountDepartment fromAccountDepartment(AccountDepartment accountDepartment) {
        return RestAccountDepartment.builder()
                .company(RestCompany.fromDepartment(accountDepartment.getDepartment()))
                .employeeNumber(accountDepartment.getEmployeeNumber())
                .email(accountDepartment.getEmail())
                .build();
    }
}
