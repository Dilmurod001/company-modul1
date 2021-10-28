package uz.pdp.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcompany.entity.Address;
import uz.pdp.appcompany.entity.Department;
import uz.pdp.appcompany.entity.Worker;
import uz.pdp.appcompany.payload.ResponseApi;
import uz.pdp.appcompany.payload.WorkerDto;
import uz.pdp.appcompany.repositrory.AddressRepository;
import uz.pdp.appcompany.repositrory.DepartmentRepository;
import uz.pdp.appcompany.repositrory.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    AddressRepository addressRepository;

    public ResponseApi add(WorkerDto workerDto) {

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (!optionalDepartment.isPresent() || existsByPhoneNumber)
            return new ResponseApi("Error", false);
        Address address = new Address();
        address.setStreet(workerDto.getStreet());
        address.setHomeNumber(workerDto.getHomeNumber());
        Address save = addressRepository.save(address);


        Worker worker = new Worker();
        worker.setFullName(workerDto.getFullName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setDepartment(optionalDepartment.get());
        worker.setAddress(save);
        workerRepository.save(worker);
        return new ResponseApi("Successfully added", true);
    }


    public List<Worker> getAll() {
        return workerRepository.findAll();
    }

    public Worker getOneById(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElse(null);
    }

    public ResponseApi delete(Integer id) {
        try {
            workerRepository.deleteById(id);
            return new ResponseApi("Successfully deleted", true);
        } catch (Exception e) {
            return new ResponseApi("Error in deleting", false);
        }

    }

    public ResponseApi edit(Integer id, WorkerDto workerDto) {
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        boolean numberAndIdNot = workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id);
        if (!optionalDepartment.isPresent() || !optionalWorker.isPresent() || numberAndIdNot) {
            return new ResponseApi("Error", false);
        }
        Address address = addressRepository.findById(optionalWorker.get().getAddress());
        address.setHomeNumber(workerDto.getHomeNumber());
        address.setStreet(workerDto.getStreet());
        Address save = addressRepository.save(address);
        Worker worker = optionalWorker.get();
        worker.setDepartment(optionalDepartment.get());
        worker.setAddress(save);
        worker.setFullName(workerDto.getFullName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        workerRepository.save(worker);
        return new ResponseApi("Successfully edited", true);

    }
}
