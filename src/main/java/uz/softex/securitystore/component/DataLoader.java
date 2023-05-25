//package uz.softex.securitystore.component;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import uz.softex.securitystore.inputs.entity.PaymentType;
//import uz.softex.securitystore.inputs.repository.PaymentTypeRepository;
//import uz.softex.securitystore.position.entity.Position;
//import uz.softex.securitystore.position.repository.PositionRepository;
//import uz.softex.securitystore.position.entity.PositionTypes;
//import uz.softex.securitystore.workers.entity.Workers;
//import uz.softex.securitystore.workers.repository.WorkersRepository;
//
//
//import java.util.List;
//
//import static uz.softex.securitystore.position.entity.PositionTypes.*;
//
//
//@Component
//public class DataLoader implements CommandLineRunner {//bu proyekt ran bolgandagi ishlydiagn klass
//    private final
//    WorkersRepository workersRepository;
//    private final
//    PositionRepository positionRepository;
//    private final
//    PasswordEncoder passwordEncoder;
//    private final
//    PaymentTypeRepository paymentTypeRepository;
//
//    @Value("${spring.sql.init.mode}")
//    private String initMode;
//
//    public DataLoader(PaymentTypeRepository paymentTypeRepository, PasswordEncoder passwordEncoder, PositionRepository positionRepository, WorkersRepository workersRepository) {
//        this.paymentTypeRepository = paymentTypeRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.positionRepository = positionRepository;
//        this.workersRepository = workersRepository;
//    }
//
//    @Override
//    public void run(String... args) {
//        if (!initMode.equals("always")) return;
//        Position projectManager = new Position();
//        projectManager.setName("ProjectManager");
//        projectManager.setPermissionList(List.of(PositionTypes.values()));
//        positionRepository.save(projectManager);
//
//        Workers workers = new Workers();
//        workers.setFullname("Bo`riyev Shaxboz");
//        workers.setUsername("devshaha");
//        workers.setPassword(passwordEncoder.encode("12345678"));
//        workers.setEnabled(true);
//        workers.setPosition(projectManager);
//        workersRepository.save(workers);
//        PaymentType paymentType = new PaymentType();
//        paymentType.setPaymentType("naqt");
//        paymentTypeRepository.save(paymentType);
//        PaymentType paymentTypeP = new PaymentType();
//        paymentTypeP.setPaymentType("plastik");
//        paymentTypeRepository.save(paymentTypeP);
//
//
//        Position position = new Position();
//        position.setName("Director");
//        List<PositionTypes> positionTypes = List.of(
//                VIEW_MY_STORE,
//
//                ADD_POSITION,
//                DELETE_POSITION,
//                EDIT_POSITION,
//                VIEW_POSITION,
//
//
//                ADD_INPUTS,
//                DELETE_INPUTS,
//                EDIT_INPUTS,
//                VIEW_INPUTS,
//
//
//                ADD_OUTPUTS,
//                DELETE_OUTPUTS,
//                EDIT_OUTPUTS,
//                VIEW_OUTPUTS,
//
//
//                ADD_WORKER,
//                DELETE_WORKER,
//                EDIT_WORKER,
//                VIEW_MY_WORKERS,
//                BLOCK_OR_UNBLOCK,
//                CHANGE_MY_PASSWORD,
//
//                ADD_PRODUCT,
//                DELETE_PRODUCT,
//                EDIT_PRODUCT,
//                VIEW_MY_PRODUCTS,
//                FILTER_PRODUCTS
//
//        );
//        position.setPermissionList(positionTypes);
//        positionRepository.save(position);
//    }
//}
