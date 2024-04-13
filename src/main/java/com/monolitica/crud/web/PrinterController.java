package com.monolitica.crud.web;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monolitica.crud.dto.PrintDTO;
import com.monolitica.crud.dto.PrintRequestDTO;

@RestController
@RequestMapping("/v1/printer")
public class PrinterController {

    @GetMapping
    public ResponseEntity<List<String>> listPrinters() {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
    
        List<String> printerNames = Arrays.stream(printServices)
                .map(PrintService::getName)
                .collect(Collectors.toList());
    
        return ResponseEntity.ok().body(printerNames);
    }

    @GetMapping("/{printerName}")
    public ResponseEntity<String> printTest(@PathVariable final String printerName) throws IOException, PrinterException{
        PDFGenerator pdfGenerator = new PDFGenerator();
        String response = pdfGenerator.printTest(printerName);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<List<String>> print(@RequestBody final PrintRequestDTO printRequest) throws IOException, PrinterException{
        List<PrintDTO> printJobs = printRequest.getPrintSpooler();

        PDFGenerator pdfGenerator = new PDFGenerator();
        for (PrintDTO printDTO : printJobs) {
            String printerName = (String) printDTO.getPrinterName();
            pdfGenerator.createPDFKitchen(printDTO, printerName );
        }
        return ResponseEntity.ok().body(Arrays.asList("ok"));   
    }
}
