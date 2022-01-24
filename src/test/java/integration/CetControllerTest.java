package integration;

import com.cet.CetApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@SpringBootTest(classes = CetApplication.class)
@AutoConfigureMockMvc
class CetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenFileShouldUploadAllData() throws Exception {
        Path getFile = Paths.get("C:\\Users\\susje\\Downloads\\CCF033COVID15012021.TXT");
        String content = Files.readString(getFile, StandardCharsets.ISO_8859_1);
        MockMultipartFile file = new MockMultipartFile(
                "file",
                getFile.toFile().getName(),
                MediaType.TEXT_PLAIN_VALUE,
                content.getBytes()
        );

        MvcResult result = mockMvc.perform(multipart("/cets/upload-data").file(file)).andReturn();
        assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    void givenFileAlreadyUploadedShouldReturn422StatusCode() throws Exception {
        Path getFile = Paths.get("C:\\Users\\susje\\Downloads\\CCF033COVID15012021.TXT");
        String content = Files.readString(getFile, StandardCharsets.ISO_8859_1);
        MockMultipartFile file = new MockMultipartFile(
                "file",
                getFile.toFile().getName(),
                MediaType.TEXT_PLAIN_VALUE,
                content.getBytes()
        );

        MvcResult result = mockMvc.perform(multipart("/cets/upload-data").file(file)).andReturn();
        assertEquals(422, result.getResponse().getStatus());
    }

    @Test
    void givenEmptyFileShouldReturn204StatusCode() throws Exception {
        Path getFile = Paths.get("C:\\Users\\susje\\Downloads\\CCF033COVID15012021EMPTY.TXT");
        String content = Files.readString(getFile, StandardCharsets.ISO_8859_1);
        MockMultipartFile file = new MockMultipartFile(
                "file",
                getFile.toFile().getName(),
                MediaType.TEXT_PLAIN_VALUE,
                content.getBytes()
        );

        MvcResult result = mockMvc.perform(multipart("/cets/upload-data").file(file)).andReturn();
        assertEquals(204, result.getResponse().getStatus());
    }

}