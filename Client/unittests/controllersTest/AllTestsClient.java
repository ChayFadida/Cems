package controllersTest;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ ClientLogInTest.class, CreateNewExamTest.class })
public class AllTestsClient {

}
