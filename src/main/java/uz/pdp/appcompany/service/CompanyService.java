package uz.pdp.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcompany.entity.Address;
import uz.pdp.appcompany.entity.Company;
import uz.pdp.appcompany.payload.CompanyDto;
import uz.pdp.appcompany.payload.ResponseApi;
import uz.pdp.appcompany.repositrory.AddressRepository;
import uz.pdp.appcompany.repositrory.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {


    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CompanyRepository companyRepository;

    public ResponseApi add(CompanyDto companyDto) {
        boolean existsByCorpName = companyRepository.existsByCorpName(companyDto.getCorpName());
        if (existsByCorpName)
            return new ResponseApi("Company already exists", false);
        Address address = new Address();
        address.setStreet(companyDto.getStreet());
        address.setHomeNumber(companyDto.getHomeNumber());
        Address save = addressRepository.save(address);

        Company company = new Company();
        company.setAddress(save);
        company.setDirectorName(companyDto.getDirectorName());
        company.setCorpName(companyDto.getCorpName());
        companyRepository.save(company);
        return new ResponseApi("Company added successfully", true);
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company getOneById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);
    }

    public ResponseApi delete(Integer id) {
        try {
            companyRepository.deleteById(id);
            return new ResponseApi("Successfully deleted", true);
        } catch (Exception e) {
            return new ResponseApi("Error in deleting company", false);
        }
    }

    public ResponseApi edit(Integer id, CompanyDto companyDto) {
        boolean existsByCorpNameAndIdNot = companyRepository.existsByCorpNameAndIdNot(companyDto.getCorpName(), id);
        if (existsByCorpNameAndIdNot) return new ResponseApi("Company already exists", false);
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent()) return new ResponseApi("Company not found", false);

        Address address = addressRepository.findById(optionalCompany.get().getAddress());
        address.setHomeNumber(companyDto.getHomeNumber());
        address.setStreet(companyDto.getStreet());
        addressRepository.save(address);
        Company company = optionalCompany.get();
        company.setCorpName(company.getCorpName());
        company.setAddress(address);
        company.setDirectorName(companyDto.getDirectorName());
        companyRepository.save(company);
        return new ResponseApi("Successfully edited", true);

    }
}
