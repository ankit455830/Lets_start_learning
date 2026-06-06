import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repo;

    @InjectMocks
    private EmployeeService svc;


    @Test
    void getByIdTest(){

        Employee employee = new Employee("john", "Doe", "ankit@gmail.com");
        when(repo.findById(1L)).thenReturn(Optional.of(employee));
        assertEquals("john", svc.getById(1L).getBody().getFirstName());
    }

    @Test
    void createEmployeeTest(){

        Employee employee = new Employee("ankit", "Doe", "ankit@gmail.com");
        when(repo.save(employee)).thenReturn(employee);
        assertEquals("ankit", svc.create(employee).getBody().getFirstName());
        assertEquals("ankit", employee.getFirstName());
        verify(repo).save(employee);
    }

    @Test
    void getById_InvalidId_Test() {

        when(repo.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> svc.getById(99L)
        );

        assertEquals("Employee not found", ex.getMessage());
    }

}
