package feb14casestudy2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.casestudy2.Appointment;
import com.casestudy2.AppointmentDao;
import com.casestudy2.Doctor;
import com.casestudy2.DoctorDao;
import com.casestudy2.HospitalService;
import com.casestudy2.MedicalRecord;
import com.casestudy2.MedicalRecordDao;
import com.casestudy2.Patient;
import com.casestudy2.PatientDao;

public class HospitalServiceTest {
    PatientDao pd = new PatientDao();
    DoctorDao dd = new DoctorDao();
    AppointmentDao ad = new AppointmentDao();
    MedicalRecordDao md = new MedicalRecordDao();

    HospitalService service = new HospitalService();

    @Test
    public void testOneToOnePersist() {
        MedicalRecord mr = new MedicalRecord(1,"O+","None");
        md.saveMedicalRecord(mr);

        Patient p = new Patient(1L,"Ravi",30,"999");
        p.setMedicalRecord(mr);

        pd.savePatient(p);

        Patient db = pd.findPatient(1L);

        assertNotNull(db);
        assertEquals("O+", db.getMedicalRecord().getBloodGroup());
    }
    
    @Test
    public void testOneToManyPersist(){

        Appointment a1 = new Appointment(11L,"today",500);
        Appointment a2 = new Appointment(12L,"today",700);

        ad.saveAppointment(a1);
        ad.saveAppointment(a2);

        Doctor d = new Doctor(2L,"Dr.Smith","Cardio");

        List<Appointment> list = new ArrayList<>();
        list.add(a1);
        list.add(a2);

        d.setList(list);

        dd.saveDoctor(d);

        Doctor db = dd.findDoctor(2L);

        assertEquals(2, db.getList().size());
    }
    
    @Test
    public void testManyToOnePersist(){

        Patient p = new Patient(3L,"Kumar",40,"888");
        pd.savePatient(p);

        Appointment a = new Appointment(21L,"today",1000);
        a.setPatient(p);

        ad.saveAppointment(a);

        Appointment db = ad.findAppointment(21L);

        assertEquals("Kumar", db.getPatient().getEname());
    }
    
    
    @Test
    public void testUpdateAppointmentFee(){

        Appointment a = new Appointment(31L,"today",200);
        ad.saveAppointment(a);

        a.setFees(999);
        ad.saveAppointment(a);   

        Appointment db = ad.findAppointment(31L);

        assertEquals(999, db.getFees());
    }
    
    
    @Test
    public void testDeletePatient(){

        Patient p = new Patient(41L,"DeleteMe",50,"777");
        pd.savePatient(p);

        pd.deletePatient(41L);

        Patient db = pd.findPatient(41L);

        assertNull(db);
    }
    
    @Test
    public void testServiceWorkflow(){

        MedicalRecord mr = new MedicalRecord(51,"B+","Dust");
        md.saveMedicalRecord(mr);

        Patient p = new Patient(51L,"Workflow",25,"555");

        service.registerPatientWithMedicalRecord(p,mr);

        Appointment a = new Appointment(52L,"today",300);
        ad.saveAppointment(a);

        service.assignAppointmentToPatient(52L,51L);

        Appointment db = ad.findAppointment(52L);

        assertEquals("Workflow", db.getPatient().getEname());
    }
    
    
    
    
}
