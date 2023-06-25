package taskManagerTest;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ CreateExamTest.class, UserTaskManagerTest.class })
public class AllTestsServer {

}
