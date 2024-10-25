package se.swedisheventplanners.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.swedisheventplanners.portal.domain.event.TestTable;
import se.swedisheventplanners.portal.repository.TestTableRepository;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestTableRepository testTableRepository;

    @GetMapping("/test123")
    public String test123() {
        TestTable testTable = new TestTable();
        testTable.setName("Bakalis");
        testTableRepository.save(testTable);
        return "main";
    }
}

