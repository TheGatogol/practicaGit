package steps;

import io.cucumber.java.en.Given;
import methods.login;

public class loginSteps {

    @Given("Abrimos Chrome")
    public void abrimosChrome() {
    	login.abrimosNavegador();
    }
}
