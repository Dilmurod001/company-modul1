package uz.pdp.appcompany.repositrory;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcompany.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    boolean existsByNameAndCompany_Id(String name, Integer company_id);
    boolean existsByNameAndIdNot(String name, Integer id);
}
