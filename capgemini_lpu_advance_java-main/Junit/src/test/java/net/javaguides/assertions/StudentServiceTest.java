package net.javaguides.assertions;

import net.javaguides.StudentService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    @Test
    public void getStudentTest() {

        StudentService studentService = new StudentService();

        // List nikali (abhi empty hai kyunki add nahi kiya)
        List<?> listOfStudents = studentService.getStudents();

        boolean actualResult = listOfStudents.isEmpty();

        // ==================================================
        // 1️⃣ BASIC assertTrue
        // ==================================================
        // Agar condition true hai → test pass
        // Agar false hai → test fail
        //
        // assertTrue(actualResult);

        // ==================================================
        // 2️⃣ assertTrue with MESSAGE
        // ==================================================
        // Agar test fail hota hai toh yeh message show hoga
        //
        // assertTrue(actualResult, "List of students is empty!");

        // ==================================================
        // 3️⃣ assertTrue with MESSAGE SUPPLIER (Lambda)
        // ==================================================
        // Message tabhi generate hota hai jab test fail ho
        // (Performance ke liye best practice)
        //
        // assertTrue(actualResult, () -> "List of students is empty!");

        // ==================================================
        // 4️⃣ assertTrue with BOOLEAN SUPPLIER (Lambda)
        // ==================================================
        // Condition bhi lambda ke through di ja sakti hai
        //
        // assertTrue(() -> actualResult);

        // ==================================================
        // 5️⃣ assertTrue with BOOLEAN + MESSAGE (DONO lambda)
        // ==================================================
        // ✅ MOST PROFESSIONAL & RECOMMENDED WAY
        //
        assertTrue(
                () -> actualResult,                     // condition
                () -> "List of students is empty!"       // failure message
        );
    }
}
