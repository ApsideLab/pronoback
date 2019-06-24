package com.apside.prono.types;

import com.apside.prono.model.TypeEntity;
import com.apside.prono.service.TypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TypeIntegrationTest {
    private String url = "http://localhost:8086/pronos/types/";
    private String label = "Ligue1TestType";
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TypeService typeService;


    @Test
    public void contextLoads() {
    }

    @Test
    public void postTest() {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setLabel(label);

        ResponseEntity<String> response = restTemplate.postForEntity(url, typeEntity, String.class);
        //Verify request succeed
        assertThat(response.getStatusCode().equals(HttpStatus.CREATED)).isTrue();
        clean(label);
    }

    @Test
    public void putTest() {
        String newLabel = "Ligue2Test";
        initialize(label);
        TypeEntity typeEntity = typeService.find(label);
        typeEntity.setLabel(newLabel);
        restTemplate.put(url, typeEntity);

        //Verify request succeed
        assertThat(typeService.find(newLabel) != null).isTrue();
        clean(newLabel);
    }

    @Test
    public void getTest() {
        initialize(label);

        //Verification du code retour
        long id = typeService.find(label).getId();
        ResponseEntity<String> response = restTemplate.getForEntity(url + id, String.class);
        assertThat(response.getStatusCode().equals(HttpStatus.OK)).isTrue();
        //Verification du contenu retourné
        TypeEntity typeEntity = restTemplate.getForObject(url + id, TypeEntity.class);
        assertThat(typeEntity.getId() == id && typeEntity.getLabel().equals(label)).isTrue();
        clean(label);
    }

    @Test
    public void homeResponse() {
/*		String body = this.restTemplate.getForObject("/", String.class);
		assertThat(body).isEqualTo("Hello");*/
    }

    @Test
    public void deleteTest() {
        initialize(label);
        long id = typeService.find(label).getId();
        restTemplate.delete(url + id);

        assertThat(typeService.find(label) == null).isTrue();
    }

    public void initialize(String label) {
        if (typeService.find(label) == null) {
            TypeEntity typeEntity = new TypeEntity();
            typeEntity.setLabel(label);
            restTemplate.postForEntity(url, typeEntity, String.class);
        }
    }

    public void clean(String label) {
        if (typeService.find(label) != null) {
            long id = typeService.find(label).getId();
            restTemplate.delete(url + id);
        }
    }
}
