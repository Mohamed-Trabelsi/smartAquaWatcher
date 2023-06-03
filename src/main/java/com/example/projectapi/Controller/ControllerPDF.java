package com.example.projectapi.Controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ControllerPDF {

        @GetMapping("/pdf")
        public void generatePdf(HttpServletResponse response) throws Exception {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            document.add(new Paragraph("Hello, NEHDI!"));
            document.close();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=hello.pdf");

    }
}
