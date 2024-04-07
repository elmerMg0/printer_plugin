package com.monolitica.crud.dto;
import java.util.List;
public class PrintRequestDTO {
    private List<PrintDTO> printSpooler;

    public List<PrintDTO> getPrintSpooler() {
        return printSpooler;
    }

    public void setPrintSpooler(List<PrintDTO> printSpooler) {
        this.printSpooler = printSpooler;
    }
}
