package sbr.track.btsystem.tests;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.BeforeAll;
import sbr.track.btsystem.web.ProjectController;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ProjectControllerTestAll {

    private static int counter = 0;

    @BeforeAll
    public static void suiteSetup() {
        System.out.println("Into beforeall");
        assertEquals(0, counter);
        counter++;
    }

/*     public ProjectControllerTestAll() {
        assertTrue(Arrays.asList(0, 5).contains(counter));
        counter++;
    }*/


    @BeforeEach
    void setUp() {
    }

    @Test
    void getProjectById() {
    }

    @Test
    void getAllProjects() throws InterruptedException {
        System.out.println("Method getAllProjects");
        int a = 3;
        int b = 1;
        int expRes = 4;
        int res = ProjectController.add(a, b);
        assertEquals(expRes, res);
        //fail("the test case is a prototype");


    }
}