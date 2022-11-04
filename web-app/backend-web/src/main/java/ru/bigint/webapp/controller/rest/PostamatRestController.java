package ru.bigint.webapp.controller.rest;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.entity.Postamat;
import ru.bigint.webapp.service.iface.PostamatService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.stream.Stream;

@Api(tags = {"Постаматы"})
@Tag(name = "Постаматы", description = "Сервис для работы с постаматами")
@RestController
@RequestMapping(value = "/api/postamats")
public class PostamatRestController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PostamatService postamatService;

    public PostamatRestController(PostamatService postamatService) {
        this.postamatService = postamatService;
    }

    @GetMapping("/list")
    public List<Postamat> getAllPostamatsList() {
        return postamatService.getAll();
    }

    @PostMapping("/save")
    public Postamat savePostamat(@RequestBody Postamat postamat) {
        return postamatService.add(postamat);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export() {
        try {
            File file = ResourceUtils.getFile("classpath:assets/fonts/arial.ttf");

            BaseFont baseFont = BaseFont.createFont(file.getPath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Document document = new Document();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            Font font = new Font(baseFont, 11, Font.NORMAL);
            Paragraph header = new Paragraph("Установленные постаматы", font);
            header.setSpacingAfter(50);
            document.add(header);

            PdfPTable table = new PdfPTable(3);
            table.setWidths(new float[] { 1, 2, 3 });
            addTableHeader(table, font);
            addRows(table, font);

            document.add(table);
            document.close();


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = "export.pdf";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addTableHeader(PdfPTable table, Font font) {
        Stream.of("#", "Место установки", "Адрес установки")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle, font));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, Font font) {
        List<Postamat> list = postamatService.getAll();

        for (Postamat postamat : list) {
            table.addCell(new PdfPCell(new Phrase(postamat.getId().toString(), font)));
            table.addCell(new PdfPCell(new Phrase(postamat.getPlace(), font)));
            table.addCell(new PdfPCell(new Phrase(postamat.getAddress(), font)));
        }
    }

}
