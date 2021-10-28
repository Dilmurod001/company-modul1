package uz.pdp.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcompany.entity.Company;
import uz.pdp.appcompany.entity.Department;
import uz.pdp.appcompany.payload.DepartmentDto;
import uz.pdp.appcompany.payload.ResponseApi;
import uz.pdp.appcompany.repositrory.CompanyRepository;
import uz.pdp.appcompany.repositrory.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CompanyRepository companyRepository;

    public ResponseApi add(DepartmentDto departmentDto) {

        boolean existsByNameAndCompany_id = departmentRepository.existsByNameAndCompany_Id(departmentDto.getName(), departmentDto.getCompanyId());
        if (existsByNameAndCompany_id) {
            return new ResponseApi("Department already exists in this compnay", false);
        }
        Optional<Company> companyRepositoryById = companyRepository.findById(departmentDto.getCompanyId());
        if (!companyRepositoryById.isPresent())
            return new ResponseApi("Company not found", false);

        Department department = new Department();
        department.setName(departmentDto.getName());
        department.setCompany(companyRepositoryById.get());
        departmentRepository.save(department);
        return new ResponseApi("Successfully added", true);

    }

    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    public Department getOneById(Integer id) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        return departmentOptional.orElse(null);
    }

    public ResponseApi delete(Integer id) {
        try {
            departmentRepository.deleteById(id);
            return new ResponseApi("Successfully deleted", true);
        } catch (Exception e) {
            return new ResponseApi("Error in deleting", false);
        }
    }

    public ResponseApi edit(Integer id, DepartmentDto departmentDto) {

        Optional<Department> departmentRepositoryById = departmentRepository.findById(id);
        if (!departmentRepositoryById.isPresent()) return new ResponseApi("Not found", false);

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent()) return new ResponseApi("Company not found", false);


        boolean existsByNameAndIdNot = departmentRepository.existsByNameAndIdNot(departmentDto.getName(), id);
        if (existsByNameAndIdNot) return new ResponseApi("Department already exists", false);

        Department department = departmentRepositoryById.get();
        department.setName(departmentDto.getName());
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new ResponseApi("Successfully edited", true);
    }

}
