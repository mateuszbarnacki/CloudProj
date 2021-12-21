package com.example.proj.team;

import com.example.proj.dto.EmployeeDTO;
import org.neo4j.driver.internal.value.ListValue;
import org.neo4j.driver.internal.value.NodeValue;

import java.util.LinkedList;
import java.util.List;

public final class TeamResultsUtils {
    static final String EMPLOYEE_NAME = "name";
    static final String EMPLOYEE_SURNAME = "surname";
    static final String EMPLOYEE_EMAIL = "email";

    private TeamResultsUtils() {
    }

    public static List<EmployeeDTO> retrieveEmployeesDataFromTeammatesResultSet(List<TeamResults.Team> data) {
        List<EmployeeDTO> employees = new LinkedList<>();
        employees.addAll(retrieveEmployeesData((ListValue) data.get(0).getProductOwner()));
        employees.addAll(retrieveEmployeesData((ListValue) data.get(0).getTechLeader()));
        employees.addAll(retrieveEmployeesData((ListValue) data.get(0).getDeveloper()));
        return employees;
    }

    private static List<EmployeeDTO> retrieveEmployeesData(ListValue employees) {
        List<EmployeeDTO> resultSet = new LinkedList<>();

        employees.values().forEach(employee -> {
            NodeValue tmp = (NodeValue) employee;
            resultSet.add(new EmployeeDTO.Builder()
                    .name(tmp.asNode().get(EMPLOYEE_NAME).asString())
                    .surname(tmp.asNode().get(EMPLOYEE_SURNAME).asString())
                    .email(tmp.asNode().get(EMPLOYEE_EMAIL).asString())
                    .build());
        });

        return resultSet;
    }

}
